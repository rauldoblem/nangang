package com.taiji.emp.res.controller;


import com.taiji.emp.res.searchVo.law.LawListVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
import com.taiji.emp.res.service.LawService;
import com.taiji.emp.res.vo.LawSaveVo;
import com.taiji.emp.res.vo.LawVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
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
@RequestMapping("/laws")
public class LawController extends BaseController {
    @Autowired
    LawService service;

    /**
     * 新增法律法规
     */
    @PostMapping
    public ResultEntity creatLaw(
            @Validated
            @NotNull(message = "LawVo不能为null")
            @RequestBody LawSaveVo lawSaveVo, Principal principal) {
        service.create(lawSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改法律法规
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateLaw(
            @NotNull(message = "LawVo不能为null")
            @RequestBody LawSaveVo lawSaveVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(lawSaveVo,id,principal);
        return ResultUtils.success();
    }

    /**
     *根据id获取单条法律法规
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findLawById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id
    ){
        LawVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }


    /**
     * 根据id删除单条法律法规
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteLaw(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id) {
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询法律法规列表 --分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody LawPageVo lawPageVo){
            RestPageImpl<LawVo> pageVo = service.findPage(lawPageVo);
            return ResultUtils.success(pageVo);
    }

    /**
     * 查询法律法规列表 --不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody LawListVo lawListVo){
        List<LawVo> listVo = service.findList(lawListVo);
        return ResultUtils.success(listVo);
    }
}
