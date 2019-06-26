package com.ad.mysql.listener;

import com.ad.mysql.TemplateHolder;
import com.ad.mysql.dto.BinglogRowData;
import com.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/23 23:43
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;

    private String tableName;

    private Map<String, Ilistener> listenerMap = new HashMap<>();

    @Autowired
    private TemplateHolder templateHolder;

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String _dbName, String _tableName, Ilistener ilistener) {
        log.info("regiter : {}-{}", _dbName, _tableName);
        this.listenerMap.put(genKey(_dbName, _tableName), ilistener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type :{}", type);
        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        //表名和库名是否已经存在填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        //找出对应表有兴趣的监听器
        String key = genKey(this.dbName, this.tableName);
        Ilistener ilistener = this.listenerMap.get(key);
        if (ilistener == null) {
            log.debug("skip {}", key);
            return;
        }

        log.info("trigger event {}", type.name());
        try {
            BinglogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }

            rowData.setEventType(type);
            ilistener.onEvent(rowData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    private BinglogRowData buildRowData(EventData eventData) {

        TableTemplate table = templateHolder.getTable(tableName);
        if (table == null) {
            log.warn("table {} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int colLen = after.length;

            for (int i = 0; i < colLen; i++) {
                //取出当前为止对应的列名
                String colName = table.getPosMap().get(i);
                //如果没有则说明不关心这个列
                if (colName == null) {
                    log.debug("ignore position: {}", i);
                    continue;
                }

                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }

        BinglogRowData rowData = new BinglogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
