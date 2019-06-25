package com.taiji.emp.duty.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SigninListVo {
    private LocalDate dutyDate;
    private String dutyShiftPattern;
    private String dutyPersonId;
    private List<String> dutyPersonIds;
    //标志位0--获取当前班次人员的签入状态专用
    private String flag;
    //当前登录用户名
    private String userName;
    //值班开始时间前后45分钟内
    private LocalDateTime startTime;
    //值班开始时间
    private LocalDateTime endTime;

}
