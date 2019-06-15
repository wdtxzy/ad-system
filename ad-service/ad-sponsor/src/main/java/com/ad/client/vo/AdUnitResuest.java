package com.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/11 23:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitResuest {

    private Long planId;

    private String unitName;

    private Integer positionType;

    private Long budget;

    public boolean createValidate() {
        return planId != null
                && !StringUtils.isEmpty(unitName)
                && positionType != null
                && budget != null;
    }
}
