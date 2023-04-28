package com.myspring.springdemo.designpattern.abstractfactory.router.impl;

import com.myspring.springdemo.designpattern.abstractfactory.router.Router;

/**
 * @author: hhuang
 * @date: 2022/10/10
 */
public class HuaWeiRouter implements Router {
    @Override
    public void conn() {
        System.out.println("no message");
    }

    public HuaWeiRouter() {
        System.out.println("HuaWeiRouter");
    }
}
