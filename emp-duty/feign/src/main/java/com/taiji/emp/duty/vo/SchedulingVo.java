package com.taiji.emp.duty.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 值班表  feign SchedulingVo
 */
public class SchedulingVo extends IdVo<String> {

    public SchedulingVo() {}

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  所属单位名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "所属单位名称 orgName字段最大长度50")
    private String orgName;

    /**
     *  值排班日期(yyyy-MM-dd)
     */
    @Getter
    @Setter
    private String dutyDate;

    /**
     *  日期类型（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    @Getter
    @Setter
    @Min(value=0,message = "日期类型编码最小为0")
    @Max(value=9999,message = "日期类型编码最大为9999")
    private Integer dateTypeCode;


    /**
     *  日期类型名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "日期类型名称 dateTypeName字段最大长度20")
    private String dateTypeName;

    /**
     *  节假日名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "节假日名称 holidayName字段最大长度50")
    private String holidayName;

    /**
     *  班次ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "班次ID shiftPatternId字段最大长度36")
    private String shiftPatternId;

    /**
     *  班次名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "班次名称 shiftPatternName字段最大长度20")
    private String shiftPatternName;

    /**
     *  值班分组ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "值班分组ID dutyTeamId字段最大长度36")
    private String dutyTeamId;

    /**
     *  值班分组名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "值班分组名称 dutyTeamName字段最大长度20")
    private String dutyTeamName;

    /**
     *  值班分组的值班类型编码（0：按班次值班，1：按天值班）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "值班分组的值班类型编码 perTypeName字段最大长度1")
    private String ptypeCode;

    /**
     *  值班人员ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "值班人员ID personId字段最大长度36")
    private String personId;


    @Getter
    @Setter
    private PersonVo person;

    /**
     *  值班人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "值班人员姓名 personName字段最大长度50")
    private String personName;

    /**
     *  历史值班人员ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "历史值班人员ID hisPersonId字段最大长度36")
    private String hisPersonId;

    /**
     *  历史值班人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "历史值班人员姓名 hisPersonName字段最大长度50")
    private String hisPersonName;

    /**
     *  值班开始时间 (yyyy-MM-dd HH:mm:SS)
     */
    @Getter
    @Setter
    private String startTime;

    /**
     *  值班结束时间(yyyy-MM-dd HH:mm:SS)
     */
    @Getter
    @Setter
    private String endTime;

//    /**
//     *  值班分组ID
//     */
//    @Getter
//    @Setter
//    @Length(max = 36,message = "值班分组ID teamId字段最大长度36")
//    private String teamId;

    /**
     *  值班分组ID列表
     */
    @Getter
    @Setter
    private List<String> teamList;

    /**
     * 值班人员ID
     */
    @Getter
    @Setter
    private List<String> personIds;

    /**
     * 统计日志类型的数量
     */
    @Getter
    @Setter
    private Long number;

//    /**
//     * 统计类型数量
//     */
//    @Getter
//    @Setter
//    private String dateTypeCode;

    /**
     * 日历格式 ，0为日历格式，1为列表格式
     */
    @Getter
    @Setter
    private String showType;

    /**
     * 查询的月份(yyyy-MM)
     */
    @Getter
    @Setter
    private String month;


    /**
     * 值班人员姓名
     */
    @Getter
    @Setter
    private List<String> personNames;

    /**
     * 值班人员姓名
     */
    @Getter
    @Setter
    private LocalDateTime currentDateTime;

    /**
     * 是否交接班
     */
    @Getter
    @Setter
    String isShift;

    /**
     * 值班人员信息
     */
    @Getter
    @Setter
    private List<PersonVo> personInfo;

    /**
     * 值班分组ids
     */
    @Getter
    @Setter
    private List<String> dutyTeamIds;

    /**
     * 当天(yyyy-MM-dd)
     */
    @Getter
    @Setter
    private String currentDutyDate;

    /**
     * 下一天(yyyy-MM-dd)
     */
    @Getter
    @Setter
    private String nextDutyDate;

    /**
     * 分组顺序
     */
    @Getter
    @Setter
    private Integer orderTeam;

    /**
     * 查询的月份天数
     */
    @Getter
    @Setter
    private Integer daysNumber;

}
