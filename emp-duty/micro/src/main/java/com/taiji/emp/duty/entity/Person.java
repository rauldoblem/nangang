package com.taiji.emp.duty.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.IdEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * 值班人员表 Person
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_PERSON")
public class Person extends IdEntity<String> implements DelFlag {

    public Person() {}

    /**
     *  通讯录ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Contact.class)
    @JoinColumn(name = "ADDR_ID", referencedColumnName = "ID")
    private Contact contact;

    /**
     *  通讯录人员姓名
     */
    @Getter
    @Setter
    @Column(name = "ADDR_NAME")
    @Length(max = 50,message = "通讯录人员姓名 addrName字段最大长度50")
    private String addrName;

    /**
     *  所在值班分组编码
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = DutyTeam.class)
    @JoinColumn(name = "DUTY_TEAM_ID", referencedColumnName = "ID")
    private DutyTeam dutyTeam;

    /**
     *  所在值班分组名称
     */
    @Getter
    @Setter
    @Column(name = "DUTY_T_NAME")
    @Length(max = 50,message = "所在值班分组名称 dutyIName字段最大长度50")
    private String dutyIName;

    /**
     *  组内排序
     */
    @Getter
    @Setter
    @Min(value=0,message = "组内排序最小为0")
    @Max(value=9999,message = "组内排序最大为9999")
    @Column(name = "ORDER_IN_TEAM")
    private Integer orderInTeam;


    @Getter
    @Setter
    @Length(max = 50,message = "创建者名称 createBy字段最大长度50")
    private String createBy;

    /**
     * 创建时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime createTime;


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
