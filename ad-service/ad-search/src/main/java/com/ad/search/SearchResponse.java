package com.ad.search;

import com.ad.index.creative.AdCreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/1 23:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String,List<Creative>> adSlotToAds = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative{

        private Long adId;

        private String adUrl;

        private Integer width;

        private Integer height;

        private Integer type;

        private Integer materialType;

        /**
         * 展示检测url
         */
        private List<String> showMonitorUrl =
                Arrays.asList("www.baidu.com","www.baidu.com");

        /**
         * 点击检测url
         */
        private List<String> clickMonitorUrl =
                Arrays.asList("www.baidu.com","www.baidu.com");
    }

    public static Creative convert(AdCreativeObject object){
        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }
}
