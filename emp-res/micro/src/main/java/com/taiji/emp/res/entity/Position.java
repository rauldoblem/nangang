package com.taiji.emp.res.entity;


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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 仓位表实体类 Position
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ER_POSITION")
public class Position extends BaseEntity<String> implements DelFlag{

    public Position(){}

    /**
     *  仓位名称
     */
    @Getter@Setter
    @NotEmpty(message = "仓位名称名称不能为空")
    @Length(max = 100,message = "仓位名称名称name字段最大长度100")
    private String name;

    /**
     *  仓位编码（从外面系统对接的数据）
     */
    @Getter@Setter
    @Length(max = 36,message = "仓位编码code字段最大长度36")
    private String code;

    /**
     *  仓位序号
     */
    @Getter@Setter
    @Min(value=0,message = "仓位序号最小为0")
    @Max(value=9999,message = "仓位序号最大为9999")
    @Column(name = "SORT_NUMBER")
    private Integer sortNumber;

    /**
     *  物资库编码
     */
    @Getter@Setter
    @Length(max = 36,message = "物资库编码repertoryId字段最大长度36")
    @Column(name = "REPERTORY_CODE")
    private String repertoryId;

    /**
     *  物资库名称
     */
    @Getter@Setter
    @Length(max = 100,message = "物资库名称repertoryName字段最大长度100")
    @Column(name = "REPERTORY_NAME")
    private String repertoryName;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    private String createOrgName;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
