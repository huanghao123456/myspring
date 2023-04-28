package com.myspring.springdemo.service.impl;

import com.myspring.springdemo.config.RabbitMqConfig;
import com.myspring.springdemo.entity.vo.GpuVO;
import com.myspring.springdemo.service.IManagerService;
import com.myspring.springdemo.common.utils.manager.OsSystem;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>获取此刻GPU占用信息，本质是Java调用C语言的Runtime.getRuntime().exec(“nvidia-smi.exe”)来实现
 * <p>此方法通过字符串硬分割来实现，后续可优化
 * <p>GPU参数如下
 * <pre>
 * Wed Apr 26 12:23:23 2023
 * +-----------------------------------------------------------------------------+
 * | NVIDIA-SMI 430.40       Driver Version: 430.40       CUDA Version: 10.1     |
 * |-------------------------------+----------------------+----------------------+
 * | GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
 * | Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
 * |===============================+======================+======================|
 * |   0  TITAN X (Pascal)    Off  | 00000000:82:00.0  On |                  N/A |
 * | 23%   36C    P8     9W / 250W |    163MiB / 12195MiB |      0%      Default |
 * +-------------------------------+----------------------+----------------------+
 * |   1  TITAN Xp            Off  | 00000000:83:00.0 Off |                  N/A |
 * | 23%   37C    P8     9W / 250W |      2MiB / 12196MiB |      0%      Default |
 *
 * +-------------------------------+----------------------+----------------------+
 *
 * +-----------------------------------------------------------------------------+
 * | Processes:                                                       GPU Memory |
 * |  GPU       PID   Type   Process name                             Usage      |
 * |=============================================================================|
 * |    0     16898      G   /usr/bin/X                                    81MiB |
 * |    0     17222      G   /usr/bin/gnome-shell                          80MiB |
 * +-----------------------------------------------------------------------------+
 * </pre>
 *
 * @author huanghao
 */
@Slf4j
@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<GpuVO> getGpuStatus() {
        Process process = null;

        // 判断操作系统类别
        try {
            if (OsSystem.isWindows()) {
                process = Runtime.getRuntime().exec("nvidia-smi.exe");
            } else if (OsSystem.isLinux()) {
                String[] shell = {"/bin/bash", "-c", "nvidia-smi"};
                process = Runtime.getRuntime().exec(shell);
            } else {
                log.error("仅支持Linux和Windows系统，不支持查看该系统GPU！");
            }
            process.getOutputStream().close();
        } catch (Exception e) {
            log.error("nvidia-smi脚本执行失败！", e);
            return Collections.emptyList();
        }

        // 通过字符串分割 获取GPU实时信息
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder wholeInfo = new StringBuilder();
        String line = "";
        try {
            while (null != (line = reader.readLine())) {
                wholeInfo.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error("GPU文本信息读取错误！", e);
            return Collections.emptyList();
        }

        String gpus = wholeInfo.toString();
        // 分离无用信息
        String[] split = gpus.split("\\|===============================\\+======================\\+======================\\|");
        String[] gpusInfo = split[1].split("                                                                               ");
        // 分割多个gpu
        String[] gpuInfo = gpusInfo[0].split("\\+-------------------------------\\+----------------------\\+----------------------\\+");
        List<GpuVO> gpuInfoList = new ArrayList<>();
        for (int i = 0; i < gpuInfo.length - 1; i++) {
            GpuVO gpuInfo1 = new GpuVO();
            String[] nameAndInfo = gpuInfo[i].split("\n");
            //只要第二块的数据
            String[] split1 = nameAndInfo[1].split("\\|")[1].split("\\s+");
            gpuInfo1.setNumber(Integer.parseInt(split1[1]));
            StringBuilder name = new StringBuilder();
            for (int j = 2; j < split1.length - 1; j++) {
                name.append(split1[j]).append(" ");
            }
            gpuInfo1.setName(name.toString());
            String[] info = nameAndInfo[2].split("\\|")[2].split("\\s+");
            gpuInfo1.setUsedMemory(info[1]);
            gpuInfo1.setTotalMemory(info[3]);
            int usable = Integer.parseInt(gpuInfo1.getTotalMemory().split("MiB")[0]) - Integer.parseInt(gpuInfo1.getUsedMemory().split("MiB")[0]);
            gpuInfo1.setUseAbleMemory(usable + "MiB");
            Double usageRate = Integer.parseInt(gpuInfo1.getUsedMemory().split("MiB")[0]) * 100.00 / Integer.parseInt(gpuInfo1.getTotalMemory().split("MiB")[0]);
            gpuInfo1.setUsageRate(usageRate);
            gpuInfoList.add(gpuInfo1);

            log.info("GPU_No." + i + " usage:" + usageRate);
        }
        return gpuInfoList;
    }

    public Integer getMqMsgCount() {
        AMQP.Queue.DeclareOk declareOk = rabbitTemplate.execute(new ChannelCallback<AMQP.Queue.DeclareOk>() {
            @Override
            public AMQP.Queue.DeclareOk doInRabbit(Channel channel) throws Exception {
                return channel.queueDeclarePassive(RabbitMqConfig.PDB_QUEUE_NAME);
            }
        });

        return declareOk.getMessageCount();
    }
}

