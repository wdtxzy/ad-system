package com.ad.service;

import com.ad.exception.AdException;
import com.ad.vo.AdUnitResponse;
import com.ad.vo.AdUnitResuest;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/11 23:18
 */
public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitResuest resuest) throws AdException;
}
