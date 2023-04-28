package com.myspring.springdemo.designpattern.strategy.impl;

import com.myspring.springdemo.designpattern.strategy.Strategy;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public class PrimaryMemberStrategy implements Strategy {

    @Override
    public double calcPrice(double price) {
        return price * 1.0;
    }
}
