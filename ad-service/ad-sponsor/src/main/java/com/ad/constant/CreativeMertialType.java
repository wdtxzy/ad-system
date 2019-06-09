package com.ad.constant;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 21:36
 */
public enum CreativeMertialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "MP4"),
    AVI(4, "AVI"),

    TXt(5, "txt");

    private int type;
    private String desc;

    CreativeMertialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
