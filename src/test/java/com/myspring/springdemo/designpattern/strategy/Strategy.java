package com.myspring.springdemo.designpattern.strategy;

import java.util.Comparator;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 策略模式
 * <p>目的是去除冗余的if-else语句</p>
 * <p>假设一件商品，其折扣优惠为：</p>
 * <ul>
 *  <li>对于初级会员不打折</li>
 *  <li>对于中级会员打9折</li>
 *  <li>对于高级会员打8折</li>
 * </ul>
 * <p>采用如下的策略模式来完成设计</p>
 * <p>应注意到{@link ThreadPoolExecutor}中的{@code RejectedExecutionHandler}和{@code Arrays.sort()}中的{@link Comparator}均采用了策略模式</p>
 *
 * @author: hhuang
 * @date: 2022/10/12
 */
public interface Strategy {

    double calcPrice(double price);
}
