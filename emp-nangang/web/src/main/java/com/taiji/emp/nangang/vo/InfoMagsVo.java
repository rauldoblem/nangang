package com.taiji.emp.nangang.vo;

import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.emp.base.vo.NoticeVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class InfoMagsVo {

    /**
     * AcceptVo
     */
    @Getter
    @Setter
    private AcceptVo imAccept;

    /**
     * 图片
     */
    @Getter@Setter
    private List<PicturesVo> pictures;

    /**
     * 视频
     */
    @Getter@Setter
    private List<VideosVo> videos;

    /**
     * 音频
     */
    @Getter@Setter
    private List<AudiosVo> audios;

    /**
     * TaskFeedbackVo
     */
    @Getter
    @Setter
    private TaskFeedbackVo ecTaskFeedback;
}
