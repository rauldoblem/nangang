package com.taiji.emp.screenShow.service;

import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.cmd.vo.*;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.event.common.constant.FeedbackGlobal;
import com.taiji.emp.event.infoConfig.vo.AcceptInformSearchVo;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import com.taiji.emp.nangang.feign.CmdTaskClient;
import com.taiji.emp.nangang.feign.CmdTaskFeedBackClient;
import com.taiji.emp.nangang.feign.DocAttClient;
import com.taiji.emp.nangang.service.BaseService;
import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.vo.PlanMainOrgVo;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.emp.res.vo.PlanOrgResponVo;
import com.taiji.emp.res.vo.PlanOrgVo;
import com.taiji.emp.screenShow.feign.*;
import com.taiji.emp.screenShow.vo.SchemePlanDTO;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CmdService extends BaseService {

    @Autowired
    private SchemeClient schemeClient;
    @Autowired
    private CmdPlanClient cmdPlanClient;
    @Autowired
    private CmdOrgClient cmdOrgClient;
    @Autowired
    private OrgResponClient orgResponClient;
    @Autowired
    private CmdExpertClient cmdExpertClient;
    @Autowired
    private CmdTeamClient cmdTeamClient;
    @Autowired
    private CmdMaterialClient cmdMaterialClient;
    @Autowired
    private CmdSupportClient cmdSupportClient;
    @Autowired
    private CmdTaskClient taskClient;
    @Autowired
    private CmdTaskFeedBackClient taskFeedBackClient;
    @Autowired
    private DocAttClient docAttClient;
    @Autowired
    private PlanOrgClient planOrgClient;
    @Autowired
    private PlanOrgResponDetailClient planOrgResponDetailClient;
    @Autowired
    private PlanOrgResponClient planOrgResponClient;
    @Autowired
    private AcceptRuleClient acceptRuleClient;
    @Autowired
    private AcceptInformClient acceptInformClient;

    //根据事件ID获取事件处置方案基本信息
    public SchemePlanDTO findSchemeVoAndCmdPlanByEventId(String eventId){
        Assert.hasText(eventId,"eventId不能为空");
        ResponseEntity<SchemeVo> resultVo = schemeClient.findByEventId(eventId);
        SchemeVo schemeVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(null == schemeVo ){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("schemeId",schemeVo.getId());
        List<CmdPlanVo> plans = findPlansAll(map);
        return new SchemePlanDTO(schemeVo.getId(),schemeVo.getSchemeName(),schemeVo.getEventId(),plans);
    }
    //根据条件查询处置方案已关联的应急预案信息
    public List<CmdPlanVo> findPlansAll(Map<String,Object> map){
        ResponseEntity<List<CmdPlanVo>> result = cmdPlanClient.searchAll(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询应急组织机构树信息
    public List<CmdOrgVo> findEmgOrgsAll(Map<String,Object> map){
        ResponseEntity<List<CmdOrgVo>> result = cmdOrgClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询责任单位/人信息
    public List<OrgResponVo> findOrgResponsAll(Map<String,Object> map){
        ResponseEntity<List<OrgResponVo>> resultVoList = orgResponClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(resultVoList);
    }

    //根据条件查询处置方案已关联的应急专家信息
    public List<CmdExpertVo> findExpertsAll(Map<String,Object> map){
        ResponseEntity<List<CmdExpertVo>> result = cmdExpertClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的应急队伍信息
    public List<CmdTeamVo> findTeamsAll(Map<String,Object> map){
        ResponseEntity<List<CmdTeamVo>> result = cmdTeamClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的应急物资信息
    public List<CmdMaterialVo> findMaterialsAll(Map<String,Object> map){
        ResponseEntity<List<CmdMaterialVo>> result = cmdMaterialClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的社会依托资源信息
    public List<CmdSupportVo> findSupportsAll(Map<String,Object> map){
        ResponseEntity<List<CmdSupportVo>> result = cmdSupportClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 获取应急任务list(不带分页)
     */
    public List<TaskVo> findList(TaskPageVo taskPageVo){
        Assert.notNull(taskPageVo,"params 不能为空");
        ResponseEntity<List<TaskVo>> resultVo = taskClient.findList(taskPageVo);
        List<TaskVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //根据taskId获取单条应急任务的所有反馈
        for (TaskVo taskVos:vo) {
            String id = taskVos.getId();
            if (!StringUtils.isEmpty(id)){
                List<TaskFeedbackVo> taskFeedbackVos = findListByTaskId(id);
                taskVos.setTaskFeedback(taskFeedbackVos);
            }
        }
        return vo;
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
     * 获取应急预案中的主责机构相关的信息
     * @param planId
     * @return
     */
    public List<PlanMainOrgVo> planMainOrgs(String planId) {
        List<PlanMainOrgVo> returnList = new ArrayList<PlanMainOrgVo>();
        List<PlanOrgResponDetailVo> responDetailVos = null;
        List<PlanOrgResponVo> planOrgResponVos = null;
        Assert.hasText(planId,"planId不能为空字符串");
        PlanOrgListVo vo = new PlanOrgListVo();
        vo.setPlanId(planId);
        vo.setIsMain("1");
        ResponseEntity<List<PlanOrgVo>> planOrgClientList = planOrgClient.findList(vo);
        //获取planOrgId和planOrgName
        List<PlanOrgVo> planOrgVoList = ResponseEntityUtils.achieveResponseEntityBody(planOrgClientList);
        List<String> planOrgIdList = planOrgVoList.stream().map(temp -> temp.getId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(planOrgVoList)){
            PlanOrgResponVo orgResponVo = new PlanOrgResponVo();
            orgResponVo.setPlanOrgIds(planOrgIdList);
            //预案责任信息
            ResponseEntity<List<PlanOrgResponVo>> orgResponClientList = planOrgResponClient.findList(orgResponVo);
            planOrgResponVos = ResponseEntityUtils.achieveResponseEntityBody(orgResponClientList);
            if (!CollectionUtils.isEmpty(planOrgResponVos)){
                List<String> orgResponIdList = planOrgResponVos.stream().map(temp -> temp.getId()).collect(Collectors.toList());
                PlanOrgResponDetailListVo detailListVo = new PlanOrgResponDetailListVo();
                detailListVo.setIds(orgResponIdList);
                ResponseEntity<List<PlanOrgResponDetailVo>> responDetailClientList = planOrgResponDetailClient.findList(detailListVo);
                //预案责任信息明细
                responDetailVos = ResponseEntityUtils.achieveResponseEntityBody(responDetailClientList);
            }
            for (PlanOrgVo planOrgVo : planOrgVoList){
                String planOrgId = planOrgVo.getId();
                PlanMainOrgVo entity = new PlanMainOrgVo();
                entity.setOrgName(planOrgVo.getName());
                List<PlanOrgResponVo> responListVo = new ArrayList<>();
                for (PlanOrgResponVo responVo : planOrgResponVos){
                    String planOrgIdOther = responVo.getPlanOrgId();
                    if (planOrgId.equals(planOrgIdOther)) {
                        responListVo.add(responVo);
                    }
                }
                if (!CollectionUtils.isEmpty(responListVo)){
                    for (PlanOrgResponVo responVo : responListVo){
                        String responVoId = responVo.getId();
                        List<PlanOrgResponDetailVo> detailEntity = new ArrayList<>();
                        for (PlanOrgResponDetailVo detailVo : responDetailVos){
                            String orgResponId = detailVo.getOrgResponId();
                            if (responVoId.equals(orgResponId)){
                                detailEntity.add(detailVo);
                            }
                        }
                        responVo.setDetails(detailEntity);
                    }
                }
                entity.setEcOrgRespons(responListVo);
                returnList.add(entity);
            }
            return returnList;
        }else {
            return returnList;
        }
    }

    /**
     * 获取一键事故的描述信息
     * @param eventTypeId
     * @return
     */
    public List<AcceptRuleVo> eventDesc(String eventTypeId) {
        ResponseEntity<List<AcceptRuleVo>> resultVo = acceptRuleClient.eventDesc(eventTypeId);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 根据条件查询通知单位列表
     * @param acceptInformVo
     * @return
     */
    public List<AcceptInformVo> searchAcceptInforms(AcceptInformSearchVo acceptInformVo) {
        ResponseEntity<List<AcceptInformVo>> resultVo = acceptInformClient.searchAcceptInforms(acceptInformVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }
}
