package com.taiji.emp.event.cmd.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用于接收启动预案时，web传输的方案id和预案串
 */
public class CmdPlansVo {

    //方案id
    @Getter@Setter
    private String schemeId;

    //预案ids
    @Getter@Setter
    private List<String> planIds;

}
