package com.ad.mysql.listener;

import com.ad.mysql.constant.Constant;
import com.ad.mysql.constant.OpType;
import com.ad.mysql.dto.BinglogRowData;
import com.ad.mysql.dto.MySqlRowData;
import com.ad.mysql.dto.TableTemplate;
import com.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/25 23:33
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Autowired
    private AggregationListener aggregationListener;

    @Resource()
    private ISender sender;

    @Override
    public void register() {
        log.info("IncrementListener register DB and table info");
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinglogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        //包装秤最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        //取出末班中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (fieldList == null) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> after : eventData.getAfter()) {

            Map<String, String> afterMap = new HashMap<>();

            for (Map.Entry<String, String> entry : after.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                afterMap.put(colName, colValue);
            }

            rowData.getFieldValueMap().add(afterMap);
        }
        sender.sender(rowData);
    }
}
