package com.myspring.springdemo.designpattern.ResponsibilityChain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: hhuang
 * @date: 2022/10/20
 */
@Data
@AllArgsConstructor
public abstract class Handler {

    Handler next;

    abstract boolean process(Request request);
}
