package com.taiji.emp.nangang.entity;


import com.taiji.micro.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ED_DAILYCHECK_DAILYLOG")
public class DailyCheckDailyLog {

    @Id
    @GenericGenerator(
            name = "customUUIDGenerator",
            strategy = "com.taiji.micro.common.id.CustomSortUUIDGenerator"
    )
    @GeneratedValue(
            generator = "customUUIDGenerator"
    )
    @Length(max = 36,message = "ID字段最大长度36")
    public String id;

    @Length(max = 36,message = "检查项checkItemID字段最大长度36")
    private String checkItemId;

    @Length(max = 36,message = "值班日志id dailylogID字段最大长度36")
    private String dailylogId;

}
