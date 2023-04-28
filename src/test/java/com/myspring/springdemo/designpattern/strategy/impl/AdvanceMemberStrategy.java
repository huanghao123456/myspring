package com.myspring.springdemo.designpattern.strategy.impl;

import com.myspring.springdemo.designpattern.strategy.Strategy;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public class AdvanceMemberStrategy implements Strategy {

    @Override
    public double calcPrice(double price) {
        return price * 0.8;
    }
}
