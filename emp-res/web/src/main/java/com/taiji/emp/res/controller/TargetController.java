package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.target.TargetSearchVo;
import com.taiji.emp.res.service.TargetService;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.emp.res.vo.TargetVo;
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
@RequestMapping("/targets")
public class TargetController extends BaseController {

    @Autowired
    TargetService service;

    /**
     * 新增防护目标信息
     * @param targetVo
     * @param principal
     * @return
     */
    @PostMapping
    public ResultEntity createRcTarget(
            @Validated
            @NotNull(message = "TargetSaveVo不能为null")
            @RequestBody TargetVo targetVo,
            Principal principal){
        service.create(targetVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改防护目标信息
     * @param rcTargetVo
     * @param principal
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updateRcTarget(
            @NotNull(message = "targetSaveVo不能为空")
            @RequestBody TargetVo rcTargetVo
            ,Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        rcTargetVo.setId(id);
        service.update(rcTargetVo,principal);
         return ResultUtils.success();
    }

    /**
     * 根据id获取单条防护目标信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findRcTargetById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")
            String id){
        TargetVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id删除单条防护目标信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteRcTarget(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询防护目标信息列表------分页
     * @param vo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody TargetSearchVo vo){
         //验证分页参数
         RestPageImpl<TargetVo> pageVo = service.findPage(vo);
         return ResultUtils.success(pageVo);
    }

    /**
     * 查询防护目标信息列表------不分页
     * @param params
     * @return
     */
    /*@PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody Map<String,Object> params){
        List<TargetVo> listVo = service.findList(params);
        return ResultUtils.success(listVo);
    }*/

    /**
     * 根据GIS数据格式要求获取防护目标图层信息-不分页
     */
    @PostMapping(path = "/searchAllForGis")
    public ResultEntity searchAllTargetForGis(){
        List<Map<String,Object>> map = service.searchAllTargetForGis();
        return ResultUtils.success(map);
    }

    /**
     * 根据组织机构获取该机构下对应的所有防护目标（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     */
    @PostMapping(path = "/searchTargetForGisByOrg")
    public ResultEntity searchTargetForGisByOrg(
            @Validated
            @NotNull(message = "targetPageForGisVo不能为空")
            @RequestBody PageForGisVo targetPageForGisVo){

        RestPageImpl<Map<String,Object>> pageVo = service.findPage(targetPageForGisVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的所有防护目标（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    @GetMapping(path = "/searchAllTargetForGisByOrg")
    public ResultEntity searchAllTargetForGisByOrg(
            @Validated
            @NotEmpty(message = "orgCode不能为空")
            @RequestParam(name = "orgCode") String orgCode){
        List<Map<String,Object>> map = service.searchAllTargetForGisByOrg(orgCode);
        return ResultUtils.success(map);
    }

    /**
     * 根据查询条件导出防护目标信息Excel
     * @param targetSearchVo
     * @return
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody TargetSearchVo targetSearchVo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("重点防护目标.xlsx", "utf-8"));
        OutputStream os = response.getOutputStream();
        service.exportToExcel(os,targetSearchVo);
        return ResultUtils.success();
    }
}
