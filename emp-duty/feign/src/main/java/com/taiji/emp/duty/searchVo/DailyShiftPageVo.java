package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;


public class DailyShiftPageVo {

    @Getter
    @Setter
    private int page;

    @Getter
    @Setter
    private int size;


    @Getter
    @Setter
    private String fromWatcherName;

    @Getter
    @Setter
    private String toWatcherName;

    @Getter
    @Setter
    private String title;

}
