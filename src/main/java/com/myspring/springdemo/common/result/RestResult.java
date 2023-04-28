package com.myspring.springdemo.common.result;

import java.io.Serializable;

/**
 * @param <T> 响应体类型
 * @author huanghao
 */
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 6095433538316185018L;

    private int code;

    private String message;

    private T data;

    public RestResult() {
    }

    public RestResult(int code, String message, T data) {
        this.code = code;
        this.setMessage(message);
        this.data = data;
    }

    public RestResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public RestResult(int code, String message) {
        this.code = code;
        this.setMessage(message);
    }

    public static <T> RestResultBuilder<T> builder() {
        return new RestResultBuilder<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean ok() {
        return this.code == 0 || this.code == 200;
    }

    @Override
    public String toString() {
        return "RestResult{" + "code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
    }

    public static final class RestResultBuilder<T> {

        private int code;

        private String message;

        private T data;

        private RestResultBuilder() {
        }

        public RestResultBuilder<T> withCode(int code) {
            this.code = code;
            return this;
        }

        public RestResultBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public RestResultBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }


        /**
         * Build result.
         *
         * @return result
         */
        public RestResult<T> build() {
            RestResult<T> restResult = new RestResult<>();
            restResult.setCode(code);
            restResult.setMessage(message);
            restResult.setData(data);
            return restResult;
        }
    }
}