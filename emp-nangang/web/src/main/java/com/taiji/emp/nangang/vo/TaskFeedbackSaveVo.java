package com.taiji.emp.nangang.vo;

import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TaskFeedbackSaveVo {

    public TaskFeedbackSaveVo(){}

    @Getter
    @Setter
    private TaskFeedbackVo ecTaskFeedback;

    @Getter
    @Setter
    private List<String> fileIds;
    @Getter
    @Setter
    private List<String> fileDeleteIds;

}
