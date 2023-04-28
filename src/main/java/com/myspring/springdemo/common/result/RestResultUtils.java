/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myspring.springdemo.common.result;


/**
 * Rest result utils.
 * <p>
 * 将原本的抽象类方法改为接口静态方法是 SonarRule Java:S1118 的建议
 *
 * @author huanghao
 */
public interface RestResultUtils {
    String ENUM_SUCCESS = "success";
    String ENUM_FAILED = "failed";

    static <T> RestResult<T> success() {
        return RestResult.<T>builder().withCode(2000).withMessage(ENUM_SUCCESS).build();
    }

    static <T> RestResult<T> success(T data) {
        return RestResult.<T>builder().withCode(2000).withMessage(ENUM_SUCCESS).withData(data).build();
    }

    static <T> RestResult<T> success(String message, T data) {
        return RestResult.<T>builder().withCode(2000).withMessage(message).withData(data).build();
    }

    static <T> RestResult<T> success(int code, T data) {
        return RestResult.<T>builder().withCode(code).withMessage(ENUM_SUCCESS).withData(data).build();
    }

    static <T> RestResult<T> failed() {
        return RestResult.<T>builder().withCode(500).build();
    }

    static <T> RestResult<T> failed(String message) {
        return RestResult.<T>builder().withCode(5000).withMessage(ENUM_FAILED).withMessage(message).build();
    }

    static <T> RestResult<T> failed(int code, T data) {
        return RestResult.<T>builder().withCode(code).withMessage(ENUM_FAILED).withData(data).build();
    }

    static <T> RestResult<T> failed(int code, T data, String message) {
        return RestResult.<T>builder().withCode(code).withData(data).withMessage(message).build();
    }

    static <T> RestResult<T> failedWithMsg(int code, String message) {
        return RestResult.<T>builder().withCode(code).withMessage(message).build();
    }

    static <T> RestResult<T> buildResult(ResultCode resultCode, T data) {
        return RestResult.<T>builder().withCode(resultCode.getCode()).withMessage(resultCode.getMessage()).withData(data).build();
    }

    static <T> RestResult<T> buildResult(ResultCode resultCode) {
        return RestResult.<T>builder().withCode(resultCode.getCode()).withMessage(resultCode.getMessage()).build();
    }
}
