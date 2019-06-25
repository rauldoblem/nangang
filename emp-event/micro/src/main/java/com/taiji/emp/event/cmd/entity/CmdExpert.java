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

/**
 * 应急处置--关联应急专家实体类 CmdExpert
 * @author qizhijie-pc
 * @date 2018年11月7日11:45:20
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_EXPERT")
public class CmdExpert extends BaseEntity<String> implements DelFlag {

    public CmdExpert(){}

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

    //专家Id
    @Getter
    @Setter
    @Length(max =36,message = "expertId 最大长度不能超过36")
    @NotEmpty(message = "expertId 不能为空字符串")
    private String expertId;

    //专家姓名
    @Getter
    @Setter
    @Length(max =50,message = "expertName 最大长度不能超过50")
    private String expertName;

    //处理事故类型名称
    @Getter
    @Setter
    @Length(max =4000,message = "eventTypeNames 最大长度不能超过4000")
    private String eventTypeNames;

    //专业特长
    @Getter
    @Setter
    @Length(max =500,message = "specialty 最大长度不能超过500")
    private String specialty;

    //专业特长
    @Getter
    @Setter
    @Length(max =50,message = "unit 最大长度不能超过50")
    private String unit;

    //联系方式
    @Getter
    @Setter
    @Length(max =16,message = "telephone 最大长度不能超过16")
    private String telephone;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
