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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "IF_CONVENTIONAL_FACTOR")
@Getter
@Setter
@NoArgsConstructor
public class ConventionalFactor extends BaseEntity<String> implements DelFlag {

    @Length(max = 50 ,message = "二氧化硫字段最大长度50")
    private String so2;
    @Length(max = 50 ,message = "PM10字段最大长度50")
    private String pm10;
    @Length(max = 50 ,message = "二氧化氮字段最大长度50")
    private String no2;
    @Length(max = 50 ,message = "一氧化碳字段最大长度50")
    private String co;
    @Length(max = 50 ,message = "PM2.5字段最大长度50")
    private String pm2_5;
    @Length(max = 50 ,message = "臭氧字段最大长度50")
    private String o3;
    @Length(max = 100,message = "nodeID字段最大长度100")
    private String nodeId;
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
