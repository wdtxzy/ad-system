package com.ad.index.adunit;

import com.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 09:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;

    private Integer unitStatus;

    private Integer positionType;

    private Long planId;

    private AdPlanObject adPlanObject;

    void update(AdUnitObject newObject) {
        if (newObject.getUnitId() != null) {
            this.unitId = newObject.getUnitId();
        }
        if (newObject.getUnitStatus() != null) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (newObject.getPositionType() != null) {
            this.positionType = newObject.getPositionType();
        }
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getAdPlanObject() != null) {
            this.adPlanObject = newObject.adPlanObject;
        }
    }
}
