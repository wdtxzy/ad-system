package com.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Author : wangdi
 * @Date : create in 2019/6/20 23:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private String database;

    private List<JsonTable> tableList;
}
