package com.myspring.springdemo.service;

import com.myspring.springdemo.entity.vo.GpuVO;

import java.util.Collection;
import java.util.List;

/**
 * @author huanghao
 */
public interface IManagerService {

    /**
     * 返回GPU状态信息
     * @return 单（多）个GPU状态信息
     */
    Collection<GpuVO> getGpuStatus();

    /**
     * 获取此刻消息队列中 堆积的消息个数
     * @return 消息队列中堆积的消息个数
     */
    Integer getMqMsgCount();
}
