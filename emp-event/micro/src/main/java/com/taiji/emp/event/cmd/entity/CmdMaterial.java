package com.taiji.emp.event.cmd.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

/**
 * 应急处置--关联应急物资实体类 CmdMaterial
 * @author qizhijie-pc
 * @date 2018年11月8日10:51:03
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_MATERIAL")
public class CmdMaterial extends BaseEntity<String> implements DelFlag {

    public CmdMaterial(){}

    //方案Id
    @Getter
    @Setter
    @Length(max =36,message = "schemeId 最大长度不能超过36")
    @NotEmpty(message = "schemeId 不能为空字符串")
    private String schemeId;

    //预案ID
    @Getter
    @Setter
    @Length(max =36,message = "planId 最大长度不能超过36")
    private String planId;

    //物资ID
    @Getter
    @Setter
    @Length(max =36,message = "materialId 最大长度不能超过36")
    @NotEmpty(message = "materialId 不能为空字符串")
    private String materialId;

    //物资名称
    @Getter
    @Setter
    @Length(max =100,message = "materialName 最大长度不能超过100")
    private String materialName;

    //物资类型
    @Getter
    @Setter
    @Length(max =100,message = "resTypeName 最大长度不能超过100")
    private String resTypeName;

    //所属单位
    @Getter
    @Setter
    @Length(max =100,message = "unit 最大长度不能超过100")
    private String unit;

    //计量单位
    @Getter
    @Setter
    @Length(max =100,message = "unitMeasure 最大长度不能超过100")
    private String unitMeasure;

    //剩余数量
    @Getter
    @Setter
    @Min(0)
    private Integer remainingQuantity;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 储备库ID
     */
    @Getter@Setter
    @Length(max = 36,message = "储备库ID repertoryId字段最大长度36")
    @Column(name = "REPERTORY_ID")
    private String repertoryId;

    /**
     * 储备库名称
     */
    @Getter@Setter
    @Length(max = 100,message = "储备库名称repertoryName字段最大长度100")
    @Column(name = "REPERTORY_NAME")
    private String repertoryName;

    /**
     * 规格型号
     */
    @Getter@Setter
    @Length(max = 100,message = "规格型号specModel字段最大长度100")
    @Column(name = "SPEC_MODEL")
    private String specModel;
}
