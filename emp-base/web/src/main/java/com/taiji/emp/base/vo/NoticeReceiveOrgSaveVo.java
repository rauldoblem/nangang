package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

public class NoticeReceiveOrgSaveVo extends NoticeReceiveOrgVo {

    /**
     * 接受公告部门ID
     */
    @Getter
    @Setter
    private List<String> orgIds;
}
