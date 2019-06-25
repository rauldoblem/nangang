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
 * 应急处置--处置方案实体类 Scheme
 * @author qizhijie-pc
 * @date 2018年11月1日17:26:37
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_SCHEME")
public class Scheme extends BaseEntity<String> implements DelFlag{

    public Scheme(){}

    //方案名称
    @Getter
    @Setter
    @Length(max =200,message = "schemeName 最大长度不能超过200")
    @NotEmpty(message = "schemeName 不能为空字符串")
    private String schemeName;

    //事件ID
    @Getter
    @Setter
    @Length(max =36,message = "eventId 最大长度不能超过36")
    @NotEmpty(message = "eventId 不能为空字符串")
    private String eventId;

    /**
     * 删除标志
     */
    @Getter@Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
