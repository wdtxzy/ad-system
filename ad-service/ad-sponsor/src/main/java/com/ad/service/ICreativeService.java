package com.ad.service;

import com.ad.exception.AdException;
import com.ad.vo.CreativeRequest;
import com.ad.vo.CreativeResponse;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:55
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
