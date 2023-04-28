package com.myspring.springdemo.common.param;

import com.myspring.springdemo.annotation.TaskNameValid;
import com.myspring.springdemo.common.utils.client.RegexPatternsUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author huanghao
 */
@Component
public class TaskNameValidator implements ConstraintValidator<TaskNameValid, String> {
    @Override
    public boolean isValid(String taskName, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(taskName)) {
            return false;
        }

        if (!taskName.matches(RegexPatternsUtils.INVALID_TASK_NAME_SIGN)) {
            return false;
        }

        return taskName.length() <= 30;
    }
}
