package com.ad.controller;

import com.ad.exception.AdException;
import com.ad.service.IAdUnitService;
import com.ad.client.vo.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:17
 */
@Slf4j
@RestController
@RequestMapping("/ad-unit")
public class AdUnitOPController {

    @Autowired
    private IAdUnitService adUnitService;

    @PostMapping("/create")
    public AdUnitResponse createAdUnit(
            @RequestBody AdUnitResuest resuest)throws AdException {
        log.info("ad-sponsor: AdUnit-create ->{}", JSON.toJSONString(resuest));
        return adUnitService.createUnit(resuest);
    }

    @PostMapping("/keyword/create")
    public AdUnitKeywordResponse createAdUnitKeyword(
            @RequestBody AdUnitKeywordRequest request)throws AdException{
        log.info("ad-sponsor: adUnit-keyword-create ->{}",JSON.toJSONString(request));
        return adUnitService.createUnitKeyword(request);
    }

    @PostMapping("/it/create")
    public AdUnitItResponse createAdUnitIt(
            @RequestBody AdUnitItRequest request)throws AdException{
        log.info("ad-sponsor: adUnit-it-create ->{}",JSON.toJSONString(request));
        return adUnitService.createUnitIt(request);
    }

    @PostMapping("/district/create")
    public AdUnitDistrictResponse createAdUnitDistrict(
            @RequestBody AdUnitDistrictRequest request)throws AdException{
        log.info("ad-sponsor: adUnit-district-create ->{}",JSON.toJSONString(request));
        return adUnitService.createUnitDistrict(request);
    }

    @PostMapping("/creative-unit/create")
    public CreativeUnitResponse createCreativeUnit(
            @RequestBody CreativeUnitRequest request)throws AdException{
        log.info("ad-sponsor: creative-unit-create ->{}",JSON.toJSONString(request));
        return adUnitService.createCreativeUnit(request);
    }
}
