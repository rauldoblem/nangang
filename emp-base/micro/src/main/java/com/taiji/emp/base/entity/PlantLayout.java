package com.taiji.emp.base.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 厂区平面图实体类 PlantLayout
 * @author qzp-pc
 * @date 2019年01月17日18:11:17
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "GIS_PLANT_LAYOUT")
public class PlantLayout extends BaseEntity<String> implements DelFlag {

    public PlantLayout() {}

    /**
     * 平面图名称
     */
    @Getter
    @Setter
    @Length(max = 200,message = "平面图名称 layoutName字段最大长度200")
    private String layoutName;

    /**
     * 经纬度1
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度1 lonAndLat1字段最大长度64")
    @Column(name = "LON_AND_LAT_1")
    private String lonAndLat1;

    /**
     * 经纬度2
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度2 lonAndLat2字段最大长度64")
    @Column(name = "LON_AND_LAT_2")
    private String lonAndLat2;

    /**
     * 经纬度3
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度3 lonAndLat3字段最大长度64")
    @Column(name = "LON_AND_LAT_3")
    private String lonAndLat3;

    /**
     * 外部地图组织机构编码
     */
    @Getter
    @Setter
    @Length(max = 60,message = "外部地图组织机构编码 gisOrgId字段最大长度60")
    private String gisOrgId;

    /**
     * 图片存储地址
     */
    @Getter
    @Setter
    @Length(max = 200,message = "图片存储地址 picUrl字段最大长度200")
    private String picUrl;

    /**
     * 描述信息
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "描述信息 notes字段最大长度2000")
    private String notes;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
