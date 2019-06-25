package com.taiji.emp.duty.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * 通讯录管理实体类 contact
 * @author SunYi
 * @date 2018年10月19日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_CONTACT")
public class Contact extends BaseEntity<String> implements DelFlag {

    public Contact(){}

    /**
     * 所属单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     * 所属单位名称
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属单位名称 orgName字段最大长度36")
    private String orgName;


    /**
     * 职务类型ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "职务类型ID dutyTypeId字段最大长度36")
    private String dutyTypeId;


    /**
     * 职务类型名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "职务类型名称 dutyTypeName字段最大长度50")
    private String dutyTypeName;

    /**
     * 姓名
     */
    @Getter
    @Setter
    @NotBlank(message = "姓名name不能为空字符串")
    @Length(max = 20,message = "姓名 name字段最大长度20")
    private String name;

    /**
     * 性别
     */
    @Getter
    @Setter
    @Length(max = 1,message = "性别 sex字段最大长度1")
    private String sex;


    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "BIRTHDATE")
    @Getter@Setter
    private LocalDateTime birthDate;

    /**
     * 联系地址
     */
    @Getter
    @Setter
    @Length(max = 80,message = "联系地址 address字段最大长度80")
    private String address;

    /**
     * 邮编
     */
    @Getter
    @Setter
    @Column(name = "POST_CODE")
    @Length(max = 10,message = "邮编 postCode 字段最大长度10")
    private String postCode;

    /**
     * 头像地址
     */
    @Getter
    @Setter
    @Length(max = 1000,message = "邮编 addrSeq 字段最大长度1000")
    private String addrSeq;



    /**
     * 办公电话
     */
    @Getter
    @Setter
    @Length(max = 16,message = "办公电话 telphone 字段最大长度16")
    private String telephone;

    /**
     * 家庭电话
     */
    @Getter
    @Setter
    @Length(max = 16,message = "家庭电话 homeTel 字段最大长度16")
    private String homeTel;

    /**
     * 移动电话
     */
    @Getter
    @Setter
    @Length(max = 16,message = "移动电话 mobile 字段最大长度16")
    private String mobile;

    /**
     * 邮箱
     */
    @Getter
    @Setter
    @Length(max = 16,message = "邮箱 email 字段最大长度16")
    private String email;

    /**
     * 其他联系方式
     */
    @Getter
    @Setter
    @Length(max = 100,message = "其他联系方式 otherWay 字段最大长度100")
    private String otherWay;

    /**
     * 通讯录类型（特殊情况下，如石油项目才有的）
     */
    @Getter
    @Setter
    @Length(max = 36,message = "通讯录类型 addType 字段最大长度36")
    private String addType;


    /**
     * 传真
     */
    @Getter
    @Setter
    @Length(max = 50,message = "传真 email 字段最大长度50")
    private String fax;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "备注 notes 字段最大长度4000")
    private String notes;


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
