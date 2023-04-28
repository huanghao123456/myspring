package com.myspring.springdemo.designpattern.decorator.impl;

import com.myspring.springdemo.designpattern.decorator.Component;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public class ConcreteComponent implements Component {

    @Override
    public void operation() {
        System.out.println("基础功能：拍照");
    }
}
