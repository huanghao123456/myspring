package com.myspring.springdemo.designpattern.adapater;

/**
 * @author: hhuang
 * @date: 2022/10/20
 */
public class AdapterTest {

    public static void main(String[] args) {
        System.out.print("原始电压");
        Adaptee adaptee = new Adaptee();
        System.out.println(adaptee.outPut220v());

        System.out.print("适配器适配后的电压");
        Adapter adapter = new Adapter(adaptee);
        System.out.println(adapter.outPut5v());
    }
}
