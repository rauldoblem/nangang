package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.material.MaterialPageVo;
import com.taiji.emp.res.service.MaterialService;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.res.vo.PageForGisVo;
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
@RequestMapping("/materials")
public class MaterialController extends BaseController {

    @Autowired
    MaterialService service;

    /**
     * 新增物资
     */
    @PostMapping
    public ResultEntity addMaterial(
            @Validated
            @NotNull(message = "MaterialVo不能为null")
            @RequestBody MaterialVo materialVo, Principal principal){
        service.create(materialVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改物资
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateMaterial(
            @NotNull(message = "MaterialVo不能为null")
            @RequestBody MaterialVo materialVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(materialVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条物资信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findMaterialById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        MaterialVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条物资信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteMaterial(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询应急物资列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findMaterials(@RequestBody MaterialPageVo materialPageVo){
        RestPageImpl<MaterialVo> pageVo = service.findPage(materialPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询应急物资列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findMaterialsAll(@RequestBody MaterialListVo materialListVo){
        List<MaterialVo> listVo = service.findList(materialListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据GIS数据格式要求获取应急物资图层信息-不分页
     */
    @PostMapping(path = "/searchAllForGis")
    public ResultEntity searchAllMaterialForGis(){
        List<Map<String,Object>> map = service.searchAllMaterialForGis();
        return ResultUtils.success(map);
    }

    /**
     * 根据组织机构获取该机构下对应的所有应急物资（去除没有经纬度的和经纬度格式不规范的）
     * 分页。该组织机构码为浙能特定编码
     */
    @PostMapping(path = "/searchMaterialForGisByOrg")
    public ResultEntity searchMaterialForGisByOrg(
            @Validated
            @NotNull(message = "materialPageForGisVo不能为空")
            @RequestBody PageForGisVo materialPageForGisVo){

        RestPageImpl<Map<String,Object>> pageVo = service.findPage(materialPageForGisVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 浙能需求
     * 根据组织机构获取该机构下对应的所有应急物资（去除没有经纬度的和经纬度格式不规范的）
     * 不分页。该组织机构码为浙能特定编码
     */
    @GetMapping(path = "/searchAllMaterialForGisByOrg")
    public ResultEntity searchAllMaterialForGisByOrg(
            @Validated
            @NotEmpty(message = "orgCode不能为空")
            @RequestParam(name = "orgCode") String orgCode){
        List<Map<String,Object>> map = service.searchAllMaterialForGisByOrg(orgCode);
        return ResultUtils.success(map);
    }

    /**
     * 根据查询条件导出应急物资信息Excel
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody MaterialListVo materialListVo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("应急物资.xlsx", "utf-8"));
        OutputStream os = response.getOutputStream();
        service.exportToExcel(os,materialListVo);
        return ResultUtils.success();
    }
}
