package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.res.service.HazardService;
import com.taiji.emp.res.vo.HazardVo;
import com.taiji.emp.res.vo.PageForGisVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
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
@AllArgsConstructor
@RequestMapping("/hazards")
public class HazardsController extends BaseController {

    private HazardService service;
    /**
     * 新增危险源
     */
    @PostMapping
    public ResultEntity createHazard(
            @Validated
            @NotNull(message = "HazardVo不能为null")
            @RequestBody HazardVo hazardVo, Principal principal){
        service.create(hazardVo,principal);
        return ResultUtils.success();
    }


    /**
     * 修改危险源
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateHazard(
            @Validated
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            @NotNull(message = "HazardVo不能为null")
            @RequestBody HazardVo hazardVo, Principal principal){
        service.update(hazardVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条危险源
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findHazardById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        HazardVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条危险源信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteHazard(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询危险源列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody HazardPageVo hazardPageVo){
            RestPageImpl<HazardVo> pageVo = service.findPage(hazardPageVo);
            return ResultUtils.success(pageVo);
    }

    /**
     * 查询危险源列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody HazardPageVo hazardPageVo){
        List<HazardVo> listVo = service.findList(hazardPageVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据GIS数据格式要求获取危险源图层信息-不分页
     */
    @PostMapping(path = "/searchAllForGis")
    public ResultEntity searchAllHazardForGis(){
        List<Map<String,Object>> map = service.searchAllHazardForGis();
        return ResultUtils.success(map);
    }

    /**
     * 根据组织机构获取该机构下对应的所有危险源（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     */
    @PostMapping(path = "/searchHazardsForGisByOrg")
    public ResultEntity searchHazardsForGisByOrg(
            @Validated
            @NotNull(message = "hazardPageForGisVo不能为空")
            @RequestBody PageForGisVo hazardPageForGisVo){

        RestPageImpl<Map<String,Object>> pageVo = service.findPage(hazardPageForGisVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的危险源（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    @GetMapping(path = "/searchAllHazardsForGisByOrg")
    public ResultEntity searchAllHazardsForGisByOrg(
            @Validated
            @NotEmpty(message = "orgCode不能为空")
            @RequestParam(name = "orgCode") String orgCode){
        List<Map<String,Object>> map = service.searchAllHazardsForGisByOrg(orgCode);
        return ResultUtils.success(map);
    }

    /**
     * 根据查询条件导出危险源信息Excel
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody HazardPageVo hazardPageVo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("重大危险源.xlsx", "utf-8"));
        OutputStream os = response.getOutputStream();
        service.exportToExcel(os,hazardPageVo);
        return ResultUtils.success();
    }

}
