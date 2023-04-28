package com.myspring.springdemo.controller;

import com.myspring.springdemo.common.result.RestResult;
import com.myspring.springdemo.common.result.RestResultUtils;
import com.myspring.springdemo.entity.vo.GpuVO;
import com.myspring.springdemo.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 管理员对任务处理，硬件性能监控的控制器
 *
 * @author huanghao
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private IManagerService managerService;

    @GetMapping("/gpu")
    public RestResult<Collection<GpuVO>> getGpuInfo() {
        return RestResultUtils.success(managerService.getGpuStatus());
    }

    @GetMapping("/msgCount")
    public RestResult<Integer> getMqMessageCount() {
        return RestResultUtils.success(managerService.getMqMsgCount());
    }
}
