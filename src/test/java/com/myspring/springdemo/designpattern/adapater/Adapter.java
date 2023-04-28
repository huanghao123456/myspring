package com.myspring.springdemo.designpattern.adapater;

/**
 * 对象适配器的核心即是把原接口/对象传进来，对其魔改后通过该该适配器再输出
 *
 * @author: hhuang
 * @date: 2022/10/20
 */
public class Adapter implements Target{

    private final Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String outPut5v() {
        return transform(adaptee.outPut220v());
    }

    private String transform(String rawVolt) {
        return Integer.parseInt(rawVolt.replace("V", "")) / 44 + "V";
    }
}
