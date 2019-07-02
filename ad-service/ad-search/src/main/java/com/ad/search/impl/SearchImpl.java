package com.ad.search.impl;

import com.ad.index.DataTable;
import com.ad.index.adunit.AdUnitIndex;
import com.ad.index.interest.AdUnitItIndex;
import com.ad.search.ISearch;
import com.ad.search.SearchResponse;
import com.ad.search.vo.SearchRequest;
import com.ad.search.vo.feature.DistrictFeature;
import com.ad.search.vo.feature.FeatureRelation;
import com.ad.search.vo.feature.ItFeature;
import com.ad.search.vo.feature.KeywordFeature;
import com.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        }
        return null;
    }
}
