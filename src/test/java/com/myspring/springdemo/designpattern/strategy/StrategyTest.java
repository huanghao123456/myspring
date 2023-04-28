package com.myspring.springdemo.designpattern.strategy;

import com.myspring.springdemo.designpattern.strategy.impl.AdvanceMemberStrategy;
import com.myspring.springdemo.designpattern.strategy.impl.IntermediateMemberStrategy;
import com.myspring.springdemo.designpattern.strategy.impl.PrimaryMemberStrategy;
import org.junit.jupiter.api.Test;

/**
 * @author: hhuang
 * @date: 2022/10/12
 */
public class StrategyTest {

    @Test
    void test1() {
        Context context = new Context(new AdvanceMemberStrategy());
        System.out.println(context.calc(100.0));
    }

    @Test
    void test2() {
        Context context = new Context(new IntermediateMemberStrategy());
        System.out.println(context.calc(100.0));
    }

    @Test
    void test3() {
        Context context = new Context(new PrimaryMemberStrategy());
        System.out.println(context.calc(100.0));
    }
}
