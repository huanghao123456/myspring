package com.myspring.springdemo;

import com.myspring.springdemo.service.ResultNotification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EmailTest {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private ResultNotification resultNotification;

    @Test
    public void test() {
//        resultNotification.watchResultsThenNotify();
    }

}
