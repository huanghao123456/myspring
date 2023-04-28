package com.myspring.springdemo.service.impl;

import com.myspring.springdemo.annotation.TaskNameValid;
import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.common.result.RestResultUtils;
import com.myspring.springdemo.entity.dto.LoginFormDTO;
import com.myspring.springdemo.service.IUserService;
import com.myspring.springdemo.common.utils.client.RegexPatternsUtils;
import com.myspring.springdemo.service.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户登录，提交文件
 *
 * @author huanghao
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Value("${upload.dir}")
    private String pdbUploadDir;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Boolean login(LoginFormDTO loginFormDTO) {
        log.info(loginFormDTO.getEmail() + "登录成功!");
        return true;
    }

    @Override
    public RestResult<String> upload(@NotNull MultipartFile pdb, @TaskNameValid String taskName, @NotBlank String fullSequence, @NotBlank String email) {
        // 创建以唯一任务名命名的文件夹
        String uniqueTaskName = createNewFileNameByTime(taskName);
        File taskFile = new File(pdbUploadDir, uniqueTaskName);
        boolean mkdir = taskFile.mkdir();
        String taskDir = pdbUploadDir + File.separator + uniqueTaskName;

        // 创建 pdb 文件，并将邮箱作为pdb名称（邮箱用于任务执行完毕后发送邮箱通知）
        String pdbFileName = email + ".pdb";
        File file = new File(taskDir, pdbFileName);
        try {
            pdb.transferTo(file);
            log.info("pdb文件上传成功！");
        } catch (IOException e) {
            log.error("pdb文件上传失败！", e);
            return RestResultUtils.failedWithMsg(4000, "pdb文件上传失败！");
        }

        // 将 FullSequence 保存到txt中
        String fullSequenceName = taskDir + File.separator + "full_sequence.txt";
        try (FileWriter fw = new FileWriter(fullSequenceName)) {
            fw.write(fullSequence);
            log.info("fullSequence保存成功！");
        } catch (IOException e) {
            log.error("fullSequence文件创建失败！", e);
            return RestResultUtils.failedWithMsg(4000, "fullSequence文件创建失败！");
        }

        // 在服务器生成任务文件后,将该任务标识发送至消息队列
        sendMsg(uniqueTaskName);

        return RestResultUtils.success("上传成功！", null);
    }

    /**
     * 用户上传的文件任务发送至消息队列
     */
    private void sendMsg(String taskName) {
        rabbitTemplate.convertAndSend("PdbFanoutExchange", "", taskName);
    }

    /**
     * 创建唯一的新名称,由于UUID、SnowFlake等唯一ID不便于人眼观察生成时间,故此处由手工简易生成
     *
     * @param originalFilename 客户上传的原名称
     * @return 时间作为前缀的新名称(可作为唯一标识)
     */
    private String createNewFileNameByTime(String originalFilename) {
        String filePrefix = LocalDateTime.now().toString().replaceAll(RegexPatternsUtils.INVALID_PDB_NAME_SIGN, "-");
        return filePrefix + "_" + originalFilename;
    }
}
