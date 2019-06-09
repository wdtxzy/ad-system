package com.ad.constant;

import lombok.Getter;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 20:02
 */
@Getter
public enum  CommonStatus {

    VALID(1, "有效状态"),
        INVALID(0,"无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }
}
