package com.ad.controller;

import com.ad.exception.AdException;
import com.ad.service.ICreativeService;
import com.ad.client.vo.CreativeRequest;
import com.ad.client.vo.CreativeResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:53
 */
@Slf4j
@RestController
public class CreativeOpController {

    @Autowired
    private ICreativeService creativeService;

    @PostMapping("/creative/create")
    public CreativeResponse createCreative(
            @RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor: creative-create -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
