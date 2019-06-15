package com.ad.controller;

import com.ad.entity.AdPlan;
import com.ad.exception.AdException;
import com.ad.service.IAdPlanService;
import com.ad.client.vo.AdPlanGetRequest;
import com.ad.client.vo.AdPlanRequest;
import com.ad.client.vo.AdPlanResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:08
 */
@Slf4j
@RestController
@RequestMapping("/ad-plan")
public class AdPlanOPController {

    @Autowired
    private IAdPlanService planService;

    @PostMapping("/create")
    public AdPlanResponse createAdPlan(
            @RequestBody AdPlanRequest request)throws AdException {
        log.info("ad-sponsor: create-AdPlan ->{}", JSON.toJSONString(request));
        return planService.createAdPlan(request);
    }

    @PostMapping("/queryByIds")
    public List<AdPlan> getAdPlanByIds(
            @RequestBody AdPlanGetRequest request)throws AdException{
        log.info("ad-sponsor: get-AdPlan-By-Ids ->{}",JSON.toJSONString(request));
        return planService.getAdPlanByIds(request);
    }

    @PostMapping("/update")
    public AdPlanResponse updateAdPlan(
            @RequestBody AdPlanRequest request)throws AdException{
        log.info("ad-sponsor: update-AdPlan ->{}",JSON.toJSONString(request));
        return planService.updateAdplan(request);
    }

    @PostMapping("/delete")
    public void deleteAdPlan(
            @RequestBody AdPlanRequest request)throws AdException{
        log.info("ad-sponsor: delete-AdPlan ->{}",JSON.toJSONString(request));
        planService.deleteAdPlan(request);
    }
}
