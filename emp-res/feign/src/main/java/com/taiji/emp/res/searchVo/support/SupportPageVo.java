package com.taiji.emp.res.searchVo.support;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SupportPageVo extends IdVo {

    @Getter@Setter
    private int page;
    @Getter@Setter
    private int size;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String address;
    @Getter@Setter
    private List<String> typeIds;
    @Getter@Setter
    private List<String> supportIds;
    @Getter@Setter
    private String createOrgId;
    @Getter@Setter
    private List<String> selectedSupportIds;
}
