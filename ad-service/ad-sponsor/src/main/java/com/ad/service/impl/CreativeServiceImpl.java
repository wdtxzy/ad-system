package com.ad.service.impl;

import com.ad.dao.CreativeRepository;
import com.ad.entity.Creative;
import com.ad.exception.AdException;
import com.ad.service.ICreativeService;
import com.ad.vo.CreativeRequest;
import com.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/13 00:01
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    @Autowired
    private CreativeRepository creativeRepository;

    @Override
    public CreativeResponse createCreative(
            CreativeRequest request) throws AdException {
        //TODO 增加参数校验
        Creative creative = creativeRepository.save(request.converToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
