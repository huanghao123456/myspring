package com.myspring.springdemo.service;

import com.myspring.springdemo.config.RabbitMqConfig;
import com.myspring.springdemo.service.impl.ManagerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author huanghao
 */
@Service
@Slf4j
public class MessageConsumer {

    @Value("${gpu.threshold}")
    private double taskCompletedSign;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ManagerServiceImpl managerService;

    /**
     * 每2分钟监测一次GPU使用情况，若使用率低，则代表上一任务已经执行完，继而可以提交新任务
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void watchGpu() {
        // 目前的服务器上1仅一张显卡
        double usageRate = managerService.getGpuStatus().get(0).getUsageRate();

        if (usageRate > taskCompletedSign) {
            return;
        }

        String taskName = getOneTask();
        if (!StringUtils.hasText(taskName)) {
            return;
        }

        // TODO: 在服务器目录中根据任务名找到对应文件后，执行Python脚本
        log.info("正在执行任务：" + taskName);
    }

    /**
     * RabbitMq的拉模式（需要消息时，手动从队列中拿消息）
     */
    public String getOneTask() {
        return (String) rabbitTemplate.receiveAndConvert(RabbitMqConfig.PDB_QUEUE_NAME);
    }
}
