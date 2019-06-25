package com.taiji.emp.base.searchVo.contacts;

import com.taiji.emp.base.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

public class ContactTeamsPageVo extends BasePageVo {
    @Getter@Setter
    private String teamId;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String telephone;

    @Getter@Setter
    private String mobileFlag;
    @Getter@Setter
    private String orgId;

}
