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

    private static boolean isKaiPing(Integer positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.KAIPING) > 0;
    }

    private static boolean isTiePian(Integer positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN) > 0;
    }

    private static boolean isTiePianMiddle(Integer positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    private static boolean isTiePianPause(Integer positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }

    private static boolean isTiePianPost(Integer positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    public static boolean isAdSlotTypeOK(Integer adSlotType, Integer positionType) {

        switch (adSlotType) {
            //AdUnitConstants.POSITION_TYPE.KAIPING
            case 1:
                return isKaiPing(positionType);
            //AdUnitConstants.POSITION_TYPE.TIEPIAN
            case 2:
                return isTiePian(positionType);
            //AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE
            case 4:
                return isTiePianMiddle(positionType);
            //AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE
            case 8:
                return isTiePianPause(positionType);
            //AdUnitConstants.POSITION_TYPE.TIEPIAN_POST
            case 16:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }
}
