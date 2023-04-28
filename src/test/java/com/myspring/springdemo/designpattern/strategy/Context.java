package com.myspring.springdemo.designpattern.strategy;

/**
 * 该类负责进行策略注入
 *
 * @author: hhuang
 * @date: 2022/10/12
 */
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public double calc(double price) {
        return strategy.calcPrice(price);
    }
}
