package com.ad.index.interest;

import com.ad.index.IndexAware;
import com.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.set.CompositeSet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 10:17
 * 兴趣索引
 */
@Slf4j
@Component
public class AdUnitItIndex implements IndexAware<String, Set<Long>> {

    /**
     * <itTag, adUnitId set>
     */
    private static Map<String, Set<Long>> itUnitMap;

    /**
     * <unitId, itTag set>
     */
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, itUnitMap, CompositeSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getOrCreate(
                    unitId, unitItMap, CompositeSet::new
            );
            its.add(key);
        }
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, itUnitMap, CompositeSet::new
        );
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> itTagSet = CommonUtils.getOrCreate(
                    unitId, unitItMap, CompositeSet::new
            );
            itTagSet.remove(key);
        }
    }

    public boolean match(Long unitId, List<String> itTags) {

        if (unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            Set<String> unitKeyword = unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(itTags, unitKeyword);
        }
        return false;
    }
}
