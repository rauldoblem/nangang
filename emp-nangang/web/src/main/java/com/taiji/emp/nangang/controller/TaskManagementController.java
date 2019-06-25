package com.taiji.emp.nangang.controller;

import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.nangang.common.constant.FileGlobal;
import com.taiji.emp.nangang.service.TaskManagementService;
import com.taiji.emp.nangang.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/appNg")
public class TaskManagementController extends BaseController{

    private TaskManagementService service;

    /**
     * 查询应急任务列表 --- 分页
     */
    @PostMapping(path = "/tasksSearch")
    public ResultEntity findPage(
            @RequestBody TaskPageVo taskPageVo,
            OAuth2Authentication principal){
        RestPageImpl<TaskVo> pageVo = service.findPage(taskPageVo,principal);
        return ResultUtils.success(pageVo);
    }

    /**
     * 根据id获取单条应急任务信息
     */
    @GetMapping(path = "/tasks/{id}")
    public ResultEntity findTaskById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        TaskVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 新增应急任务反馈
     */
    @PostMapping(path = "/taskFeedback")
    public ResultEntity createFeedBack(
            @Validated
            @NotNull(message = "TaskFeedbackVo 不能为null")
            @RequestBody TaskFeedbackSaveVo taskFeedbackSaveVo, OAuth2Authentication principal){
        service.create(taskFeedbackSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据任务id获取 对应的所有反馈信息
     */
    @GetMapping(path = "/taskFeedback")
    public ResultEntity findTaskByTaskId(
            @NotEmpty(message = "taskId不能为空")
            @RequestParam(value = "taskId") String taskId){
        List<TaskFeedbackVo> vos = service.findListByTaskId(taskId);
        List<NoticesVo> noticesVoList = new ArrayList<NoticesVo>();
        //遍历信息查询附件
        for (TaskFeedbackVo vo:vos) {
            NoticesVo noticesVo = new NoticesVo();
            String id = vo.getId();
            //查询附件信息
            List<DocAttVo> listVos = service.findDocAtts(id);
            List<DocAttsVo> docAtts = new ArrayList<DocAttsVo>();
            for (DocAttVo dos:listVos) {
                DocAttsVo docAttsVo = new DocAttsVo();
                docAttsVo.setId(dos.getId());
                docAttsVo.setFileName(dos.getName());
                docAttsVo.setFileType(dos.getType());
                docAttsVo.setLocation(dos.getLocation());
                docAtts.add(docAttsVo);
            }
            noticesVo.setEcTaskFeedback(vo);
            noticesVo.setDocAtts(docAtts);
            noticesVoList.add(noticesVo);
        }
        return ResultUtils.success(noticesVoList);
    }

    /**
     * 根据id获取 对应的所有反馈信息
     */
    @GetMapping(path = "/taskFeedback/{id}")
    public ResultEntity findTaskFeedbackById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        TaskFeedbackVo vos = service.findTaskFeedbackById(id);
        //获取附件信息
        List<DocAttVo> docAttVos = service.findDocAtts(id);
        if (CollectionUtils.isEmpty(docAttVos)){
            vos.setIsHaveFiles("0");
        }
        List<PicturesVo> pictures = new ArrayList<PicturesVo>();
        List<VideosVo> videos = new ArrayList<VideosVo>();
        List<AudiosVo> audios = new ArrayList<AudiosVo>();
        //区分不同附件类型
        //picture,audio,video
        for (DocAttVo docAttVo:docAttVos) {
            String type = docAttVo.getType();
            if (StringUtils.isNotEmpty(type)){
                if (type.equals(FileGlobal.DOC_PIC_TYPE)){
                    //图片
                    PicturesVo picturesVo = new PicturesVo();
                    picturesVo.setId(docAttVo.getId());
                    picturesVo.setFileName(docAttVo.getName());
                    picturesVo.setFileType(docAttVo.getType());
                    picturesVo.setLocation(docAttVo.getLocation());
                    pictures.add(picturesVo);
                }else if (type.equals(FileGlobal.DOC_VIDEO_TYPE)){
                    //视频
                    VideosVo videosVo = new VideosVo();
                    videosVo.setId(docAttVo.getId());
                    videosVo.setFileName(docAttVo.getName());
                    videosVo.setFileType(docAttVo.getType());
                    videosVo.setLocation(docAttVo.getLocation());
                    videos.add(videosVo);
                }else if (type.equals(FileGlobal.DOC_AUDIO_TYPE)){
                    //音频
                    AudiosVo audiosVo = new AudiosVo();
                    audiosVo.setId(docAttVo.getId());
                    audiosVo.setFileName(docAttVo.getName());
                    audiosVo.setFileType(docAttVo.getType());
                    audiosVo.setLocation(docAttVo.getLocation());
                    audios.add(audiosVo);
                }
            }
        }

        //封装数据
        InfoMagsVo infoMagsVo = new InfoMagsVo();
        infoMagsVo.setEcTaskFeedback(vos);
        infoMagsVo.setPictures(pictures);
        infoMagsVo.setVideos(videos);
        infoMagsVo.setAudios(audios);
        return ResultUtils.success(infoMagsVo);
    }
}
