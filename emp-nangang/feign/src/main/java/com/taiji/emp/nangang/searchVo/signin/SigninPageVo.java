package com.taiji.emp.nangang.searchVo.signin;

import com.taiji.emp.nangang.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class SigninPageVo extends BasePageVo {
    private String dutyPersonName;
    private String checkDateStart;
    private String checkDateEnd;
}
