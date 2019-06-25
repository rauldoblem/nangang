package com.taiji.emp.res.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * 应急物资
 */
public class MaterialVo extends BaseVo<String>{

    public MaterialVo(){}

    /**
     * 物资名称
     */
    @Getter@Setter
    @NotEmpty(message = "物资名称不能为空")
    @Length(max = 200,message = "专家姓名name字段最大长度200")
    private String name;

    /**
     * 物资编码
     */
    @Getter@Setter
    @Length(max = 36,message = "物资编码code字段最大长度36")
    private String code;

    /**
     * 储备库ID
     */
    @Getter@Setter
    @Length(max = 36,message = "储备库ID repertoryId字段最大长度36")
    private String repertoryId;

    /**
     * 储备库名称
     */
    @Getter@Setter
    @Length(max = 100,message = "储备库名称repertoryName字段最大长度100")
    private String repertoryName;

    /**
     * 仓位ID
     */
    @Getter@Setter
    @Length(max = 36,message = "仓位ID positionId字段最大长度36")
    private String positionId;

    /**
     * 仓位名称
     */
    @Getter@Setter
    @Length(max = 100,message = "仓位名称positionName字段最大长度100")
    private String positionName;

    /**
     * 物资类型ID
     */
    @Getter@Setter
    @Length(max = 36,message = "物资类型ID resTypeId字段最大长度36")
    private String resTypeId;

    /**
     * 物资类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "物资类型名称positionName字段最大长度100")
    private String resTypeName;

    /**
     * 经纬度
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度 lonAndLat字段最大长度64")
    private String lonAndLat;

    /**
     * 生产厂商
     */
    @Getter
    @Setter
    @Length(max = 50,message = "生产厂商manufacturers字段最大长度50")
    private String manufacturers;

    /**
     * 制造信息：0进口，1国产，2自制
     */
    @Getter
    @Setter
    @Length(max = 1,message = "制造信息vendorCountry字段最大长度1")
    private String vendorCountry;

    /**
     * 规格型号
     */
    @Getter
    @Setter
    @Length(max = 100,message = "规格型号specModel字段最大长度100")
    private String specModel;

    /**
     * 生产日期(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @Getter@Setter
    private LocalDate productTime;

    /**
     * 报废日期(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @Getter@Setter
    private LocalDate expiratTime;

    /**
     * 计量单位
     */
    @Getter@Setter
    @Length(max = 20,message = "计量单位unitMeasure字段最大长度20")
    private String unitMeasure;

    /**
     * 所属单位
     */
    @Getter@Setter
    @NotEmpty(message = "管理单位不能为空")
    @Length(max = 80,message = "管理单位unit字段最大长度80")
    private String unit;


    /**
     * 初始数量
     */
    @Getter@Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer initialQuantity;


    /**
     * 在外数量
     */
    @Getter@Setter
    @Min(value=0,message = "在外数量最小为0")
    @Max(value=9999,message = "在外数量最大为9999")
    private Integer outsideQuantity;

    /**
     * 剩余数量
     */
    @Getter@Setter
    @Min(value=0,message = "剩余数量最小为0")
    @Max(value=9999,message = "剩余数量最大为9999")
    private Integer remainingQuantity;


    /**
     * 备注
     */
    @Getter@Setter
    @Length(max = 4000,message = "备注notes字段最大长度4000")
    private String notes;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    @Length(max = 50,message = "创建单位ID createOrgId字段最大长度50")
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    @Length(max = 100,message = "创建单位编码 createOrgName字段最大长度100")
    private String createOrgName;

}
