package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 应急处置-- 处置方案vo
 * @author qizhijie-pc
 * @date 2018年11月1日16:51:24
 */
public class SchemeVo extends BaseVo<String> {

    public SchemeVo(){}

    //方案名称
    @Getter@Setter
    @Length(max =200,message = "schemeName 最大长度不能超过200")
    @NotEmpty(message = "schemeName 不能为空字符串")
    private String schemeName;

    //事件ID
    @Getter
    @Setter
    @Length(max =36,message = "eventId 最大长度不能超过36")
    @NotEmpty(message = "eventId 不能为空字符串")
    private String eventId;

}
