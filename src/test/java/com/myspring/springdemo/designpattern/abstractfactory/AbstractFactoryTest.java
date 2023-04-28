package com.myspring.springdemo.designpattern.abstractfactory;

import com.myspring.springdemo.designpattern.abstractfactory.factory.AbstractFactory;
import com.myspring.springdemo.designpattern.abstractfactory.factory.impl.HuaWeiFactory;
import com.myspring.springdemo.designpattern.abstractfactory.factory.impl.XiaomiFactory;
import com.myspring.springdemo.designpattern.abstractfactory.phone.Phone;
import com.myspring.springdemo.designpattern.abstractfactory.router.Router;
import org.junit.jupiter.api.Test;

/**
 * @author: hhuang
 * @date: 2022/10/10
 */
public class AbstractFactoryTest {
    @Test
    void test1() {
        AbstractFactory huaweiFactory = new HuaWeiFactory();

        Phone huaweiIphone = huaweiFactory.iPhone();
        huaweiIphone.sendMsg();

        Router huaweiRouter = huaweiFactory.iRouter();
        huaweiRouter.conn();
    }

    @Test
    void test2() {
        XiaomiFactory xiaomiFactory = new XiaomiFactory();

        Phone xiaomiIphone = xiaomiFactory.iPhone();
        xiaomiIphone.shutDown();

        Router xiaomiRouter = xiaomiFactory.iRouter();
        xiaomiRouter.conn();
    }
}
