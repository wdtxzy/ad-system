package com.ad.client.vo;

import com.ad.constant.CommonStatus;
import com.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/12 23:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequest {

    private String name;

    private Integer type;

    private Integer materialType;

    private Integer height;

    private Integer width;

    private Long size;

    private Integer duration;

    private Long userId;

    private String url;

    public Creative converToEntity() {

        Creative craetive = new Creative();
        craetive.setName(name);
        craetive.setType(type);
        craetive.setMaterialType(materialType);
        craetive.setHeight(height);
        craetive.setWidth(width);
        craetive.setSize(size);
        craetive.setDuration(duration);
        craetive.setAuditStatus(CommonStatus.VALID.getStatus());
        craetive.setUserId(userId);
        craetive.setUrl(url);
        craetive.setCreateTime(new Date());
        craetive.setUpdateTime(craetive.getCreateTime());
        return craetive;
    }
}
