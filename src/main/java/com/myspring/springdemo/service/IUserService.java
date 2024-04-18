package com.myspring.springdemo.service;

import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.entity.dto.LoginFormDTO;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huanghao
 */
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
     * @param email        用户email（记录此信息以发送结果给用户）
     * @return 上传的结果信息
     */
    RestResult<String> upload(MultipartFile pdb, String taskName, String fullSequence, String email);

    /**
     * 用于计算两链之间最小距离
     *
     * @param pdbId          pdb的位移标识
     * @return Python对两条链的计算结果（包含最小距离矩阵图和字符串序列）
     */
    RestResult<Pair<String, String>> calcDistMatrix(String pdbId);
}
