package com.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/1 23:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    /**
     * 广告位编码
     */
    private String adSlotCode;

    /**
     * 流量类型
     */
    private Integer positionType;

    /**
     * 广告的宽
     */
    private Integer width;

    /**
     * 广告的高
     */
    private Integer height;

    /**
     * 广告的物料类型
     */
    private List<Integer> type;

    /**
     * 最低出价
     */
    private Integer minCpm;
}
