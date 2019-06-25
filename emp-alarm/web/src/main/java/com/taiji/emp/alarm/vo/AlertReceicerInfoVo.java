package com.taiji.emp.alarm.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 预警信息已通知单位
 * @author yhcookie
 * @date 2019/1/5 15:06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertReceicerInfoVo {

    @Length(max = 36 , message = "组织机构Id字段 最大不能超过36")
    private String orgId;

    @Length(max = 50 , message = "组织机构名称字段 最大不能超过50")
    private String orgName;
}
