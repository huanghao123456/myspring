package com.myspring.springdemo.designpattern.abstractfactory.phone.impl;

import com.myspring.springdemo.designpattern.abstractfactory.phone.Phone;

/**
 * @author: hhuang
 * @date: 2022/10/10
 */
public class HuaWeiPhone implements Phone {

    @Override
    public void start() {

    }

    @Override
    public void shutDown() {

    }

    @Override
    public void sendMsg() {

    }

    public HuaWeiPhone() {
        System.out.println("HuaWeiPhone");
    }
}
