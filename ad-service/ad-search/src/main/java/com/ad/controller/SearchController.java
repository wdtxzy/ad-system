package com.ad.controller;

import com.ad.annotation.IgnoreResponseAdvice;
import com.ad.client.vo.AdPlan;
import com.ad.client.vo.AdPlanGetRequest;
import com.ad.client.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/15 23:19
 */
@Slf4j
@RestController
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(
            @RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansByRibbon ->{}", JSON.toJSONString(request));
        return restTemplate.postForEntity(
          "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
          request,
          CommonResponse.class
        ).getBody();
    }
}
