package com.myspring.springdemo.designpattern.ResponsibilityChain;

/**
 * @author: hhuang
 * @date: 2022/10/21
 */
public class RequestFrequentHandler extends Handler {

    public RequestFrequentHandler(Handler next) {
        super(next);
    }

    @Override
    boolean process(Request request) {
        System.out.println("访问频率控制");

        if (!request.isFrequentOK()) {
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
