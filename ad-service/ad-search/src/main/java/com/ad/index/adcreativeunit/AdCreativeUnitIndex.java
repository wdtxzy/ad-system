package com.ad.index.adcreativeunit;

import com.ad.index.IndexAware;
import com.ad.index.adunit.AdUnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 10:52
 * 创意与推广关系的索引
 */
@Slf4j
@Component
public class AdCreativeUnitIndex implements IndexAware<String, AdCreativeUnitObject> {

    /**
     * <adId-unitId,AdCreativeUnitObject>
     */
    private static Map<String, AdCreativeUnitObject> objectMap;

    /**
     * <adId,unitId Set>
     */
    private static Map<Long, Set<Long>> creativeUnitMap;

    /**
     * <unitId,AdId Set>
     */
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdCreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, AdCreativeUnitObject value) {
        objectMap.put(key, value);

        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());
    }

    @Override
    public void update(String key, AdCreativeUnitObject value) {
        log.error("CreativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, AdCreativeUnitObject value) {
        objectMap.remove(key);

        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getAdId());
        }
    }

    public List<Long> selectAds(List<AdUnitObject> unitObjects){

        if (CollectionUtils.isEmpty(unitObjects)){
            return Collections.emptyList();
        }

        List<Long> result = new ArrayList<>();

        for(AdUnitObject unitObject : unitObjects){
            Set<Long> adIds = unitCreativeMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adIds)){
                result.addAll(adIds);
            }
        }
        return result;
    }
}
