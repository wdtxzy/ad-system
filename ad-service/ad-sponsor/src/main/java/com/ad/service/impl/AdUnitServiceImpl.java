package com.ad.service.impl;

import com.ad.constant.Constants;
import com.ad.dao.AdPlanRepository;
import com.ad.dao.AdUnitRepository;
import com.ad.entity.AdPlan;
import com.ad.entity.AdUnit;
import com.ad.exception.AdException;
import com.ad.service.IAdUnitService;
import com.ad.vo.AdUnitResponse;
import com.ad.vo.AdUnitResuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/11 23:23
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    @Autowired
    private AdPlanRepository planRepository;

    @Autowired
    private AdUnitRepository unitRepository;

    @Override
    public AdUnitResponse createUnit(AdUnitResuest resuest) throws AdException {
        if (!resuest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> adPlan = planRepository.findById(resuest.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdUnit oldUnit = unitRepository.findByPlanAndUnitName(
                resuest.getPlanId(), resuest.getUnitName()
        );
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdunit = unitRepository.save(
                new AdUnit(resuest.getPlanId(), resuest.getUnitName(),
                        resuest.getPositionType(), resuest.getBudget())
        );
        return new AdUnitResponse(newAdunit.getId(), newAdunit.getUnitName());
    }
}
