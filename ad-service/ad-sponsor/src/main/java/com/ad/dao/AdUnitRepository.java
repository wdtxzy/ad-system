package com.ad.dao;

import com.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 21:44
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanAndUnitName(Long planId, String unitName);

    List<AdUnit> findByUnitStatus(Integer status);
}
