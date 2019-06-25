package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchAllDutyVo {
    public SearchAllDutyVo() {}

    /**
     *  日期(yyyy-MM-dd)
     */
    @Getter
    @Setter
    private String searchDate;

    /**
     *  日期(yyyy-MM-dd)
     */
    @Getter
    @Setter
    private List<String> orgIds;
}
