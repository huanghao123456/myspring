package com.myspring.springdemo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huanghao
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JNUEmailValidator.class)
public @interface JNUEmail {
    String message() default "邮箱格式或权限不正确！";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}



