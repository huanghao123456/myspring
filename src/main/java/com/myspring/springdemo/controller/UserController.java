package com.myspring.springdemo.controller;

import com.myspring.springdemo.annotation.TaskNameValid;
import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.common.result.RestResultUtils;
import com.myspring.springdemo.entity.dto.LoginFormDTO;
import com.myspring.springdemo.service.IUserService;
import org.springframework.data.util.Pair;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户操作相关的控制器
 *
 * @author huanghao
 */
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    private final IUserService userService;

    @PostMapping("/login")
    public RestResult<Boolean> login(@Validated @RequestBody LoginFormDTO loginFormDTO) {
        return RestResultUtils.success(userService.login(loginFormDTO));
    }

    @PostMapping("/upload")
    public RestResult<String> uploadPdb(@NotNull @RequestBody MultipartFile pdb,
                                        @TaskNameValid @RequestParam("task_name") String taskName,
                                        @NotBlank @RequestParam("full_sequence") String fullSequence,
                                        @NotBlank @RequestParam("email") String email
    ) {
        return userService.upload(pdb, taskName, fullSequence, email);
    }

    // TODO: 改为实际结果内容
    @GetMapping("/result")
    public RestResult<Map<String, Object>> getPdbResult(@NotBlank @RequestParam("task_name") String taskName) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("des1", "123");
        map.put("des2", "234");
        map.put("des3", "435");
        return RestResultUtils.success("开发完毕后，这将会是一个关于结果的大json", map);

    }

    @PostMapping("/calcDistMatrix")
    public RestResult<Pair<String, String>> calcDistMatrix(@NotBlank @RequestParam("pdbId") String pdbId) {
        return userService.calcDistMatrix(pdbId);
    }
}
