package com.taiji.emp.event.cmd.vo;

import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


/**
 * 任务反馈 添加Vo TaskFeedbackSaveVo
 * @author SunYi
 * @date 2018年11月9日
 */
public class TaskFeedbackSaveVo extends IdVo<String> {

    public TaskFeedbackSaveVo(){}

    @Getter
    @Setter
    TaskFeedbackVo ecTaskFeedback;

    @Getter
    @Setter
    private List<String> fileIds;
    @Getter
    @Setter
    private List<String> fileDeleteIds;

}
