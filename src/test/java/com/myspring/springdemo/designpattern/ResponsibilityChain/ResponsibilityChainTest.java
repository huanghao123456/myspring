package com.myspring.springdemo.designpattern.ResponsibilityChain;

import org.junit.jupiter.api.Test;

/**
 * @author: hhuang
 * @date: 2022/10/20
 */
public class ResponsibilityChainTest {

    @Test
    void test() {
        Request request = new Request.RequestBuilder().frequentOK(true).loggedOn(false).build();
        RequestFrequentHandler handler = new RequestFrequentHandler(new LoginHandler(null));

        if (handler.process(request)) {
            System.out.println("正常业务处理");
        } else {
            System.out.println("业务异常");
        }
    }
}
