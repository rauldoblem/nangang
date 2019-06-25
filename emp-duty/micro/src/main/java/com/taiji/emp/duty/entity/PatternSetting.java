package com.taiji.emp.duty.entity;


import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * 值班模式设置表 PatternSetting
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_PATTERN_SETTINGS")
public class PatternSetting extends IdEntity<String> {

    public PatternSetting() {}

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    @Column(name = "ORG_ID")
    @Length(max = 36,message = "所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  所属单位名称
     */
    @Getter
    @Setter
    @Column(name = "ORG_NAME")
    @Length(max = 50,message = "所属单位名称orgName字段最大长度50")
    private String orgName;


    /**
     *  日期类型编码（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    @Getter
    @Setter
    @Min(value=0,message = "日期类型编码最小为0")
    @Max(value=9999,message = "日期类型编码最大为9999")
    @Column(name = "DTYPE_CODE")
    private Integer dtypeCode;

}
