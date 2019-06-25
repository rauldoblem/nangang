package com.taiji.emp.event.eva.controller;

import com.taiji.emp.event.eva.service.EventEvaProcessService;
import com.taiji.emp.event.eva.vo.EventEvaProcessSaveVo;
import com.taiji.emp.event.eva.vo.EventEvaProcessNodeVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/process")
public class EventEvaProcessController extends BaseController {

    @Autowired
    private EventEvaProcessService service;
    /**
     * 新增过程再现
     */
    @PostMapping
    public ResultEntity createProcess(
            @Validated
            @NotNull(message = "ProcessVo不能为null")
            @RequestBody EventEvaProcessSaveVo processVo, Principal principal){
        service.create(processVo,principal);
        return ResultUtils.success();
    }


    /**
     * 修改过程再现
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateProcess(
            @NotEmpty(message = "id不能为空")
            @NotNull(message = "ProcessVo不能为null")
            @PathVariable(name = "id") String id,
            @RequestBody EventEvaProcessSaveVo processVo, Principal principal){
        service.update(processVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条过程再现信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findProcessById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        EventEvaProcessSaveVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条过程再现信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteProcess(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }


    /**
     * 查询过程再现列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(
            @NotEmpty(message = "id不能为空")
            @RequestParam(name = "eventId") String eventId){
        List<EventEvaProcessNodeVo> root = service.findNodeListVo(eventId);
        return ResultUtils.success(root);
    }

    @GetMapping(path = "/searchNodesAll")
    public ResultEntity searchNodesAll(){
        List<EventEvaProcessNodeVo> root = service.searchNodesAll();
        return ResultUtils.success(root);
    }

}
