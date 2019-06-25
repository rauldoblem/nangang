package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 应急处置-- 分析研判vo
 * @author qizhijie-pc
 * @date 2018年10月30日17:24:57
 */
public class AnalyseVo extends BaseVo<String>{

    public AnalyseVo(){}

    //事件ID
    @Getter@Setter
    @Length(max =36,message = "eventId 最大长度不能超过36")
    @NotEmpty(message = "eventId 不能为空字符串")
    private String eventId;

    //研判意见
    @Getter@Setter
    @Length(max =4000,message = "analyseResult 最大长度不能超过4000")
    @NotEmpty(message = "analyseResult 不能为空字符串")
    private String analyseResult;

    //注意事项
    @Getter@Setter
    @Length(max =4000,message = "note 最大长度不能超过4000")
    private String note;

}
