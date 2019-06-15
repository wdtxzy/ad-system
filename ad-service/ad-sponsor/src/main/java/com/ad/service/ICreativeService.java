package com.ad.service;

import com.ad.exception.AdException;
import com.ad.client.vo.CreativeRequest;
import com.ad.client.vo.CreativeResponse;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:55
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
