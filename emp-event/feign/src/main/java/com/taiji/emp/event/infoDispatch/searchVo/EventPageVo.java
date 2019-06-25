package com.taiji.emp.event.infoDispatch.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 事件信息分页查询类eventPageVo
 * @author qizhijie-pc
 * @date 2018年10月23日15:24:59
 */
public class EventPageVo extends BasePageVo{

    public EventPageVo(){}

    //事件名称
    @Getter@Setter
    private String eventName;

    //事件等级ID
    @Getter@Setter
    private String eventGradeId;

    //事件类型IDs
    @Getter@Setter
    private List<String> eventTypeIds;

    //处置状态：0处置中 1处置结束  2 已评估 3 已归档
    @Getter@Setter
    private List<String> handleFlags;

    //合成事件部门ID,为空时默认为登录用户所属单位ID进行数据过滤
    @Getter@Setter
    private String createOrgId;

}
