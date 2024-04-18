package com.myspring.springdemo.service;

import com.myspring.springdemo.entity.dto.PdbFileInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author huanghao
 */
@Slf4j
@Service
public class ResultNotification {
    private final String uri = "http://172.19.238.240/task_viewer.html?task_name=";
    @Value("${spring.mail.username}")
    private String from;

    @Value("${upload.dir}")
    private String pdbUploadDir;

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 每10分钟检查每个任务文件夹下是否有结果生成，有则发邮件通知用户
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void watchResultsThenNotify() {
        List<PdbFileInfoDTO> tasksNeedNotified = watchResults();

        sendEmails(tasksNeedNotified);

        recordToTxt(tasksNeedNotified);
    }

    /**
     * 因未搭建数据库，故已完成的任务，其任务名将以字符串格式 保存在completed.txt文件中，扫描时将排除这些文件，以防止邮件重复发送
     */
    private List<PdbFileInfoDTO> watchResults() {
        List<String> thisTimeCompletedTaskName = getThisTimeCompletedTaskName();
        List<String> lastTimeCompletedTaskName = getLastTimeCompletedTaskName();
        thisTimeCompletedTaskName.removeAll(lastTimeCompletedTaskName);

        List<PdbFileInfoDTO> pdbCompletedList = new ArrayList<>();
        for (String taskName : thisTimeCompletedTaskName) {
            File file = new File(pdbUploadDir + File.separator + taskName);
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                String componentName = listFile.getName();
                // pdb文件是以email命名
                if (componentName.endsWith(".pdb")) {
                    String email = componentName.replace(".pdb", "");
                    PdbFileInfoDTO pdbFileInfoDTO = new PdbFileInfoDTO(null, file.getName(), null, email);
                    pdbCompletedList.add(pdbFileInfoDTO);
                }
            }
        }
        return pdbCompletedList;
    }

    /**
     * 获取本次所有有.result生成的任务，文件目录示例结构如下：
     * <pre>
     *  root
     *   |
     *   |_pdb
     *      |
     *      |_task1
     *      |   |_fullSequence.txt
     *      |   |_task.result
     *      |   |_task.pdb
     *      |
     *      |_task2
     *      |   |_fullSequence.txt
     *      |   |_task.pdb
     *      |
     *      |_task3
     *      |
     *     ...
     * </pre>
     */
    private List<String> getThisTimeCompletedTaskName() {
        List<String> thisTimeCompletedTaskNameList = new ArrayList<>();

        File uploadFile = new File(pdbUploadDir);
        for (File file : Objects.requireNonNull(uploadFile.listFiles())) {
            if (!file.isDirectory()) {
                continue;
            }

            boolean hasCompleted = false;
            for (File contentFile : Objects.requireNonNull(file.getAbsoluteFile().listFiles())) {
                if (contentFile.getName().endsWith(".result")) {
                    hasCompleted = true;
                    break;
                }
            }

            if (hasCompleted) {
                thisTimeCompletedTaskNameList.add(file.getName());
            }
        }

        return thisTimeCompletedTaskNameList;
    }

    /**
     * 获取所有上轮已完成且已发过邮件，不需要再发邮箱通知的任务对象
     */
    private List<String> getLastTimeCompletedTaskName() {
        List<String> lastTimeCompletedTaskNameList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pdbUploadDir + File.separator + "completed.txt"))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                lastTimeCompletedTaskNameList.add(tempString);
            }
        } catch (IOException e) {
            log.error("读取completed.txt文件失败！", e);
        }
        return lastTimeCompletedTaskNameList;
    }

    private void recordToTxt(List<PdbFileInfoDTO> tasksNeedNotified) {
        if (tasksNeedNotified.isEmpty()) {
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pdbUploadDir + File.separator + "completed.txt", true))) {
            for (PdbFileInfoDTO pdbFileInfoDTO : tasksNeedNotified) {
                bufferedWriter.append(pdbFileInfoDTO.getTaskName());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            log.error("记录至completed.txt失败", e);
        }
    }

    public void sendEmails(List<PdbFileInfoDTO> taskNeedNotified) {
        taskNeedNotified.forEach(e -> sendSimpleMail(e.getEmail(), "运行结果详见：" + uri + e.getTaskName()));
    }

    private void sendSimpleMail(String to, String content) {
        this.sendSimpleMail0(from, to, content);
    }

    private void sendSimpleMail0(String from, String to, String content) {
        // 简单邮件直接构建一个 SimpleMailMessage 对象进行配置并发送即可
        SimpleMailMessage simpMsg = new SimpleMailMessage();
        simpMsg.setFrom(from);
        simpMsg.setTo(to);
        String subject = "PDB任务执行结束通知";
        simpMsg.setSubject(subject);
        simpMsg.setText(content);
        javaMailSender.send(simpMsg);
    }
}
