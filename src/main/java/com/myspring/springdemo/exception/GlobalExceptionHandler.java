package com.myspring.springdemo.exception;


import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.common.result.RestResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

/**
 * 全局异常处理
 * @author huanghao
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static int PARAM_FAIL_CODE = 4000;

    /**
     * 参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        return RestResultUtils.failedWithMsg(PARAM_FAIL_CODE, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult<String> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage());
        return RestResultUtils.failedWithMsg(PARAM_FAIL_CODE, e.getMessage());
    }
}