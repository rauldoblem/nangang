package com.taiji.emp.event.cmd.service;

import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.event.cmd.feign.*;
import com.taiji.emp.event.cmd.vo.TaskFeedbackSaveVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskExeorgVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.FeedbackGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.msgService.InfoMsgService;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class CmdTaskFeedBackService extends BaseService {

    private CmdTaskFeedBackClient taskFeedBackClient;
    private DocAttClient docAttClient;
    private CmdTaskClient taskClient;
    private InfoMsgService infoMsgService;
    private EventEvaProcessClient processClient;
    private UtilsFeignClient utilsFeignClient;
    private TaskExeorgClient taskExeorgClient;

    /**
     * 新增应急任务
     */
    @Transient
    public void create(TaskFeedbackSaveVo saveVo, Principal principal){

        TaskFeedbackVo vo = saveVo.getEcTaskFeedback();
//        vo.setFeedbackBy(principal.getName()); //创建人
        vo.setFeedbackTime(utilsFeignClient.now().getBody());
        vo.setFeedbackType(FeedbackGlobal.INFO_FEEDBACK_TYPE_ZERO);//反馈
        //设置状态,
        //vo.setCompleteStatus();
        ResponseEntity<TaskFeedbackVo> resultVo = taskFeedBackClient.create(vo);
        TaskFeedbackVo feedbackVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String taskId = vo.getTaskId();
        ResponseEntity<TaskVo> resEntity = taskClient.findOne(taskId);
        TaskVo taskVo = ResponseEntityUtils.achieveResponseEntityBody(resEntity);
        if (vo.getCompleteStatus().equals(FeedbackGlobal.INFO_VARIABLE_ONE)) {
            taskVo.setTaskStatus(FeedbackGlobal.INFO_VARIABLE_TWO);//已完成
        }else if (vo.getCompleteStatus().equals(FeedbackGlobal.INFO_VARIABLE_ZERO))  {
            taskVo.setTaskStatus(FeedbackGlobal.INFO_VARIABLE_ONE);//已完成
        }
        taskClient.update(taskVo,taskId);

        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = saveVo.getFileIds();
        List<String> docAttDelIds = saveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        //添加“过程再现”节点 && 添加系统消息
        addNodeAndSendInfoMsg(principal,feedbackVo);
        ResponseEntityUtils.achieveResponseEntityBody(docResult);

    }

    /**
     * 添加“过程再现”节点 && 添加系统消息
     * @param principal
     */
    private void addNodeAndSendInfoMsg(Principal principal,TaskFeedbackVo feedbackVo) {
        String taskId = feedbackVo.getTaskId();
        ResponseEntity<TaskVo> responseEntity = taskClient.findOne(taskId);
        TaskVo taskVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
        String account = principal.getName();
        if (null != taskVo){
            EventEvaProcessVo processVo = new EventEvaProcessVo();
            processVo.setEventId(taskVo.getEventId());
            processVo.setEventName(taskVo.getEventName());
            StringBuilder builder = new StringBuilder();
            String content = feedbackVo.getContent();
            String completeStatus = feedbackVo.getCompleteStatus();
            String temp = null;
            if (!StringUtils.isEmpty(completeStatus)){
                if (completeStatus.equals(FeedbackGlobal.INFO_VARIABLE_ZERO)){
                    temp = "任务未完成";
                }else if (completeStatus.equals(FeedbackGlobal.INFO_VARIABLE_ONE)){
                    temp = "任务已完成";
                }
            }
            builder.append(temp)
                    .append(",反馈内容：")
                    .append(content);
            processVo.setOperation(builder.toString());
            processVo.setOperationTime(feedbackVo.getFeedbackTime());
            processVo.setFirstNodeCode(ProcessNodeGlobal.DISPOSAL);
            processVo.setFirstNodeName(ProcessNodeGlobal.DISPOSAL_NAME);
            processVo.setSecNodeCode(ProcessNodeGlobal.DISPOSAL_FEEDBACK);
            processVo.setSecNodeName(ProcessNodeGlobal.DISPOSAL_FEEDBACK_NAME);
            processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
            processVo.setOperator(account);
            processVo.setCreateBy(account);
            processVo.setUpdateBy(account);
            //添加“过程再现”节点
            ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
            ResponseEntityUtils.achieveResponseEntityBody(result);
            String id = feedbackVo.getTaskOrgId();
            ResponseEntity<TaskExeorgVo> respEntity = taskExeorgClient.findOne(id);
            TaskExeorgVo taskExeorgVo = ResponseEntityUtils.achieveResponseEntityBody(respEntity);
            Assert.notNull(taskExeorgVo,"taskExeorgVo不能为空");
            String sendOrg = taskExeorgVo.getOrgId();
            String sendOrgName = getOrgVoById(taskExeorgVo.getOrgId()).getOrgName();
            //添加应急任务反馈系统消息
            infoMsgService.sendSystemFeedBackMsg(taskVo,feedbackVo,sendOrgName,sendOrg);
        }
    }

    /**
     * 根据taskId获取单条应急任务的所有反馈
     */
    public List<TaskFeedbackVo> findListByTaskId(String taskId){
        Assert.hasText(taskId,"id不能为空字符串");
        ResponseEntity<List<TaskFeedbackVo>> resultVo = taskFeedBackClient.findListByTaskId(taskId);
        List<TaskFeedbackVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if (!CollectionUtils.isEmpty(vo)){
            for (TaskFeedbackVo feedbackVo : vo){
                String id = feedbackVo.getId();
                ResponseEntity<List<DocAttVo>> clientList = docAttClient.findList(id);
                List<DocAttVo> voList = ResponseEntityUtils.achieveResponseEntityBody(clientList);
                if (voList.size() > 0){
                    feedbackVo.setIsHaveFiles(FeedbackGlobal.INFO_VARIABLE_ONE);
                }else {
                    feedbackVo.setIsHaveFiles(FeedbackGlobal.INFO_VARIABLE_ZERO);
                }
            }
        }
        return vo;
    }

    /**
     * 根据id获取单条反馈
     */
    public TaskFeedbackVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<TaskFeedbackVo> resultVo = taskFeedBackClient.findOne(id);
        TaskFeedbackVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 督办任务反馈信息（含附件）
     * @param saveVo
     * @param principal
     */
    @Transient
    public void tasksupervise(TaskFeedbackSaveVo saveVo, Principal principal){
        TaskFeedbackVo vo = saveVo.getEcTaskFeedback();
        vo.setFeedbackBy(principal.getName()); //创建人
        vo.setFeedbackTime(utilsFeignClient.now().getBody());
        vo.setFeedbackType(FeedbackGlobal.INFO_FEEDBACK_TYPE_ONE);//反馈
        //设置状态,
        //vo.setCompleteStatus();
        ResponseEntity<TaskFeedbackVo> resultVo = taskFeedBackClient.create(vo);
        TaskFeedbackVo feedbackVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String taskId = vo.getTaskId();
        ResponseEntity<TaskVo> resEntity = taskClient.findOne(taskId);
        TaskVo taskVo = ResponseEntityUtils.achieveResponseEntityBody(resEntity);
        if (vo.getCompleteStatus().equals(FeedbackGlobal.INFO_VARIABLE_ONE)) {
            taskVo.setTaskStatus(FeedbackGlobal.INFO_VARIABLE_TWO);}//已完成
//        }else if (vo.getCompleteStatus().equals(FeedbackGlobal.INFO_VARIABLE_ZERO))  {
//            taskVo.setTaskStatus(FeedbackGlobal.INFO_VARIABLE_ONE);//已完成
//        }
        taskClient.update(taskVo,taskId);

        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = saveVo.getFileIds();
        List<String> docAttDelIds = saveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        //添加“过程再现”节点 && 添加系统消息
        addNodeAndSendInfoMsg(principal,feedbackVo);
        ResponseEntityUtils.achieveResponseEntityBody(docResult);

    }
}
