package com.taiji.emp.drill.searchVo;

import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DrillSchemeReceiveSearchVo extends BasePageVo {

    /**
     * 方案名称
     */
    @Getter
    @Setter
    private String drillName;

    @Getter
    @Setter
    private String drillStartTime;

    @Getter
    @Setter
    private String drillEndTime;

    /**
     * 制定部门ID字符串
     */
    @Getter
    @Setter
    private String orgIds;

    /**
     * 接收单位ID，默认登录用户所属单位ID进行接收单位过滤
     */
    @Getter
    @Setter
    private String receiveOrgId;

    /**
     * 演练方案ID
     */
    @Getter
    @Setter
    private String drillSchemeId;

    /**
     * 演练方案ID
     */
    @Getter
    @Setter
    private String sendType;

    /**
     * 下发/上报的接收部门的ID和名称
     */
    @Getter
    @Setter
    private List<DrillSchemeReceiveVo> orgIdANames;
}
