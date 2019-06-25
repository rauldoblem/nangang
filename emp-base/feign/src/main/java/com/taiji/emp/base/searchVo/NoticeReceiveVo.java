package com.taiji.emp.base.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 接受通知条件查询列表----分页
 * @author qzp-pc
 * @date 2018年10月24日15:35:04
 */
public class NoticeReceiveVo extends BasePageVo {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String noticeTypeId;

    @Getter
    @Setter
    private String sendStartTime;

    @Getter
    @Setter
    private String sendEndTime;

    @Getter
    @Setter
    private List<String> buildOrgIds;

    @Getter
    @Setter
    private String revOrgId;
}
