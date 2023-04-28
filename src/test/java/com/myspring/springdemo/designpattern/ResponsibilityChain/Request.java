package com.myspring.springdemo.designpattern.ResponsibilityChain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: hhuang
 * @date: 2022/10/20
 */
@AllArgsConstructor
@Data
public class Request {

    /**
     * 是否已登录
     */
    private boolean loggedOn;
    /**
     * 访问频率是否正常
     */
    private boolean frequentOK;
    /**
     * 用户是否对该项有访问权
     */
    private boolean isPermits;
    /**
     * 是否包含敏感词
     */
    private boolean containsSensitiveWords;

    /**
     * 构造者模式
     */
    static class RequestBuilder {

        private boolean loggedOn;
        private boolean frequentOK;
        private boolean isPermits;
        private boolean containsSensitiveWords;

        RequestBuilder loggedOn(boolean loggedOn) {
            this.loggedOn = loggedOn;
            return this;
        }

        RequestBuilder frequentOK(boolean frequentOK) {
            this.frequentOK = frequentOK;
            return this;
        }

        RequestBuilder isPermits(boolean isPermits) {
            this.isPermits = isPermits;
            return this;
        }

        RequestBuilder containsSensitiveWords(boolean containsSensitiveWords) {
            this.containsSensitiveWords = containsSensitiveWords;
            return this;
        }

        public Request build() {
            return new Request(loggedOn, frequentOK, isPermits, containsSensitiveWords);
        }
    }
}
