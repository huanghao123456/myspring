package com.myspring.springdemo.designpattern.decorator;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public abstract class Decorator implements Component{

    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }
}
