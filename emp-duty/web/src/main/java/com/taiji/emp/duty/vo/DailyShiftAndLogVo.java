package com.taiji.emp.duty.vo;

import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import com.taiji.emp.duty.vo.dailyShift.DailyShiftVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DailyShiftAndLogVo {

    @Getter
    @Setter
    private List<DailyLogShiftVo> dailyLogShift;

    @Getter
    @Setter
    private DailyShiftVo dailyShift;

    @Getter
    @Setter
    private List<String> logIds;

    @Getter
    @Setter
    private SigninVo signinVo;

}
