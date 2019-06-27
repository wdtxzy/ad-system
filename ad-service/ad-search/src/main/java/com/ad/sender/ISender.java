package com.ad.sender;

import com.ad.mysql.dto.MySqlRowData;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/25 23:32
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
