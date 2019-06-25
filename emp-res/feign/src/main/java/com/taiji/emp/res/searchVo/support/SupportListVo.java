package com.taiji.emp.res.searchVo.support;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SupportListVo {

    @Getter@Setter
    private String name;
    @Getter@Setter
    private String address;
    @Getter@Setter
    private String typeId;
    @Getter@Setter
    private List<String> typeIds;
    @Getter@Setter
    private List<String> supportIds;
    @Getter@Setter
    private String createOrgId;
    @Getter@Setter
    private List<String> selectedSupportIds;

}
