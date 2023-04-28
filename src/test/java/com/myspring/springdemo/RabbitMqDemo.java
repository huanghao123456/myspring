package com.myspring.springdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMqDemo {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        String msg = "发送的pdb消息!";
        rabbitTemplate.convertAndSend("PdbFanoutExchange", "",msg);
    }

    @Test
    @RabbitListener(queues = "PdbQueue")
    @RabbitHandler
    public void listenPdbQueue(String msg) {
        System.out.println("msg = " + msg);
    }

}
