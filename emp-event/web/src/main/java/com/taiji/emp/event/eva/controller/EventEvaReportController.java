package com.taiji.emp.event.eva.controller;

import com.taiji.emp.event.eva.service.EventEvaItemService;
import com.taiji.emp.event.eva.service.EventEvaReportService;
import com.taiji.emp.event.eva.vo.EventEvaItemVo;
import com.taiji.emp.event.eva.vo.EventEvaReportSaveVo;
import com.taiji.emp.event.eva.vo.EventEvaReportVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/eventEva")
public class EventEvaReportController extends BaseController {

    @Autowired
    EventEvaReportService service;

    @Autowired
    EventEvaItemService eventEvaItemService;

    /**
     * 新增事件评估报告
     * @param saveVo
     * @param principal
     * @return
     */
    @PostMapping
    public ResultEntity create(
            @Validated
            @NotNull(message = "saveVo不能为null")
            @RequestBody EventEvaReportSaveVo saveVo,
            Principal principal){
        service.create(saveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条 事件评估报告信息（含各评估细项分数）
     * @param eventId
     * @return
     */
    @GetMapping(path = "/evaReport")
    public ResultEntity findOne(@RequestParam(value = "eventId") String eventId){
        Assert.hasText(eventId,"eventId不能为空");
        EventEvaReportVo vo = service.findOne(eventId);
        return ResultUtils.success(vo);
    }

    /**
     * 修改事件评估报告信息（含各评估细项分数）
     * @param saveVo
     * @param principal
     * @return
     */
    @PutMapping(path = "/evaReport")
    public ResultEntity update(
            @NotNull(message = "saveVo不能为null")
            @RequestBody EventEvaReportSaveVo saveVo,Principal principal){
        service.update(saveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 新增事件评估项
     * @param vo
     * @return
     */
    @PostMapping(path = "/items")
    public ResultEntity createItem(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaItemVo vo,
            Principal principal){
        eventEvaItemService.create(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 删除事件评估项信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/items/{id}")
    public ResultEntity deleteLogic(
            @PathVariable(value = "id")
            @NotNull(message = "id不能为空") String id){
        eventEvaItemService.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改事件评估项信息
     * @param vo
     * @param id
     * @param principal
     * @return
     */
    @PutMapping(path = "/items/{id}")
    public ResultEntity updateItem(
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaItemVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id,
            Principal principal){
        vo.setId(id);
        eventEvaItemService.update(id,principal,vo);
        return ResultUtils.success();
    }

    /**
     * 获取单条事件评估项信息
     * @param id
     * @return
     */
    @GetMapping(path = "/items/{id}")
    public ResultEntity findEvaItem(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
        EventEvaItemVo vo = eventEvaItemService.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据事件类型获取事件评估项列表
     * @param eventTypeId
     * @return
     */
    @GetMapping(path = "/items")
    public ResultEntity findListByEventTypeId(
            @NotNull(message = "eventTypeId不能为空")String eventTypeId){
        List<EventEvaItemVo> listVo = eventEvaItemService.findListByEventTypeId(eventTypeId);
        return ResultUtils.success(listVo);
    }

}
