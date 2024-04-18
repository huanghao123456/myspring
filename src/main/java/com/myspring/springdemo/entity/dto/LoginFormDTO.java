package com.myspring.springdemo.entity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myspring.springdemo.annotation.TaskNameValid;
import com.myspring.springdemo.validator.JNUEmail;
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
     */
    @JNUEmail
    private String email;
}
