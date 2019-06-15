package com.ad.service;

import com.ad.entity.AdPlan;
import com.ad.exception.AdException;
import com.ad.client.vo.AdPlanGetRequest;
import com.ad.client.vo.AdPlanRequest;
import com.ad.client.vo.AdPlanResponse;

import java.util.List;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/10 23:03
 */
public interface IAdPlanService {

    /**
     * 增加投放计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    AdPlanResponse updateAdplan(AdPlanRequest request) throws AdException;

    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
