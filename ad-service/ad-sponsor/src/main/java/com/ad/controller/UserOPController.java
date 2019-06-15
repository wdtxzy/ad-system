package com.ad.controller;

import com.ad.exception.AdException;
import com.ad.service.IAdUserService;
import com.ad.client.vo.CreateUserRequest;
import com.ad.client.vo.CreateUserResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:05
 */
@Slf4j
@RestController
public class UserOPController {

    @Autowired
    private IAdUserService userService;

    @PatchMapping("/user/create")
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request)throws AdException {
        log.info("ad-sponsor:create-user ->{}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
