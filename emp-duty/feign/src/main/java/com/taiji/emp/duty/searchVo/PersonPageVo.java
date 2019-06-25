package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PersonPageVo {

    @Getter
    @Setter
    private int page;

    @Getter
    @Setter
    private int size;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    private List<String> teamIds;

    @Getter
    @Setter
    private List<String> orgIds;
}
