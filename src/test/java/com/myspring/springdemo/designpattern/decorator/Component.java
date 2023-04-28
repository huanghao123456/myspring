package com.myspring.springdemo.designpattern.decorator;

/**
 * 装饰器模式
 * <p>不改变原功能，在原功能的基础上做增强</p>
 * <p>要了解装饰器模式和代理模式的差别</p>
 * <li>
 *   装饰器的目标实例是从构造器传入，即
 *     <pre> {@code
 *       public Decorator(Component component) {
 *         this.component = component;
 *       }
 *     }</pre>
 * </li>
 * <li>
 *   而代理模式的目标实例是自己创建，即
 *     <pre> {@code
 *       public Proxy() {
 *         this.instance = new Instance()
 *       }
 *     }</pre>
 * </li>
 *
 * @author: hhuang
 * @date: 2022/10/12
 */
public interface Component {
    void operation();
}
