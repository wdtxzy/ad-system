package com.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 10:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictObject {

    private Long unitId;

    private String provience;

    private String city;
}
