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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ED_DAILYCHECK_ITEMS")
public class DailyCheckItems extends BaseEntity<String> implements DelFlag {

    @Length(max = 36,message = "主键id字段最大长度36")
    private String id;

    @Length(max = 36,message = "检查表ID dailyCheckID字段最大长度36")
    private String dailycheckId;

    @Length(max = 36,message = "检查项ID idcheckItemID字段最大长度36")
    private String checkItemId;

    @Length(max = 1000,message = "检查项（来自数据字典）checkItemContent字段最大长度1000")
    private String checkItemContent;

    @Length(max = 1,message = "检查结果checkResult字段长度最大为1(0 ：否，1 ：是)")
    private String checkResult;

    @Length(max = 1,message = "删除标志位delFlag字段长度最大为1(0 ：否，1 ：是)")
    private String delFlag;
}
