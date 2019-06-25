package com.taiji.emp.res.searchVo.repertory;

import lombok.Getter;
import lombok.Setter;

public class RepertoryPageVo {

    @Getter@Setter
    private int page;
    @Getter@Setter
    private int size;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String address;
    @Getter@Setter
    private String unit;
    @Getter@Setter
    private String createOrgId;
}
