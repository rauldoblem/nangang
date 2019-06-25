package com.taiji.emp.nangang.vo;

import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.emp.base.vo.NoticeVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class NoticesVo {

    /**
     * NoticeReceiveOrgVo
     */
    @Getter
    @Setter
    private NoticeReceiveOrgVo noticeRec;

    /**
     * 附件
     */
    @Getter@Setter
    private List<DocAttsVo> docAtts;

    /**
     * NoticeVo
     */
    @Getter
    @Setter
    private NoticeVo notice;

    /**
     * TaskFeedbackVo
     */
    @Getter
    @Setter
    private TaskFeedbackVo ecTaskFeedback;
}
