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
 * 应急处置--关联应急队伍实体类 CmdTeam
 * @author qizhijie-pc
 * @date 2018年11月7日11:45:20
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_TEAM")
public class CmdTeam extends BaseEntity<String> implements DelFlag {

    public CmdTeam(){}

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

    //队伍ID
    @Getter
    @Setter
    @Length(max =36,message = "teamId 最大长度不能超过36")
    @NotEmpty(message = "teamId 不能为空字符串")
    private String teamId;

    //队伍名称
    @Getter
    @Setter
    @Length(max =100,message = "teamName 最大长度不能超过100")
    private String teamName;

    //队伍类型名称
    @Getter
    @Setter
    @Length(max =100,message = "teamTypeName 最大长度不能超过100")
    private String teamTypeName;

    //队伍常驻地址
    @Getter
    @Setter
    @Length(max =100,message = "teamAddress 最大长度不能超过100")
    private String teamAddress;

    //负责人
    @Getter
    @Setter
    @Length(max =50,message = "principal 最大长度不能超过50")
    private String principal;

    //负责人手机
    @Getter
    @Setter
    @Length(max =50,message = "principalTel 最大长度不能超过50")
    private String principalTel;


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
