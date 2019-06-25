package com.taiji.emp.res.searchVo.knowledge;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class KnowListVo {

    @Getter
    @Setter
    private String title;

    @Getter@Setter
    private List<String> eventTypeIds;

    @Getter@Setter
    private String keyWord;

    @Getter@Setter
    private String knoTypeId;

    @Getter@Setter
    private String createOrgId;

}
