package com.taiji.emp.nangang.entity;

import com.taiji.micro.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import com.taiji.micro.common.entity.utils.DelFlag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IF_COMPANY")
@Getter
@Setter
public class Company extends BaseEntity<String>  implements DelFlag {

    @Length(max = 50,message = "最大长度为50")
    private String name;
    @Length(max = 50,message = "最大长度为50")
    private String companyType;
    @Length(max = 200,message = "最大长度为200")
    private String address;
    @Length(max = 64,message = "最大长度为64")
    private String lonAndLot;
    @Length(max = 50,message = "最大长度为50")
    private String companySize;
    @Length(max = 50,message = "最大长度为50")
    private Double floorArea;

    private Double personNum;

    @Length(max = 1000,message = "最大长度为1000")
    private String busiScope;
    @Length(max = 50,message = "最大长度为50")
    private String legalPerson;
    @Length(max = 200,message = "最大长度为200")
    private String induType;
    @Length(max = 50,message = "最大长度为50")
    private String chargePerson;
    @Length(max = 50,message = "最大长度为50")
    private String chargeTel;
    @Length(max = 50,message = "最大长度为50")
    private String safetyPerson;
    @Length(max = 50,message = "最大长度为50")
    private String safetyTel;
    @Length(max = 50,message = "最大长度为50")
    private String notes;
    @Length(max = 1,message = "最大长度为1")
    private String sourceFlag;
    @Length(max = 50,message = "最大长度为50")
    private String code;
    @Length(max = 50,message = "最大长度为50")
    private String nature;
    @Length(max = 50,message = "最大长度为50")
    private String region;

    private Double regCapital;
    @Length(max = 100,message = "最大长度为100")
    private String email;
    @Length(max = 50,message = "最大长度为50")
    private String fax;
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
 }
