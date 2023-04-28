package com.myspring.springdemo.designpattern.observer;

/**
 * @author: hhuang
 * @date: 2022/10/21
 */
public class Task1 implements Observer{

    @Override
    public void update(Object object) {
        System.out.println("task1 received:" + object);
    }
}
