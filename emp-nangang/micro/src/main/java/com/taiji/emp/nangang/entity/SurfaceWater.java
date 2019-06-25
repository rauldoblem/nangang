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
@Table(name = "IF_SURFACE_WATER")
@Getter
@Setter
@NoArgsConstructor
public class SurfaceWater extends BaseEntity<String> implements DelFlag {
    @Length(max = 50 ,message = "水温字段最大长度50")
    private String waterTemperature;
    @Length(max = 50 ,message = "PH值字段最大长度50")
    private String ph;
    @Length(max = 50 ,message = "电导率字段最大长度50")
    private String conductivity;
    @Length(max = 50 ,message = "浊度字段最大长度50")
    private String turbidity;
    @Length(max = 50 ,message = "溶解氧字段最大长度50")
    private String dissolvedOxygen;
    @Length(max = 50 ,message = "氨氮字段最大长度50")
    private String ammoniaNitrogen;
    @Length(max = 50 ,message = "总磷字段最大长度50")
    private String totalPhosphorus;
    @Length(max = 50 ,message = "总氮字段最大长度50")
    private String totalNitrogen;
    @Length(max = 50 ,message = "总有机氮字段最大长度50")
    private String totalOrganicNitrogen;
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
