package com.taiji.emp.duty.vo.dailylog;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DailyLogTYVo {

    public DailyLogTYVo() {
    }

    /**
     * 今天值班日志信息
     */
    @Getter
    @Setter
    List<DailyLogVo> today;

    /**
     * 昨天值班日志信息
     */
    @Getter
    @Setter
    List<DailyLogVo> yesterday;
}
