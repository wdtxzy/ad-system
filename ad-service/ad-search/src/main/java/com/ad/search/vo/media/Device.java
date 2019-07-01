package com.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/1 23:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    /**
     * 设备id
     */
    private String deviceCode;

    /**
     * mac地址
     */
    private String mac;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 机型编码
     */
    private String model;

    /**
     * 分辨率尺寸
     */
    private String displaySize;

    /**
     * 屏幕尺寸
     */
    private String screenSize;

    /**
     * 设备号信息
     */
    private String serialName;
}
