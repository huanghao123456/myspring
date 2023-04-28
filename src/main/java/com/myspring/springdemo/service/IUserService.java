package com.myspring.springdemo.service;

import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.entity.dto.LoginFormDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    /**
     * 用户登录
     *
     * @param loginFormDTO 登录信息的DTO
     * @return 登录的结果信息
     */
    Boolean login(LoginFormDTO loginFormDTO);

    /**
     * 用户上传pdb文件
     *
     * @param pdb          pdb文件
     * @param taskName     任务名称
     * @param fullSequence pdb的序列号
     * @return 上传的结果信息
     */
    RestResult<String> upload(MultipartFile pdb, String taskName, String fullSequence, String email);
}
