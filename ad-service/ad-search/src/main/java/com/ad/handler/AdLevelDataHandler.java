package com.ad.handler;

import com.ad.dump.table.*;
import com.ad.index.DataTable;
import com.ad.index.IndexAware;
import com.ad.index.adcreativeunit.AdCreativeUnitIndex;
import com.ad.index.adcreativeunit.AdCreativeUnitObject;
import com.ad.index.adplan.AdPlanIndex;
import com.ad.index.adplan.AdPlanObject;
import com.ad.index.adunit.AdUnitIndex;
import com.ad.index.adunit.AdUnitObject;
import com.ad.index.creative.AdCreativeIndex;
import com.ad.index.creative.AdCreativeObject;
import com.ad.index.district.AdUnitDistrictIndex;
import com.ad.index.interest.AdUnitItIndex;
import com.ad.index.keyword.AdUnitKeywordIndex;
import com.ad.mysql.constant.OpType;
import com.ad.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 1.索引之间存在着层级的划分，也就是依赖关系的划分
 * 2.加载圈梁索引其实是增量索引"添加"的一种特殊实现
 *
 * @Author : wangdi
 * @Date : create in 2019/6/19 23:00
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type)
        ;

    }

    public static void handleLevel2(AdCreativeTable creativeTable,
                                    OpType type) {
        AdCreativeObject creativeObject = new AdCreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handleBinlogEvent(
                DataTable.of(AdCreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    public static void handleLevel3(AdUnitTable adUnitTable, OpType type) {
        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class)
                .get(adUnitTable.getPlanId());
        if (adPlanObject == null) {
            log.error("handlevel3 found AdPlanObject error : {}", adUnitTable.getPlanId());
            return;
        }

        AdUnitObject adUnitObject = new AdUnitObject(
                adUnitTable.getUnitId(),
                adUnitTable.getUnitStatus(),
                adUnitTable.getPositionType(),
                adUnitTable.getPlanId(),
                adPlanObject
        );

        handleBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                adUnitObject.getUnitId(),
                adUnitObject,
                type
        );
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable,
                                    OpType type) {
        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        AdCreativeObject creativeObject = DataTable.of(AdCreativeIndex.class).get(creativeUnitTable.getAdId());
        if (unitObject == null || creativeObject == null) {
            log.error("AdCreativeUnitTable index error : {}", JSON.toJSONString(creativeObject));
            return;
        }

        AdCreativeUnitObject creativeUnitObject = new AdCreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId()
        );

        handleBinlogEvent(
                DataTable.of(AdCreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString()),
                creativeUnitObject,
                type
        );
    }

    public static void handleLevel4(AdUnitKeywordTable keywordTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (unitObject == null) {
            log.error("keyword index can not support update : {}", keywordTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(
                Collections.singleton(keywordTable.getUnitId())
        );

        handleBinlogEvent(
                DataTable.of(AdUnitKeywordIndex.class),
                keywordTable.getKeyword(),
                value,
                type
        );
    }

    public static void handleLevel4(AdUnitItTable itTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(itTable.getUnitId());
        if (unitObject == null) {
            log.error("it index can not support update : {}", itTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(
                Collections.singleton(itTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(AdUnitItIndex.class),
                itTable.getItTag(),
                value,
                type
        );
    }

    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("district index can not support update : {}", unitDistrictTable.getUnitId());
            return;
        }

        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );

        handleBinlogEvent(
                DataTable.of(AdUnitDistrictIndex.class),
                key,
                value,
                type
        );
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index,
                                                 K key,
                                                 V value,
                                                 OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            case OTHER:
                break;
        }
    }
}
