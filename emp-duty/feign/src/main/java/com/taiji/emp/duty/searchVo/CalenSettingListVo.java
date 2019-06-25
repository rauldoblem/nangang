package com.taiji.emp.duty.searchVo;

import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CalenSettingListVo {

    @Getter
    @Setter
    private String orgId;

    @Getter
    @Setter
    private String orgName;

    /**
     * 排班标志，0未排班，1已排班
     */
    @Getter
    @Setter
    private String schedulingFlag;

    @Getter
    @Setter
    private List<CalenSettingVo> calenSetting;
}
