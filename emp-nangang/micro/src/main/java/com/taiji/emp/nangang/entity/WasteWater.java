package com.taiji.emp.nangang.entity;

import com.taiji.micro.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yhcookie
 * @date 2018/12/10 11:05
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "IF_WASTE_WATER")
public class WasteWater extends BaseEntity<String> {

    @Length(max = 50,message = "水流量字段最大长度50")
    private String waterFlow;

    @Length(max = 50,message = "PH字段最大长度50")
    private String ph;

    @Length(max = 50,message = "COD字段最大长度50")
    private String cod;

    @Length(max = 50,message = "氨氮折算值字段最大长度50")
    private String ammoniaNitrogen;

    @Length(max = 50,message = "总氮字段最大长度50")
    private String totalNitrogen;

    @Length(max = 50,message = "总磷字段最大长度50")
    private String totalPhosphorus;
    @Length(max = 50,message = "nodeID字段最大长度50")
    private String nodeId;
}
