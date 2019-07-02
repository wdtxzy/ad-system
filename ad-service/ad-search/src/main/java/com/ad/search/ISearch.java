package com.ad.search;

import com.ad.search.vo.SearchRequest;

/**
 * @Author : wangdi
 * @Date : create in 2019/7/1 23:18
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
