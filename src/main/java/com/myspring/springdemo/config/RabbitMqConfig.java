package com.myspring.springdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 消息队列配置
 * @author huanghao
 */

@Slf4j
@Configuration
public class RabbitMqConfig {

    /**
     * 静态变量，需要通过set方法来让spring注入该变量
     */
    public static String PDB_QUEUE_NAME;

    @Value("${spring.rabbitmq.pdb-queue-name}")
    public void setPdbQueueName(String pdbQueueName) {
        PDB_QUEUE_NAME = pdbQueueName;
    }

    /**
     * Fanout队列
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(PDB_QUEUE_NAME,true);
    }

    /**
     * Fanout交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("PdbFanoutExchange",true,false);
    }

    /**
     * 将队列和交换机绑定
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
}
