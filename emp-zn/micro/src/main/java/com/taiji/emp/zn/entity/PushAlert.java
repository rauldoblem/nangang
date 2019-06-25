package com.taiji.emp.zn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.IdEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.time.LocalDateTime;

/**
 * @author yhcookie
 * @date 2018/12/22 18:50
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@NoArgsConstructor
@Entity
@Table(name = "MA_ALERT")
public class PushAlert extends BaseEntity<String> implements DelFlag{

    @Getter
    @Setter
    @Length(max = 50,message = "预警信息来源ID（2：天气预警；3：设备预警）字段最大长度50")
    private String source;

    @Getter
    @Setter
    @Length(max = 50,message = "预警信息的标题字段最大长度50")
    private String headline;

    @Getter
    @Setter
    @Length(max = 50,message = "事件类型ID（10010：台风、暴雨气象灾害；10006：大坝险情字段最大长度50")
    private String eventTypeId;

    @Getter
    @Setter
    @Length(max = 50,message = "事件类型名称字段最大长度50")
    private String eventTypeName;

    @Getter
    @Setter
    @Length(max = 50,message = "预警级别ID（1：红色预警；2：橙色预警；3：黄色预警；4：蓝色预警；9：未知）最大长度50")
    private String severityId;

    @Getter
    @Setter
    @Length(max = 50,message = "预警级别名称字段最大长度50")
    private String severityName;

    @Getter
    @Setter
    @Length(max = 255,message = "预警信息的正文字段最大长度255")
    @NotEmpty(message = "description 不能为空字符串")
    private String description;

    @Getter
    @Setter
    @Length(max = 50,message = "预警信息相关编码（台风编码、大坝编码）字段最大长度50")
    private String relateCode;

    @Getter
    @Setter
    @Length(max = 50,message = "对建议采取措施的描述字段最大长度50")
    private String instruction;

    @Getter
    @Setter
    @Length(max = 50,message = "对突发事件影响区域的文字描述最大长度50")
    private String areaDesc;

    @Getter
    @Setter
    @Length(max = 50,message = "预警信息发布区域的行政区划代码字段最大长度50")
    private String geoCode;

    @Getter
    @Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    private LocalDateTime sendTime;

    @Getter
    @Setter
    @Length(max = 50,message = "预警处理状态最大长度50")
    private String alertStatus;

    @Getter
    @Setter
    @Length(max = 50,message = "设备所属的组织机构代码字段最大长度50")
    private String deviceOrgId;

    @Getter
    @Setter
    @Length(max = 50,message = "设备所属的组织机构名称字段最大长度50")
    private String deviceOrgName;

    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 创建单位ID
     */
    @Setter
    @Getter
    @Length(max =36,message = "createOrgId 最大长度不能超过1000")
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Setter
    @Getter
    @Length(max =100,message = "createOrgName 最大长度不能超过100")
    private String createOrgName;

}
