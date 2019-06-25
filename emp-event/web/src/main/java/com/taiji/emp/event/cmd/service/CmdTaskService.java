package com.taiji.emp.event.cmd.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.event.cmd.feign.*;
import com.taiji.emp.event.cmd.searchVo.*;
import com.taiji.emp.event.cmd.vo.SchemeVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskExeorgVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.emp.event.msgService.InfoMsgService;
import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.vo.PlanTaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CmdTaskService extends BaseService {

    private CmdTaskClient taskClient;
    private UtilsFeignClient utilsFeignClient;
    private PlanTaskClient planTaskClient;
    private DocAttClient docAttClient;
    private EventClient eventClient;
    private SchemeClient schemeClient;
    private InfoMsgService infoMsgService;
    private EventEvaProcessClient processClient;

    /**
     * 新增应急任务
     */
    public void create(TaskVo vo, Principal principal){
        String userName  = principal.getName();
        vo.setCreateBy(userName); //创建人
        vo.setTaskStatus(EventGlobal.EVENT_TASK_UNSEND);//设置状态为0
        //设置部门
        UserVo userVo = getCurrentUser(principal);
        UserProfileVo userProfileVo = userVo.getProfile();
        String orgId = userProfileVo.getOrgId();
        String orgName = userProfileVo.getOrgName();
        vo.setCreateOrgId(orgId);
        vo.setCreateOrgName(orgName);
        taskClient.create(vo);
    }


    /**
     * 更新单条应急任务
     */
    public void update(TaskVo vo, Principal principal,String id) {
        String userName  = principal.getName(); //获取用户姓名
        vo.setUpdateBy(userName); //更新人
        taskClient.update(vo,id);
    }


    /**
     * 根据id获取单条应急任务
     */
    public TaskVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<TaskVo> resultVo = taskClient.findOne(id);
        TaskVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条应急任务
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        taskClient.deleteLogic(id);
    }


    /**
     * 获取应急任务分页list
     */
    public RestPageImpl<TaskVo> findPage(TaskPageVo taskPageVo,Principal principal){
        Assert.notNull(taskPageVo,"params 不能为空");
        String orgId = getCurrentUser(principal).getProfile().getOrgId();
        taskPageVo.setOrgId(orgId);
        ResponseEntity<RestPageImpl<TaskVo>> resultVo = taskClient.findPage(taskPageVo);
        RestPageImpl<TaskVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取应急任务list(不带分页)
     */
    public List<TaskVo> findList(TaskPageVo taskPageVo,Principal principal){
        Assert.notNull(taskPageVo,"params 不能为空");
        String orgId = getCurrentUser(principal).getProfile().getOrgId();
        taskPageVo.setOrgId(orgId);
        ResponseEntity<List<TaskVo>> resultVo = taskClient.findList(taskPageVo);
        List<TaskVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }



    /**
     * 根据id将应急任务下发
     */
    public void dispatch(DispatchVo dispatchVo,Principal principal){
        Assert.notNull(dispatchVo.getTaskId(),"dispatchVo 不能为空");
        Assert.hasText(dispatchVo.getTaskId(),"TaskId不能为空串");
        dispatchVo.setSendTime(utilsFeignClient.now().getBody());
        ResponseEntity<Void> resultVo = taskClient.dispatch(dispatchVo);
        //添加“过程再现”节点 && 添加系统消息
        addNodeAndSendInfoMsg(dispatchVo,principal);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 添加“过程再现”节点 && 添加系统消息
     * @param dispatchVo
     */
    private void addNodeAndSendInfoMsg(DispatchVo dispatchVo,Principal principal) {
        ResponseEntity<TaskVo> responseEntity = taskClient.findOne(dispatchVo.getTaskId());
        TaskVo taskVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
        String account = principal.getName();
        if (null != taskVo){
            TaskExeorgVo taskExeorg = taskVo.getTaskExeorg();
            Assert.notNull(taskExeorg,"taskExeorg不能为空");
            EventEvaProcessVo processVo = new EventEvaProcessVo();
            processVo.setEventId(taskVo.getEventId());
            processVo.setEventName(taskVo.getEventName());
            StringBuilder builder = new StringBuilder();
            String orgName = taskExeorg.getOrgName();
            String principalName = taskExeorg.getPrincipal();
            String title = taskVo.getName();
            builder.append("下发任务给")
                    .append(orgName)
                    .append(",负责人：")
                    .append(principalName)
                    .append(",任务标题：")
                    .append(title);
            processVo.setOperation(builder.toString());
            processVo.setOperationTime(taskExeorg.getSendTime());
            processVo.setFirstNodeCode(ProcessNodeGlobal.DISPOSAL);
            processVo.setFirstNodeName(ProcessNodeGlobal.DISPOSAL_NAME);
            processVo.setSecNodeCode(ProcessNodeGlobal.DISPOSAL_TASK_SEND);
            processVo.setSecNodeName(ProcessNodeGlobal.DISPOSAL_TASK_SEND_NAME);
            processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
            processVo.setOperator(account);
            processVo.setCreateBy(account);
            processVo.setUpdateBy(account);

            //添加“过程再现”节点
            ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
            ResponseEntityUtils.achieveResponseEntityBody(result);
            //添加应急任务下发系统消息
            infoMsgService.sendSystemDispatchMsg(taskVo);
        }
    }


    /**
     * 根据预案生成任务信息
     * @param buildTaskVo
     * @param principal
     */
//    @Transient
    public void buildTask(BuildTaskVo buildTaskVo, Principal principal){
        Assert.notNull(buildTaskVo,"buildTaskVo 不能为空");
        String schemeId = buildTaskVo.getSchemeId();
        Assert.hasText( schemeId,"schemeId 不能为空");
        Assert.notNull( buildTaskVo.getPlanIds(),"planIds 不能为空");
        String eventId = buildTaskVo.getEventId();
        Assert.notNull( eventId,"eventId 不能为空");
        String eventName =buildTaskVo.getEventName();
        String schemeName = buildTaskVo.getSchemeName();
        PlanTaskListVo planTaskListVo = new PlanTaskListVo();
        planTaskListVo.setPlanIds(buildTaskVo.getPlanIds());
        ResponseEntity<List<PlanTaskVo>> planTaskList = planTaskClient.findByPlanIds(planTaskListVo);
        List<PlanTaskVo> list = ResponseEntityUtils.achieveResponseEntityBody(planTaskList);
        List<TaskVo> voList = new ArrayList<>(list.size());
        for (PlanTaskVo vo: list) {
            TaskVo taskVo = new TaskVo();
            taskVo.setEventId(eventId);
            taskVo.setEventName(eventName);
            taskVo.setSchemeId(schemeId);
            taskVo.setSchemeName(schemeName);
            taskVo.setPlanId(vo.getPlanID());
            taskVo.setCreateBy(principal.getName());
            taskVo.setName(vo.getTitle());
            taskVo.setContent(vo.getContent());
            //添加创建人单位ID，创建人单位名称
            String orgId = getCurrentUser(principal).getProfile().getOrgId();
            String orgName = getCurrentUser(principal).getProfile().getOrgName();
            taskVo.setCreateOrgId(orgId);
            taskVo.setCreateOrgName(orgName);
            //任务状态(0.未下发1.已下发2.已完成）
            taskVo.setTaskStatus("0");
            taskVo.setPlanTaskId(vo.getId());
                TaskExeorgVo taskExeorgVo = new TaskExeorgVo();
                taskExeorgVo.setPrincipal(vo.getPrinciName());
                taskExeorgVo.setPrincipalTel(vo.getPrinciTel());
                taskExeorgVo.setOrgId(vo.getPrinciOrgId());
                taskExeorgVo.setOrgName(vo.getPrinciOrgName());
            taskVo.setTaskExeorg(taskExeorgVo);
            voList.add(taskVo);
        }
        taskClient.createList(voList);
    }

    /**
     * 任务时间轴信息
     * @param timeAxisTaskVo
     * @return
     */
    public List<EcTaskListVo> timeAxisTask(TimeAxisTaskVo timeAxisTaskVo){
        Assert.hasText(timeAxisTaskVo.getSchemeId(),"SchemeId 不能为空或空字符串");
        ResponseEntity<List<EcTaskListVo>> resultVo = taskClient.timeAxisTask(timeAxisTaskVo);
        List<EcTaskListVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //添加是否包含附件，0不包含，1包含
        for (EcTaskListVo vos:vo) {
            List<EcTaskVo> ecTaskVos = vos.getTasks();
            for (EcTaskVo taskVo:ecTaskVos) {
                List<TaskFeedbackVo> taskFeedback = taskVo.getTaskFeedback();
                for (TaskFeedbackVo taskFeedbackVo:taskFeedback) {
                    ResponseEntity<List<DocAttVo>> list = docAttClient.findList(taskFeedbackVo.getId());
                    if(list.getBody().size()==0){
                        //不包含附件
                        taskFeedbackVo.setIsHaveFiles(EventGlobal.EVENT_TASK_FEEDBACK_NOT_COVER);
                    }else {
                        //包含附件
                        taskFeedbackVo.setIsHaveFiles(EventGlobal.EVENT_TASK_FEEDBACK_COVER);
                    }
                }
            }
        }
        return vo;
    }

    /**
     * 任务和反馈信息 条数
     * @param timeAxisTaskVo
     * @return
     */
    public Map<String,Integer> taskStat(TimeAxisTaskVo timeAxisTaskVo){
        Assert.notNull(timeAxisTaskVo,"buildTaskVo 不能为空");
        Assert.hasText(timeAxisTaskVo.getSchemeId(),"SchemeId 不能为空");
        ResponseEntity<List<EcTaskListVo>> resultVo = taskClient.timeAxisTask(timeAxisTaskVo);
        List<EcTaskListVo> vos = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        Integer taskTotalNum = 0;
        Integer feedbackTotalNum = 0;

        for (EcTaskListVo vo:vos) {
            taskTotalNum += vo.getTasks().size();
            List<EcTaskVo> tasks = vo.getTasks();
            for (EcTaskVo taskVo:tasks) {
                feedbackTotalNum += taskVo.getTaskFeedback().size();
            }
        }
        Map<String,Integer> map = new HashMap<>(2);
        map.put("taskTotalNum",taskTotalNum);
        map.put("feedbackTotalNum",feedbackTotalNum);
        return map;
    }
}
