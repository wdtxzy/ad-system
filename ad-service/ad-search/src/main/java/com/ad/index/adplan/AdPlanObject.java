package com.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 09:16
 * 索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    private Long planId;

    private Long userId;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;

    public void update(AdPlanObject newObject) {
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getUserId() != null) {
            this.userId = newObject.getUserId();
        }
        if (newObject.getPlanStatus() != null) {
            this.planStatus = newObject.getPlanStatus();
        }
        if (newObject.getStartDate() != null) {
            this.startDate = newObject.getStartDate();
        }
        if (newObject.getEndDate() != null) {
            this.endDate = newObject.getEndDate();
        }
    }
}
