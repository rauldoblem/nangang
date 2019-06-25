package com.taiji.emp.zn.controller;

import com.taiji.emp.base.searchVo.PlantLayoutSearchVo;
import com.taiji.emp.base.vo.PlantLayoutVo;
import com.taiji.emp.zn.service.PlantLayoutService;
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

/**
 * 厂区平面图管理
 * @author quzp
 * @date 2019-01-17 18:31:01
 */
@Slf4j
@RestController
@RequestMapping("/plantLayouts")
public class PlantLayoutController {

    @Autowired
    PlantLayoutService service;

    /**
     * 新增厂区平面图
     * @param plantLayoutVo
     * @return
     */
    @PostMapping
    public ResultEntity createPlantLayout(
            @Validated
            @NotNull(message = "plantLayoutVo不能为null")
            @RequestBody PlantLayoutVo plantLayoutVo, Principal principal){
        service.create(plantLayoutVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条厂区平面图信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlantLayout(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改厂区平面图信息
     * @param plantLayoutVo
     * @param principal
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updatePlantLayout(
            @NotNull(message = "noticeVo不能为null")
            @RequestBody PlantLayoutVo plantLayoutVo
            , Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        plantLayoutVo.setId(id);
        service.update(plantLayoutVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取某条厂区平面图信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlantLayoutById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        PlantLayoutVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 查询厂区平面图信息列表------分页
     * @param plantLayoutVo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PlantLayoutSearchVo plantLayoutVo){
        //验证分页参数
        RestPageImpl<PlantLayoutVo> pageVo = service.findPage(plantLayoutVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询厂区平面图信息列表------不分页
     * @param plantLayoutVo
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlantLayoutSearchVo plantLayoutVo){
        List<PlantLayoutVo> listVo = service.findList(plantLayoutVo);
        return ResultUtils.success(listVo);
    }
}
