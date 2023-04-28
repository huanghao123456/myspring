package com.myspring.springdemo.entity.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GpuVO {
    /**
     * GPU编号
     */
    private Integer number;

    /**
     * 显卡名称
     */
    private String name;

    /**
     * 总内存
     */
    private String totalMemory;

    /**
     * 使用内存
     */
    private String usedMemory;

    /**
     * 可用内存
     */
    private String useAbleMemory;

    /**
     * 使用率
     */
    private Double usageRate;
}
