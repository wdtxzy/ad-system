package com.ad.service;

import com.ad.exception.AdException;
import com.ad.vo.*;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/11 23:18
 */
public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitResuest request) throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request)
            throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request)
            throws AdException;
}
