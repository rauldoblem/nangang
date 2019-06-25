package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.service.TeamService;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/teams")
public class TeamController extends BaseController{

    @Autowired
    TeamService service;

    /**
     * 新增救援队伍
     */
    @PostMapping
    public ResultEntity addTeam(
            @Validated
            @NotNull(message = "TeamVo不能为null")
            @RequestBody TeamVo teamVo, Principal principal){
        service.create(teamVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改救援队伍
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateTeam(
            @NotNull(message = "TeamVo不能为null")
            @RequestBody TeamVo teamVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(teamVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条救援队伍信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findTeamById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        TeamVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条救援队伍信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteTeam(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询救援队伍列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findTeams(@RequestBody TeamPageVo teamPageVo){
        RestPageImpl<TeamVo> pageVo = service.findPage(teamPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询救援队伍列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findTeamsAll(@RequestBody TeamListVo teamListVo){
        List<TeamVo> listVo = service.findList(teamListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据GIS数据格式要求获取救援队伍图层信息-不分页
     */
    @PostMapping(path = "/searchAllForGis")
    public ResultEntity searchAllTeamForGis(){
        List<Map<String,Object>> map = service.searchAllTeamForGis();
        return ResultUtils.success(map);
    }

    /**
     * 根据组织机构获取该机构下对应的所有应急队伍（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     */
    @PostMapping(path = "/searchTeamForGisByOrg")
    public ResultEntity searchTeamForGisByOrg(
            @Validated
            @NotNull(message = "teamPageForGisVo不能为空")
            @RequestBody PageForGisVo teamPageForGisVo){

        RestPageImpl<Map<String,Object>> pageVo = service.findPage(teamPageForGisVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的所有应急队伍（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    @GetMapping(path = "/searchAllTeamForGisByOrg")
    public ResultEntity searchAllTeamForGisByOrg(
            @Validated
            @NotEmpty(message = "orgCode不能为空")
            @RequestParam(name = "orgCode") String orgCode){
        List<Map<String,Object>> map = service.searchAllTeamForGisByOrg(orgCode);
        return ResultUtils.success(map);
    }

    /**
     * 根据查询条件导出救援队伍信息Excel
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody TeamListVo teamListVo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("救援队伍.xlsx", "utf-8"));
        OutputStream os = response.getOutputStream();
        service.exportToExcel(os,teamListVo);
        return ResultUtils.success();
    }

}
