package com.myspring.springdemo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JNUEmailValidator implements ConstraintValidator<JNUEmail, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 可根据需求调整是否允许 null
        if (value == null) {
            return false;
        }

        // 先验证邮箱格式
        if (!isValidFormat(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("邮箱格式错误！").addConstraintViolation();
            return false;
        }

        // 再验证权限 (仅江南大学的邮箱有权限)
        if (!hasPermission(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("该邮箱无访问权限！").addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isValidFormat(String email) {
        // 邮箱格式正则匹配
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    private boolean hasPermission(String email) {
        // 权限校验逻辑
        return email.matches("^([A-Za-z0-9_\\-\\.])+\\@(jiangnan.edu.cn|stu.jiangnan.edu.cn)$");
    }
}