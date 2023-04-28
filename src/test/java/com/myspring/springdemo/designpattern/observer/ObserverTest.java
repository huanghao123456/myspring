package com.myspring.springdemo.designpattern.observer;

import org.junit.jupiter.api.Test;

/**
 * @author: hhuang
 * @date: 2022/10/21
 */
public class ObserverTest {

    @Test
    void test() {
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();

        Subject subject = new Subject();
        subject.addObserver(task1);
        subject.addObserver(task2);

        subject.notifyObserver("有吏夜捉人");
    }
}
