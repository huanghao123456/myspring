package com.myspring.springdemo.annotation;

import com.myspring.springdemo.common.param.TaskNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author huanghao
 */
@Documented
@Constraint(validatedBy = {TaskNameValidator.class})
@Target({PARAMETER, TYPE_USE, TYPE_PARAMETER})
@Retention(RUNTIME)
public @interface TaskNameValid {
    String message() default "任务名格式不正确！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
