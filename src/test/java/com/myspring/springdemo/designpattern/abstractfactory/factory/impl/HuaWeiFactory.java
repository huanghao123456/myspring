package com.myspring.springdemo.designpattern.abstractfactory.factory.impl;

import com.myspring.springdemo.designpattern.abstractfactory.factory.AbstractFactory;
import com.myspring.springdemo.designpattern.abstractfactory.phone.Phone;
import com.myspring.springdemo.designpattern.abstractfactory.phone.impl.HuaWeiPhone;
import com.myspring.springdemo.designpattern.abstractfactory.router.impl.HuaWeiRouter;
import com.myspring.springdemo.designpattern.abstractfactory.router.Router;

/**
 * @author: hhuang
 * @date: 2022/10/10
 */
public class HuaWeiFactory implements AbstractFactory {
    @Override
    public Phone iPhone() {
        return new HuaWeiPhone();
    }

    @Override
    public Router iRouter() {
        return new HuaWeiRouter();
    }
}
