package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.CmdTeamClient;
import com.taiji.emp.event.cmd.feign.PlanTeamClient;
import com.taiji.emp.event.cmd.feign.TeamClient;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.cmd.vo.CmdTeamVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmdTeamService extends BaseService {

    @Autowired
    private CmdTeamClient client;
    @Autowired
    private TeamClient teamClient;
    @Autowired
    private PlanTeamClient planTeamClient;

    //新增关联应急队伍
    public void addEcTeams(String schemeId,List<String> teamIds,Principal principal){
        Assert.hasText(schemeId,"schemeId 不能为空");
        TeamListVo teamListVo = new TeamListVo();
        teamListVo.setTeamIds(teamIds);
        ResponseEntity<List<TeamVo>> teamResultList = teamClient.findList(teamListVo);
        List<TeamVo> teamList = ResponseEntityUtils.achieveResponseEntityBody(teamResultList);
        if(null!=teamList && teamList.size()>0){
            List<CmdTeamVo> cmdTeamVos = new ArrayList<>();
            for(TeamVo teamVo:teamList){
                cmdTeamVos.add(teamToCmdTeam(schemeId,teamVo,principal));
            }
            ResponseEntity<List<CmdTeamVo>> result = client.createList(cmdTeamVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

    //应急队伍对象转化为应急处置队伍关联对象
    private CmdTeamVo teamToCmdTeam(String schemeId,TeamVo teamVo,Principal principal){
        CmdTeamVo cmdTeamVo = new CmdTeamVo();
        cmdTeamVo.setPrincipal(teamVo.getPrincipal());
        cmdTeamVo.setPrincipalTel(teamVo.getPrincipalTel());
        cmdTeamVo.setSchemeId(schemeId);
        cmdTeamVo.setTeamAddress(teamVo.getAddress());
        cmdTeamVo.setTeamId(teamVo.getId());
        cmdTeamVo.setTeamName(teamVo.getName());
        cmdTeamVo.setTeamTypeName(teamVo.getTeamTypeName());
        cmdTeamVo.setCreateBy(principal.getName());
        cmdTeamVo.setUpdateBy(principal.getName());
        return cmdTeamVo;
    }

    //删除关联的应急队伍
    public void deleteEcTeam(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的应急队伍信息
    public List<CmdTeamVo> findTeamsAll(Map<String,Object> map){
        ResponseEntity<List<CmdTeamVo>> result = client.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据启动的预案数字化信息生成关联的应急队伍信息
    public void addPlanTeams(CmdPlansVo cmdPlansVo, Principal principal){
        String schemeId = cmdPlansVo.getSchemeId();
        Assert.hasText(schemeId,"schemeId 不能为空");

        List<String> planIds = cmdPlansVo.getPlanIds();
        if(planIds.size()<=0){
            return;
        }
        //调用 预案数字化中 根据预案ID数组，查询其对应的所有队伍信息（去重复）-不分页 接口
        PlanTeamListVo planTeamListVo = new PlanTeamListVo();
        planTeamListVo.setPlanIds(planIds);
        ResponseEntity<List<TeamVo>> teamResultList = planTeamClient.findByPlanIds(planTeamListVo);
        List<TeamVo> teamVoList = ResponseEntityUtils.achieveResponseEntityBody(teamResultList);

        if(null!=teamVoList && teamVoList.size()>0){
            List<CmdTeamVo> cmdTeamVos = new ArrayList<>();
            for(TeamVo teamVo:teamVoList){
                cmdTeamVos.add(teamToCmdTeam(schemeId,teamVo,principal));
            }
            ResponseEntity<List<CmdTeamVo>> result = client.createList(cmdTeamVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }

    }

    /**
     * 2019年1月18日新增】按照地图格式要求获取方案中的队伍信息
     * @param cmdPlansVo
     * @return
     */
    public List<Map<String,Object>> getTeamsForGis(CmdPlansVo cmdPlansVo) {
        List<Map<String,Object>> list = new ArrayList<>();
        String schemeId = cmdPlansVo.getSchemeId();
        ResponseEntity<List<TeamVo>> resultVo = teamClient.findBySchemeId(schemeId);
        List<TeamVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if (!CollectionUtils.isEmpty(voList)){
            for (TeamVo vo : voList){
                Map<String,Object> map = new HashMap<>();
                Map<String,Object> geometryMap = new HashMap<>();
                geometryMap.put("type","Point");
                Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                if(null==coordinatesArray){
                    continue; //经纬度解析结果为 null，则跳出循环
                }
                geometryMap.put("coordinates",coordinatesArray);
                map.put("properties",vo);
                map.put("type","Feature");
                map.put("geometry",geometryMap);
                list.add(map);
            }
        }
        return list;
    }
}
