package com.myspring.springdemo.entity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myspring.springdemo.annotation.TaskNameValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * pdb文件信息DTO
 * @author huanghao
 */
@Valid
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PdbFileInfoDTO {
    /**
     * 客户上传的pdb文件
     */
    private CommonsMultipartFile pdb;

    /**
     * 执行此次GPU计算任务的任务名称
     */
    @TaskNameValid
    private String taskName;

    /**
     * 该任务的完整序列号,同时也是任务的唯一标识
     */
    @NotBlank
    private String fullSequence;

    /**
     * upload中不应该含email，因为在login时用户已经提供过，当搭建数据库后可以记录用户信息时，应优化掉该字段
     */
    @NotBlank
    private String email;
}
