package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ContactMidSaveVo {

    /**
     * teamId 组ID
     */
    @Getter
    @Setter
    private String teamId;

    /**
     * contactIds  通讯录信息ID集合
     */
    @Getter
    @Setter
    @NotNull(message = "contactIds不能为null")
    private List<String> contactIds;
}
