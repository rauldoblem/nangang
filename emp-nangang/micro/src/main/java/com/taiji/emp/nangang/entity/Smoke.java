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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yhcookie
 * @date 2018/12/10 10:52
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "IF_SMOKE")
public class Smoke extends BaseEntity<String>{

    @Length(max = 50,message = "So2折算值字段最大长度50")
    private String so2converted;

    @Length(max = 50,message = "NO折算值字段最大长度50")
    private String noconverted;

    @Length(max = 50,message = "Smoke折算值字段最大长度50")
    private String smokeconverted;

    @Length(max = 50,message = "HCl折算值字段最大长度50")
    private String hclconverted;

    @Length(max = 50,message = "流量字段最大长度50")
    private String flow;
}
