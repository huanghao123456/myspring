package com.myspring.springdemo.designpattern.builder.impl;

import com.myspring.springdemo.designpattern.builder.Buildable;
import com.myspring.springdemo.designpattern.builder.Phone;
import lombok.ToString;

/**
 * @author: hhuang
 * @date: 2022/10/13
 */
@ToString
public class PhoneBuilder implements Buildable {

    private final Phone phone = new Phone();

    public PhoneBuilder addCpu(String cpu) {
        phone.setCpu(cpu);
        return this;
    }

    public PhoneBuilder addBattery(String battery) {
        phone.setBattery(battery);
        return this;
    }

    public PhoneBuilder addCamera(String camera) {
        phone.setCamera(camera);
        return this;
    }

    public PhoneBuilder addEarPhone(String earPhone) {
        phone.setEarPhone(earPhone);
        return this;
    }

    @Override
    public Buildable build() {
        return this;
    }
}
