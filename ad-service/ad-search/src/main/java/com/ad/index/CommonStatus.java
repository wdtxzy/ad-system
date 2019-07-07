package com.ad.index;

import lombok.Getter;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/7 22:34
 */
@Getter
public enum CommonStatus {

    VALID(1,"有效状态"),
    INVALID(0,"无效信息");

    private Integer status;

    private String desc;

    CommonStatus(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }
}
