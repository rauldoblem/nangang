package com.taiji.emp.res.searchVo.law;

import com.taiji.emp.res.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class LawPageVo extends BasePageVo {
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String keyWord;
    @Getter
    @Setter
    private String buildOrg;
    @Getter
    @Setter
    private List<String> eventTypeIds;
    @Getter
    @Setter
    private List<String> lawTypeIds;
    @Getter
    @Setter
    private String createOrgId;
}
