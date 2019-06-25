package com.taiji.emp.base.searchVo.contacts;

import lombok.Getter;
import lombok.Setter;


public class ContactTeamListVo {
    @Getter@Setter
    private String teamId;
    @Getter@Setter
    private String teamName;

    @Getter@Setter
    private String parentIdStr;

    @Getter@Setter
    private String userId;

}
