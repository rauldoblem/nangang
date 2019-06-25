package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.repertory.RepertoryPageVo;
import com.taiji.emp.res.service.RepeService;
import com.taiji.emp.res.vo.PositionVo;
import com.taiji.emp.res.vo.RepertoryVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/repertorys")
public class RepeController extends BaseController {

    @Autowired
    RepeService service;

    /**
     * 新增储备库
     */
    @PostMapping
    public ResultEntity createRepe(
            @Validated
            @NotNull(message = "RepertoryVo不能为null")
            @RequestBody RepertoryVo repertoryVo, Principal principal){
        service.create(repertoryVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改储备库
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateRepe(
            @NotNull(message = "RepertoryVo不能为null")
            @RequestBody RepertoryVo repertoryVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(repertoryVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条储备库信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findRepeById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        RepertoryVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条储备库信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteRepe(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询应急储备库列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody RepertoryPageVo vo){
            RestPageImpl<RepertoryVo> pageVo = service.findPage(vo);
            return ResultUtils.success(pageVo);
    }

    /**
     * 查询应急储备库列表 --- 不分页
     */
    @PostMapping(path = "/searchPositions")
    public ResultEntity findList(@RequestBody PositionVo vo){
        List<PositionVo> listVo = service.findList(vo);
        return ResultUtils.success(listVo);
    }

    /**
     *根据条件查询物资库列表-不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList1(@RequestBody RepertoryPageVo vo){
        List<RepertoryVo> listVo = service.findRepertoryList(vo);
        return ResultUtils.success(listVo);
    }


    /**
     * 根据GIS数据格式要求获取应急物资图层信息-不分页
     */
    @PostMapping(path = "/searchAllForGis")
    public ResultEntity findAllForGis(){
        List<Map<String,Object>> map = service.searchAllMaterialForGis();
        return ResultUtils.success(map);
    }










}
