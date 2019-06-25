package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
import com.taiji.emp.res.service.KnowService;
import com.taiji.emp.res.vo.KnowSaveVo;
import com.taiji.emp.res.vo.KnowledgeVo;
import com.taiji.emp.res.controller.BaseController;
import com.taiji.emp.res.service.KnowService;
import com.taiji.emp.res.vo.KnowSaveVo;
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
@RequestMapping("/knowledges")
public class KnowController extends BaseController {

    @Autowired
    KnowService service;

    /**
     * 新增知识
     */
    @PostMapping
    public ResultEntity createKnow(
            @Validated
            @NotNull(message = "KnowledgeVo不能为null")
            @RequestBody KnowSaveVo knowSaveVo, Principal principal){
        service.create(knowSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改知识
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateKnow(
            @NotNull(message = "KnowledgeVo不能为null")
            @RequestBody KnowSaveVo knowSaveVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(knowSaveVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条知识信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findKnowById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        KnowledgeVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条知识信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteKnow(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询应急知识列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody KnowPageVo knowPageVo){
        RestPageImpl<KnowledgeVo> pageVo = service.findPage(knowPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询应急知识列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody KnowListVo knowListVo){
        List<KnowledgeVo> listVo = service.findList(knowListVo);
        return ResultUtils.success(listVo);
    }
}
