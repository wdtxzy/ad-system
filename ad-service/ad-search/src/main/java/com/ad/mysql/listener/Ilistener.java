package com.ad.mysql.listener;

import com.ad.mysql.dto.BinglogRowData;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/23 23:42
 */
public interface Ilistener {

    void register();

    void onEvent(BinglogRowData eventData);


}
