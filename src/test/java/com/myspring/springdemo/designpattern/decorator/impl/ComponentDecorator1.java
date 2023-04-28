package com.myspring.springdemo.designpattern.decorator.impl;

import com.myspring.springdemo.designpattern.decorator.Component;
import com.myspring.springdemo.designpattern.decorator.Decorator;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public class ComponentDecorator1 extends Decorator {

    public ComponentDecorator1(Component component) {
        super(component);
        component.operation();
    }

    @Override
    public void operation() {
        System.out.println("附加功能1：美颜");
    }
}
