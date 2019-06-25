package com.taiji.emp.nangang.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "IF_METEOROLOGICAL")
@Getter
@Setter
@NoArgsConstructor
public class Meteorological extends BaseEntity<String> implements DelFlag {
    @Length(max = 50 ,message = "温度字段最大长度50")
    private String temperature;
    @Length(max = 50 ,message = "大气压力字段最大长度50")
    private String atmosphericPressure;
    @Length(max = 50 ,message = "湿度字段最大长度50")
    private String humidity;
    @Length(max = 50 ,message = "风速字段最大长度50")
    private String windSpeed;
    @Length(max = 50 ,message = "主导风向字段最大长度50")
    private String windDirection;
    @Length(max = 50 ,message = "雨量字段最大长度50")
    private String rainfall;
    @Length(max = 50,message = "nodeID字段最大长度50")
    private String nodeId;
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
