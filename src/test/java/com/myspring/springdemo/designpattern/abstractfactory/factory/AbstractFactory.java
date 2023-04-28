package com.myspring.springdemo.designpattern.abstractfactory.factory;

import com.myspring.springdemo.designpattern.abstractfactory.phone.Phone;
import com.myspring.springdemo.designpattern.abstractfactory.router.Router;

/**
 * <p>抽象工厂</p>
 * 抽象工厂模式中只有一个产品体系时会退化为工厂方法模式
 * <p>四要素：</p>
 * <li>抽象工厂，抽象产品</li>
 * <li>具体工厂，具体产品</li>
 * @author: hhuang
 * @date: 2022/10/10
 */
public interface AbstractFactory {
    Phone iPhone();
    Router iRouter();
}
