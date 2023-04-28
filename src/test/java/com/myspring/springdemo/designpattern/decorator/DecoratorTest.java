package com.myspring.springdemo.designpattern.decorator;

import com.myspring.springdemo.designpattern.decorator.impl.ConcreteComponent;
import com.myspring.springdemo.designpattern.decorator.impl.ComponentDecorator1;
import com.myspring.springdemo.designpattern.decorator.impl.ComponentDecorator2;
import org.junit.jupiter.api.Test;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public class DecoratorTest {

    @Test
    void fundamentalTest() {
        Component component = new ConcreteComponent();
        component.operation();
    }

    @Test
    void decorativeTest() {
        Component component = new ComponentDecorator1(new ConcreteComponent());
        component.operation();
    }

    @Test
    void complexDecorativeTest() {
        Component component = new ComponentDecorator2(new ComponentDecorator1(new ConcreteComponent()));
        component.operation();
    }
}
