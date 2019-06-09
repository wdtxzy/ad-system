package com.ad.constant;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 21:35
 */
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
