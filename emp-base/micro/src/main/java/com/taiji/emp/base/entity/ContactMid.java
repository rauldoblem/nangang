package com.taiji.emp.base.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;


/**
 * 通讯录组 与 通讯录信息 中间表 实体类 Hazard
 * @author SunYi
 * @date 2018年10月19日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_CONTACT_MID")
public class ContactMid extends IdEntity<String> {

    public ContactMid(){}

    /**
     * 组织ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "组织ID teamId字段最大长度36")
    private String teamId;

    /**
     * 组织名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "组织名称 teamId字段最大长度50")
    private String teamName;


    /**
     * 用户详情
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = Contact.class)
    @JoinColumn(name = "ADDR_ID", referencedColumnName = "ID")
    private Contact contact;
    /**
     * 人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "人员姓名 addrName字段最大长度50")
    private String addrName;

}
