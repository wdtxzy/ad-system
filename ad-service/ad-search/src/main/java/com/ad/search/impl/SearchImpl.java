package com.ad.search.impl;

import com.ad.index.DataTable;
import com.ad.index.adunit.AdUnitIndex;
import com.ad.index.district.AdUnitDistrictIndex;
import com.ad.index.interest.AdUnitItIndex;
import com.ad.index.keyword.AdUnitKeywordIndex;
import com.ad.search.ISearch;
import com.ad.search.SearchResponse;
import com.ad.search.vo.SearchRequest;
import com.ad.search.vo.feature.DistrictFeature;
import com.ad.search.vo.feature.FeatureRelation;
import com.ad.search.vo.feature.ItFeature;
import com.ad.search.vo.feature.KeywordFeature;
import com.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/3 00:04
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        //请求广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        //三个Feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();
        //构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String,List<SearchResponse.Creative>> adSlotToAds = response.getAdSlotToAds();
        for (AdSlot adSlot : adSlots){
            Set<Long> targetUnitIdSet;

            //根据流量类型获取初始AdUnit
            Set<Long> adUnitIdSet = DataTable.of(
                    AdUnitIndex.class
            ).match(adSlot.getPositionType());
            if (relation == FeatureRelation.AND){

                fiterKeywordFeature(adUnitIdSet,keywordFeature);
                fiterItFeature(adUnitIdSet,itFeature);
                fiterDistrictFeature(adUnitIdSet,districtFeature);

                targetUnitIdSet = adUnitIdSet;
            }else{
                targetUnitIdSet = getORRelationUnitIds(
                        adUnitIdSet,
                        keywordFeature,
                        itFeature,
                        districtFeature
                );
            }
        }
        return null;
    }

    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           ItFeature itFeature,
                                           DistrictFeature districtFeature){
        if (CollectionUtils.isEmpty(adUnitIdSet)){
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);

        fiterKeywordFeature(keywordUnitIdSet,keywordFeature);
        fiterItFeature(itUnitIdSet,itFeature);
        fiterDistrictFeature(districtUnitIdSet,districtFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet,itUnitIdSet),
                        districtUnitIdSet)
        );
    }

    private void fiterKeywordFeature(Collection<Long> adUnitIds,
                                     KeywordFeature keywordFeature){
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())){
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->DataTable.of(AdUnitKeywordIndex.class)
                            .match(adUnitId,keywordFeature.getKeywords()));
        }
    }

    private void fiterItFeature(Collection<Long> adUnitIds,
                                      ItFeature itFeature){
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())){
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->DataTable.of(AdUnitItIndex.class)
                            .match(adUnitId,itFeature.getIts()));
        }
    }

    private void fiterDistrictFeature(Collection<Long> adUnitIds,
                                      DistrictFeature districtFeature){
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())){
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->DataTable.of(AdUnitDistrictIndex.class)
                            .match(adUnitId,districtFeature.getDistricts()));
        }
    }
}
