package com.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/1 23:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    /**
     * 经度
     */
    private Float latitude;

    /**
     * 维度
     */
    private Float longtitude;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;
}
