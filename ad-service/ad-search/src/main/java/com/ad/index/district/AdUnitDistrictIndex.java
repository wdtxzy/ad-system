package com.ad.index.district;

import com.ad.index.IndexAware;
import com.ad.search.vo.feature.DistrictFeature;
import com.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.set.CompositeSet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 10:32
 * 地域限制的索引
 */
@Slf4j
@Component
public class AdUnitDistrictIndex implements IndexAware<String, Set<Long>> {

    /**
     * <province-city,unitId Set>
     */
    private static Map<String, Set<Long>> districtUnitMap;

    /**
     * <unitId, province-city Set>
     */
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {

        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, districtUnitMap, CompositeSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(
                    unitId, unitDistrictMap, CompositeSet::new
            );
            districts.add(key);
        }
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, districtUnitMap, CompositeSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(
                    unitId, unitDistrictMap, CompositeSet::new
            );
            districts.remove(key);
        }
    }

    public boolean match(Long adUnitId, List<DistrictFeature.ProvinceAndCity> districts){
        if (unitDistrictMap.containsKey(adUnitId)&& CollectionUtils.isNotEmpty(unitDistrictMap.get(adUnitId))){
            Set<String> unitDistricts = unitDistrictMap.get(adUnitId);
            List<String> targetDistricts = districts.stream().map(
                    x->CommonUtils.stringConcat(x.getProvince(),x.getCity())
            ).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts,unitDistricts);
        }
        return false;
    }

}
