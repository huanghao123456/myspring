package com.myspring.springdemo.entity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myspring.springdemo.annotation.TaskNameValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Priority;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录信息DTO
 * @author huanghao
 */
@Data
public class LoginFormDTO {
    /**
     * 客户email，同时作为登录账号
     * <p>TODO: 此处两个注解在实际校验过程中无顺序之分，当入参是错误的邮箱时，有时会报无权限的错误，需要改进
     */
    @Email(message = "邮箱格式错误！")
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.])+\\@(jiangnan.edu.cn|stu.jiangnan.edu.cn)$", message = "该邮箱无访问权限!")
    private String email;
}
