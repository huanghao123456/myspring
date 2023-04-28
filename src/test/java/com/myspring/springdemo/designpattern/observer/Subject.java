package com.myspring.springdemo.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hhuang
 * @date: 2022/10/21
 */
public class Subject {

    private final List<Observer> container = new ArrayList<>();

    public void addObserver(Observer observer) {
        container.add(observer);
    }

    public boolean removeObserver(Observer observer) {
        if (!container.contains(observer)) {
            return false;
        }

        container.remove(observer);
        return true;
    }

    public void notifyObserver(Object object) {
        for (Observer observer : container) {
            observer.update(object);
        }
    }
}
