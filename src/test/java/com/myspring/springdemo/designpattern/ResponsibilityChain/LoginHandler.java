package com.myspring.springdemo.designpattern.ResponsibilityChain;

/**
 * @author: hhuang
 * @date: 2022/10/21
 */
public class LoginHandler extends Handler {

    public LoginHandler(Handler next) {
        super(next);
    }

    @Override
    boolean process(Request request) {
        System.out.println("登录验证");

        if (!request.isLoggedOn()) {
            return false;
        }

        if (getNext() == null) {
            return true;
        }

        if (getNext().process(request)) {
            return true;
        }

        return false;
    }
}
