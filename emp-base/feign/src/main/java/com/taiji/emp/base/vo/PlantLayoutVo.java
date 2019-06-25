package com.taiji.emp.base.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 厂区平面图实体类 feign PlantLayoutVo
 * @author qzp-pc
 * @date 2019年01月17日18:11:17
 */
public class PlantLayoutVo extends BaseVo<String> {

    public PlantLayoutVo() {}

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
    private String lonAndLat1;

    /**
     * 经纬度2
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度2 lonAndLat2字段最大长度64")
    private String lonAndLat2;

    /**
     * 经纬度3
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度3 lonAndLat3字段最大长度64")
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
}
