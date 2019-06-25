package com.taiji.emp.screenShow.controller;

import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.cmd.vo.*;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.event.infoConfig.vo.AcceptInformSearchVo;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import com.taiji.emp.res.vo.PlanMainOrgVo;
import com.taiji.emp.screenShow.service.CmdService;
import com.taiji.emp.screenShow.vo.SchemePlanDTO;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cmd")
public class CmdController {

    CmdService service;

    /**
     * 根据事件ID获取事件处置方案基本信息和已关联的应急预案信息
     */
    @GetMapping(path = "/schemes")
    public ResultEntity findSchemeVoByEventId(
            @NotEmpty(message = "eventId 不能为空字符串")
            @RequestParam(value = "eventId") String eventId){
        SchemePlanDTO resultVo = service.findSchemeVoAndCmdPlanByEventId(eventId);
        if(null!=resultVo){
            return ResultUtils.success(resultVo);
        }else{
            return ResultUtils.fail(ResultCodeEnum.INTERNAL_ERROR,"该事件未查询到相应方案");
        }
    }

    /**
     * 根据条件查询应急组织机构树信息
     * @param map
     * 参数key:schemeId,planId
     */
    @PostMapping(path ="/emgorgs/searchAll")
    public ResultEntity findEmgOrgsAll(@RequestBody Map<String,Object> map){
        if(map.containsKey("schemeId")&&map.containsKey("planId")){

            List<CmdOrgVo> resultVoList = service.findEmgOrgsAll(map);
            CmdOrgVo topVo = new CmdOrgVo();
            topVo.setId("1");
            topVo.setParentId("1");
            topVo.setName("应急组织");
            topVo.setLeaf("false");
            topVo.setOrders(1);
            resultVoList.add(topVo);
            List<CmdOrgVo> root = AssemblyTreeUtils.assemblyTree(resultVoList); //组装成树形

            return ResultUtils.success(root);

        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据条件查询责任单位/人信息
     * key:emgOrgId
     */
    @PostMapping(path ="/orgrespons/searchAll")
    public ResultEntity findOrgResponsAll(@RequestBody Map<String,Object> map){
        List<OrgResponVo> resultVoList = service.findOrgResponsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据条件查询处置方案已关联的应急专家信息
     * {
     "schemeId": "string",
     "name": "string",
     "specialty": "string"
     }
     */
    @PostMapping(path ="/experts/searchAll")
    public ResultEntity findExpertsAll(@RequestBody Map<String,Object> map){
        List<CmdExpertVo> resultVoList = service.findExpertsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据条件查询处置方案已关联的应急队伍信息
     * {
     "schemeId": "string",
     "name": "string",
     "teamTypeName": "string"
     }
     */
    @PostMapping(path ="/teams/searchAll")
    public ResultEntity findTeamsAll(@RequestBody Map<String,Object> map){
        List<CmdTeamVo> resultVoList = service.findTeamsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据条件查询处置方案已关联的应急物资信息
     * {
     "schemeId": "string",
     "name": "string",
     "resTypeName: "string",
     }
     */
    @PostMapping(path ="/materials/searchAll")
    public ResultEntity findMaterialsAll(@RequestBody Map<String,Object> map){
        List<CmdMaterialVo> resultVoList = service.findMaterialsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据条件查询处置方案已关联的社会依托资源信息
     * {
     "schemeId": "string",
     "name": "string",
     "address": "string"
     }
     */
    @PostMapping(path ="/supports/searchAll")
    public ResultEntity findSupportsAll(@RequestBody Map<String,Object> map){
        List<CmdSupportVo> resultVoList = service.findSupportsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 查询应急任务列表 --- 不分页
     */
    @PostMapping(path = "/tasks/searchAll")
    public ResultEntity findList(
            @RequestBody TaskPageVo taskPageVo){
        List<TaskVo> listVo = service.findList(taskPageVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据任务id获取 对应的所以反馈信息
     */
    @GetMapping(path = "/taskfeedback")
    public ResultEntity findTaskByTaskId(
            @NotEmpty(message = "taskId不能为空")
            @RequestParam(value = "taskId") String taskId){
        List<TaskFeedbackVo> vo = service.findListByTaskId(taskId);
        return ResultUtils.success(vo);
    }

    /**
     * 获取应急预案中的主责机构相关的信息
     * @param planId
     * @return
     */
    @GetMapping(path = "/planMainOrgs")
    public ResultEntity planMainOrgs(
            @NotEmpty(message = "planId不能为空")
            @RequestParam(value = "planId") String planId){
        List<PlanMainOrgVo> vo = service.planMainOrgs(planId);
        return ResultUtils.success(vo);
    }

    /**
     * 获取一键事故的描述信息
     * @param eventTypeId
     * @return
     */
    @GetMapping(path = "/eventDesc")
    public ResultEntity eventDesc(
            @NotEmpty(message = "eventTypeId不能为空")
            @RequestParam(value = "eventTypeId") String eventTypeId){
        List<AcceptRuleVo> vo = service.eventDesc(eventTypeId);
        return ResultUtils.success(vo);
    }

    /**
     * 根据条件查询通知单位列表
     * @param acceptInformVo
     * @return
     */
    @PostMapping(path = "/searchAcceptInforms")
    public ResultEntity searchAcceptInforms(
            @RequestBody AcceptInformSearchVo acceptInformVo){
        List<AcceptInformVo> listVo = service.searchAcceptInforms(acceptInformVo);
        return ResultUtils.success(listVo);
    }
}
