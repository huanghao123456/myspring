package com.myspring.springdemo.service.impl;

import com.myspring.springdemo.annotation.TaskNameValid;
import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.common.result.RestResultUtils;
import com.myspring.springdemo.entity.dto.LoginFormDTO;
import com.myspring.springdemo.service.IUserService;
import com.myspring.springdemo.common.utils.client.RegexPatternsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private ResourceLoader resourceLoader;

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

        return RestResultUtils.success("上传成功！", uniqueTaskName);
    }

    @Override
    public RestResult<Pair<String, String>> calcDistMatrix(String pdbId) {
        // 根据pdbId(uniqueTaskName)来判断服务器中是否存在该pdb文件
        if (!findPdbFromHostById(pdbId)) {
            return RestResultUtils.failedWithMsg(4000, "pdb文件中不存在该ID：" + pdbId);
        }

        List<String> params = new ArrayList<>();
        params.add(pdbId);
        params.add(getPdbAbsolutePath(pdbId));
        Pair<String, String> resultPair;
        try {
            resultPair = exec(params);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            return RestResultUtils.failed("创建临时dist_matrix_multiprocess_calc.py文件失败");
        }

        if (null == resultPair) {
            return RestResultUtils.failed("python脚本执行失败");
        }

        return RestResultUtils.success(resultPair);
    }

    private String getPdbAbsolutePath(String pdbId) {
        File pdbFile = new File(pdbUploadDir + File.separator + pdbId);
        for (File contentFile : Objects.requireNonNull(pdbFile.listFiles())) {
            if (contentFile.getName().endsWith(".pdb")) {
                return contentFile.getAbsolutePath();
            }
        }
        return "";
    }

    private Pair<String, String> exec(List<String> params) throws IOException {
        // 从resource资源中获取python脚本
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("script/dist_matrix_multiprocess_calc.py");
        // 打包项目后resource中的python脚本将不可见，不可被执行，因此需要创建副本，此副本可被执行
        File tempFile = File.createTempFile("dist_matrix_multiprocess_calc", ".py");
        tempFile.deleteOnExit();
        String command = "python ";
        Pair<String, String> resultPair = null;
        try (FileOutputStream tempOutputStream = new FileOutputStream(tempFile)) {
            assert inputStream != null;
            IOUtils.copy(inputStream, tempOutputStream);
            String filePath = tempFile.getAbsolutePath();
            log.info("文件路径:{}", filePath);

            if (!CollectionUtils.isEmpty(params)) {
                command += filePath + " " + params.get(0) + " " + params.get(1);
            }
            log.info("执行python脚本[{}]", command);

            Process proc = Runtime.getRuntime().exec(command);
            BufferedReader bfr = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            List<String> resultList = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                resultList.add(line);
            }
            bfr.close();

            log.info("执行结果状态码: " + proc.waitFor());
            resultPair = Pair.of(resultList.get(0), resultList.get(1));
            return resultPair;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("脚本执行失败!" + e);
            return resultPair;
        } finally {
            IOUtils.closeQuietly(inputStream);
            // 删除临时文件
            tempFile.deleteOnExit();
        }
    }

    private boolean findPdbFromHostById(String pdbId) {
        File pdbFile = new File(pdbUploadDir + File.separator + pdbId);
        return pdbFile.exists() || pdbFile.isDirectory();
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
