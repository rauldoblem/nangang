package com.taiji.emp.event.cmd.searchVo;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 根据处置发案接口的Vo
 */
public class TimeAxisTaskVo {

    /**状态*/
    @Getter
    @Setter
    private List<String> taskStatus;

    /**处置方案*/
    @Getter@Setter
    private String schemeId;


}
