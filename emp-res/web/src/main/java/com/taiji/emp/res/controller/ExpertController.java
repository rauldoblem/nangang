package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.service.ExpertService;
import com.taiji.emp.res.vo.ExpertVo;
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

@Slf4j
@RestController
@RequestMapping("/experts")
public class ExpertController extends BaseController {

    @Autowired
    ExpertService service;

    /**
     * 新增专家
     */
    @PostMapping
    public ResultEntity addExpert(
            @Validated
            @NotNull(message = "ExpertVo不能为null")
            @RequestBody ExpertVo expertVo, Principal principal){
        service.create(expertVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改专家
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateExpert(
            @NotNull(message = "ExpertVo不能为null")
            @RequestBody ExpertVo expertVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(expertVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条专家信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findExpertById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        ExpertVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条专家信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteExpert(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询应急专家列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findExperts(@RequestBody ExpertPageVo expertPageVo){
        RestPageImpl<ExpertVo> pageVo = service.findPage(expertPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询应急专家列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findExpertsAll(@RequestBody ExpertListVo expertListVo){
        List<ExpertVo> listVo = service.findList(expertListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据查询条件导出专家信息Excel
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody ExpertListVo expertListVo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("应急专家.xlsx", "utf-8"));
        OutputStream os = response.getOutputStream();
        service.exportToExcel(os,expertListVo);
        return ResultUtils.success();
    }
}
