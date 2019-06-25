package com.taiji.emp.base.entity;

import com.taiji.micro.common.entity.IdEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 通讯录组 管理实体类 contactTeam
 * @author SunYi
 * @date 2018年10月22日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_CONTACTTEAM")
public class ContactTeam extends IdEntity<String> implements DelFlag {

    public ContactTeam() {}

    /**
     * 组名
     */
    @Getter
    @Setter
    @Length(max = 50, message = "组名 teamName字段最大长度36")
    private String teamName;

    /**
     * 当前用户ID
     */
    @Getter
    @Setter
    @Length(max = 36, message = "当前用户ID userId 字段最大长度36")
    private String userId;

    /**
     * 父节点ID
     */
    @Getter
    @Setter
    @Length(max = 36, message = "父节点ID parentId 字段最大长度36")
    private String parentId;

    /**
     * 是否叶子节点（0：是；1：不是）
     */
    @Getter
    @Setter
    @Length(max = 1, message = "是否叶子节点 leaf 字段最大长度1")
    private String leaf;

    /**
     *排序字段
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}