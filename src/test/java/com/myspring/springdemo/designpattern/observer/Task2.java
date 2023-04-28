package com.myspring.springdemo.designpattern.observer;

/**
 * @author: hhuang
 * @date: 2022/10/21
 */
public class Task2 implements Observer{

    @Override
    public void update(Object object) {
        System.out.println("task2 received:" + object);
    }
}
