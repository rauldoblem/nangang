package com.taiji.emp.event.eva.vo;

import lombok.Getter;
import lombok.Setter;
import java.util.List;


/**
 * 过程在线管理v EventEvaProcessSaveVo
 * @author SunYi
 * @date 2018年10月30日
 */
public class EventEvaProcessSaveVo {

    public EventEvaProcessSaveVo(){}

    @Getter@Setter
    private EventEvaProcessVo process;

    @Getter@Setter
    private List<String> fileIds;

    @Getter@Setter
    private List<String> fileDeleteIds;
}
