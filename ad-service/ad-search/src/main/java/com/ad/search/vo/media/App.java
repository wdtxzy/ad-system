package com.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/1 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    /**
     * 应用编码
     */
    private String appCide;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用包名
     */
    private String packageName;

    /**
     * activity名称
     */
    private String activityName;

}
