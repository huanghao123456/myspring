package com.myspring.springdemo.designpattern.builder;

import com.myspring.springdemo.designpattern.builder.impl.PhoneBuilder;
import org.junit.jupiter.api.Test;

/**
 * @author: hhuang
 * @date: 2022/10/13
 */
public class BuilderTest {

    @Test
    void test() {
        Buildable phone = new PhoneBuilder()
                .addBattery("南孚")
                .addCamera("徕卡")
                .addCpu("锐龙")
                .addEarPhone("iPod")
                .build();
        System.out.println(phone);
    }
}
