package com.ad.service.impl;

import com.ad.constant.Constants;
import com.ad.dao.AdPlanRepository;
import com.ad.dao.AdUserRepository;
import com.ad.entity.AdPlan;
import com.ad.entity.AdUser;
import com.ad.exception.AdException;
import com.ad.service.IAdPlanService;
import com.ad.utils.CommonUtils;
import com.ad.vo.AdPlanGetRequest;
import com.ad.vo.AdPlanRequest;
import com.ad.vo.AdPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/10 23:15
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    @Autowired
    private AdUserRepository userRepository;

    @Autowired
    private AdPlanRepository planRepository;

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //确保关联的User对象存在
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldAdPlan = planRepository.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName()
        );
        if (oldAdPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newAdPlan = planRepository.save(
                new AdPlan(request.getUserId(), request.getPlanName(),
                        CommonUtils.parseStringDate(request.getStartDate()),
                        CommonUtils.parseStringDate(request.getEndDate())));

        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        return null;
    }

    @Override
    public AdPlanResponse updateAdplan(AdPlanRequest request) throws AdException {
        return null;
    }

    @Override
    public void deleteAdPlan(AdPlanRequest request) throws AdException {

    }
}
