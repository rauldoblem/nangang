package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class SignInStatusVo {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private LocalDate dutyDate;

    @Getter
    @Setter
    private String dutyShiftPattern;

    @Getter
    @Setter
    private String dutyPersonId;
    @Getter
    @Setter
    private String dutyPersonName;

    @Getter
    @Setter
    private String signStatus;
}
