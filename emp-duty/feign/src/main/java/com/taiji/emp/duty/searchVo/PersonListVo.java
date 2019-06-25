package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PersonListVo {

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

    @Getter
    @Setter
    private List<String> addrIds;

    @Getter
    @Setter
    private String dutyTeamId;

    @Getter
    @Setter
    private List<String> personIds;
}
