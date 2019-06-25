package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdTeamService;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.cmd.vo.CmdTeamVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/teams")
public class CmdTeamController {

    @Autowired
    private CmdTeamService service;

    /**
     * 新增关联应急队伍
     * {
     "teamIds": [
     "string"
     ],
     "schemeId":"string"
     }
     */
    @PostMapping
    public ResultEntity addEcTeams(@RequestBody Map<String,Object> map, Principal principal){
        if(map.containsKey("teamIds")&&map.containsKey("schemeId")){
            String schemeId =map.get("schemeId").toString();
            List<String> teamIds = (List<String> )map.get("teamIds");
            if(null!=teamIds||teamIds.size()>0){
                service.addEcTeams(schemeId,teamIds,principal);
                return ResultUtils.success();
            }else{
                return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"关联的应急队伍为空");
            }
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 删除关联的应急队伍
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEcTeam(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEcTeam(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询处置方案已关联的应急队伍信息
     * {
     "schemeId": "string"
     }
     */
    @PostMapping(path ="/searchAll")
    public ResultEntity findTeamsAll(@RequestBody Map<String,Object> map){
        List<CmdTeamVo> resultVoList = service.findTeamsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据启动的预案数字化信息生成关联的应急队伍信息
     */
    @PostMapping(path ="/plans")
    public ResultEntity addPlanTeams(
            @NotNull(message = "CmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo, Principal principal){
        service.addPlanTeams(cmdPlansVo,principal);
        return ResultUtils.success();
    }

    /**
     * 【2019年1月18日新增】按照地图格式要求获取方案中的队伍信息
     */
    @PostMapping(path ="/getTeamsForGis")
    public ResultEntity getTeamsForGis(
            @NotNull(message = "cmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo){
        List<Map<String,Object>> resultVoList = service.getTeamsForGis(cmdPlansVo);
        return ResultUtils.success(resultVoList);
    }
}
