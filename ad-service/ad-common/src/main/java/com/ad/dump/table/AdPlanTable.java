package com.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/17 23:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {

    private Long id;

    private Long userId;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;
}
