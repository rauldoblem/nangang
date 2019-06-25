package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter@Setter
public class TotalConventionalFactorVo {
    private List<String> SO2 = new ArrayList<>();

    private List<String> PM2_5 = new ArrayList<>();

    private List<String> O3 = new ArrayList<>();

    private List<String> PM10 = new ArrayList<>();

    private List<String> NO2 = new ArrayList<>();

    private List<String> time = new ArrayList<>();
}
