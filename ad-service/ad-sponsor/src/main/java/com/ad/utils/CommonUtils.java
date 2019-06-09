package com.ad.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 22:11
 */
public class CommonUtils {

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
