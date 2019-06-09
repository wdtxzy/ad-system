package com.ad.service;

import com.ad.exception.AdException;
import com.ad.vo.CreateUserRequest;
import com.ad.vo.CreateUserResponse;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 21:59
 */
public interface IAdUserService {

    /**
     * <h2>创建用户</h2>
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
