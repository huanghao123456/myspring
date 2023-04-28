package com.myspring.springdemo.designpattern.abstractfactory.factory.impl;

import com.myspring.springdemo.designpattern.abstractfactory.factory.AbstractFactory;
import com.myspring.springdemo.designpattern.abstractfactory.phone.Phone;
import com.myspring.springdemo.designpattern.abstractfactory.phone.impl.XiaomiPhone;
import com.myspring.springdemo.designpattern.abstractfactory.router.Router;
import com.myspring.springdemo.designpattern.abstractfactory.router.impl.XiaomiRouter;

/**
 * @author: hhuang
 * @date: 2022/10/10
 */
public class XiaomiFactory implements AbstractFactory {
    @Override
    public Phone iPhone() {
        return new XiaomiPhone();
    }

    @Override
    public Router iRouter() {
        return new XiaomiRouter();
    }
}
