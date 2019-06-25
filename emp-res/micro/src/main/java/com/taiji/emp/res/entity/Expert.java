package com.taiji.emp.res.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import com.taiji.micro.common.validator.Phone;
import com.taiji.micro.common.validator.Sex;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 应急专家实体类 Expert
 * @author qizhijie-pc
 * @date 2018年10月10日14:16:08
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ER_EXPERT")
public class Expert extends BaseEntity<String> implements DelFlag{

    public Expert(){}

    /**
     * 专家姓名
     */
    @Getter@Setter
    @NotEmpty(message = "专家姓名不能为空")
    @Length(max = 50,message = "专家姓名name字段最大长度50")
    private String name;

    /**
     * 性别：(0：女；1：男)
     */
    @Getter@Setter
    @NotEmpty(message = "性别不能为空")
    @Length(max = 1,message = "性别sex字段最大长度1")
    @Sex
    private String sex;

    /**
     * 出生年月
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @Getter@Setter
    private LocalDate birthday;

    /**
     * 学历
     */
    @Getter@Setter
    @Length(max = 50,message = "学历education字段最大长度1")
    private String education;

    /**
     * 工作单位
     */
    @Getter@Setter
    @NotEmpty(message = "工作单位不能为空")
    @Length(max = 50,message = "工作单位unit字段最大长度50")
    private String unit;

    /**
     * 职务
     */
    @Getter@Setter
    @Length(max = 50,message = "职务post字段最大长度50")
    private String post;

    /**
     * 职称
     */
    @Getter@Setter
    @Length(max = 50,message = "职称technicalTitle字段最大长度50")
    private String technicalTitle;

    /**
     * 办公电话
     */
    @Getter@Setter
    @Length(max = 50,message = "办公电话officeTel字段最大长度50")
    private String officeTel;

    /**
     * 手机号码
     */
    @Getter@Setter
    @NotEmpty(message = "手机号码不能为空")
    @Length(max = 16,message = "手机号码telephone字段最大长度16")
    @Phone
    private String telephone;

    /**
     * 邮箱
     */
    @Getter@Setter
    @Length(max = 100,message = "邮箱email字段最大长度100")
    private String email;

    /**
     * 联系地址
     */
    @Getter@Setter
    @Length(max = 100,message = "联系地址address字段最大长度100")
    private String address;

    /**
     * 事件类型IDS为用英文逗号连接的ID串
     */
    @Getter@Setter
    @NotEmpty(message = "事件类型IDS不能为空")
    @Length(max = 4000,message = "事件类型IDS eventTypeIds字段最大长度4000")
    private String eventTypeIds;

    /**
     * 事件类型NAMES为用英文逗号连接的事件类型名称串,新增时前端可不传该属性
     */
    @Getter@Setter
    @NotEmpty(message = "事件类型NAMES不能为空")
    @Length(max = 4000,message = "事件类型NAMES eventTypeNames字段最大长度4000")
    private String eventTypeNames;

    /**
     * 专业特长
     */
    @Getter@Setter
    @Length(max = 500,message = "专业特长 specialty字段最大长度500")
    private String specialty;

    /**
     * 备注
     */
    @Getter@Setter
    @Length(max = 2000,message = "备注 note字段最大长度2000")
    private String notes;

    /**
     * 专家照片存储地址
     */
    @Getter@Setter
    @Length(max = 100,message = "专家照片存储地址 photoUrl字段最大长度100")
    @Column(name = "PHOTO_URL")
    private String photoUrl;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    @Length(max = 50,message = "创建单位ID createOrgId字段最大长度50")
    @Column(name = "CREATE_ORG_ID")
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    @Length(max = 100,message = "创建单位编码 createOrgName字段最大长度100")
    @Column(name = "CREATE_ORG_NAME")
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
