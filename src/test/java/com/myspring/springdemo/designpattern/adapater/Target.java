package com.myspring.springdemo.designpattern.adapater;

/**
 * 采用对象适配器进行适配，该适配器采用的是接口方法
 * <p>应避免使用类适配器，类适配器采用的是继承，会造成父类方法的暴露</p>
 *
 * @author: hhuang
 * @date: 2022/10/20
 */
public interface Target {
    String outPut5v();
}
