package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.service.SupportService;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.emp.res.vo.SupportVo;
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
@RequestMapping("/supports")
public class SupportController extends BaseController {

    @Autowired
    SupportService service;

    /**
     * 新增社会依托资源
     */
    @PostMapping
    public ResultEntity addSupport(
            @Validated
            @NotNull(message = "SupportVo不能为null")
            @RequestBody SupportVo supportVo, Principal principal){
        service.create(supportVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改社会依托资源
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateSupport(
            @NotNull(message = "SupportVo不能为null")
            @RequestBody SupportVo supportVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(supportVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条社会依托资源
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findSupportById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        SupportVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条社会依托资源
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteSupport(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询应急社会依托资源 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findSupports(@RequestBody SupportPageVo supportPageVo){
        RestPageImpl<SupportVo> pageVo = service.findPage(supportPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询应急社会依托资源 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findSupportsAll(@RequestBody SupportListVo supportListVo){
        List<SupportVo> listVo = service.findList(supportListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据GIS数据格式要求获取社会依托资源图层信息-不分页
     */
    @PostMapping(path = "/searchAllForGis")
    public ResultEntity searchAllSupportForGis(){
        List<Map<String,Object>> map = service.searchAllSupportForGis();
        return ResultUtils.success(map);
    }

    /**
     * 根据组织机构获取该机构下对应的所有社会依托资源（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     */
    @PostMapping(path = "/searchSupportForGisByOrg")
    public ResultEntity searchSupportForGisByOrg(
            @Validated
            @NotNull(message = "supportPageForGisVo不能为空")
            @RequestBody PageForGisVo supportPageForGisVo){

        RestPageImpl<Map<String,Object>> pageVo = service.findPage(supportPageForGisVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的社会依托资源（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    @GetMapping(path = "/searchAllSupportForGisByOrg")
    public ResultEntity searchAllSupportForGisByOrg(
            @Validated
            @NotEmpty(message = "orgCode不能为空")
            @RequestParam(name = "orgCode") String orgCode){
        List<Map<String,Object>> map = service.searchAllSupportForGisByOrg(orgCode);
        return ResultUtils.success(map);
    }

    /**
     * 根据查询条件导出社会依托资源信息Excel
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody SupportListVo supportListVo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("避难场所.xlsx", "utf-8"));
        OutputStream os = response.getOutputStream();
        service.exportToExcel(os,supportListVo);
        return ResultUtils.success();
    }
}
