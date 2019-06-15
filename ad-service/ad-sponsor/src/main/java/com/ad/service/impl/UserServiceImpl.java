package com.ad.service.impl;

import com.ad.constant.Constants;
import com.ad.dao.AdUserRepository;
import com.ad.entity.AdUser;
import com.ad.exception.AdException;
import com.ad.service.IAdUserService;
import com.ad.utils.CommonUtils;
import com.ad.vo.CreateUserRequest;
import com.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 22:04
 */
@Slf4j
@Service
public class UserServiceImpl implements IAdUserService {

    @Autowired
    private AdUserRepository adUserRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = adUserRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = adUserRepository.save(new AdUser(
                request.getUsername(), CommonUtils.md5(request.getUsername())
        ));

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(),
                newUser.getToken(), newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
