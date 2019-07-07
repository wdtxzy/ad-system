package com.ad.index.creative;

import com.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 10:47
 * 广告创意索引
 */
@Slf4j
@Component
public class AdCreativeIndex implements IndexAware<Long, AdCreativeObject> {

    private static Map<Long, AdCreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public List<AdCreativeObject> fetch(Collection<Long> adIds){
        if(CollectionUtils.isEmpty(adIds)){
            return Collections.emptyList();
        }

        List<AdCreativeObject> result = new ArrayList<>();

        adIds.forEach( x->{
            AdCreativeObject object = get(x);
            if (object == null){
                log.error("CreativeObject not found : {}",x);
                return;
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public AdCreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdCreativeObject value) {
        objectMap.put(key, value);
    }

    @Override
    public void update(Long key, AdCreativeObject value) {

        AdCreativeObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
    }

    @Override
    public void delete(Long key, AdCreativeObject value) {
        objectMap.remove(key);
    }
}
