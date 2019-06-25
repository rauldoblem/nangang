package com.taiji.emp.nangang.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class DailyCheckItemsVo extends BaseVo<String>{

    @Length(max = 36,message = "检查表ID dailyCheckID字段最大长度36")
    private String dailycheckId;

    @Length(max = 36,message = "检查项ID checkItemID字段最大长度36")
    private String checkItemId;

    @Length(max = 1000,message = "检查项（来自数据字典）checkItemContent字段最大长度1000")
    private String checkItemContent;

    @Length(max = 1,message = "检查结果checkResult字段长度最大为1(0 ：未选择，1 ：是 ，2:否)")
    private String checkResult;

    @Length(max = 36,message = "检查日志Id dailyLogId字段最大长度36")
    private String dailyLogId;

    private LocalDate checkDate;

    private String checkShiftPattern;


}
