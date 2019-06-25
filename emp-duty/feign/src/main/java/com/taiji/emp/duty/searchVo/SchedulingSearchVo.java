package com.taiji.emp.duty.searchVo;

import com.taiji.emp.duty.vo.SchedulingTimeVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SchedulingSearchVo {

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
    private Integer dateTypeCode;

    /**
     *  日期类型名称
     */
    @Getter
    @Setter
    private String dateTypeName;

    /**
     *  节假日名称
     */
    @Getter
    @Setter
    private String holidayName;

    /**
     *  本月的第几天
     */
    @Getter
    @Setter
    private String day;

    /**
     *  星期几
     */
    @Getter
    @Setter
    private String weekDay;

    /**
     *  值排班按天值班
     */
    @Getter
    @Setter
    private List<SchedulingVo> SchedulingsForDay;

    /**
     *  值排班按班次值班
     */
    @Getter
    @Setter
    private List<SchedulingTimeVo> SchedulingsForTime;

}
