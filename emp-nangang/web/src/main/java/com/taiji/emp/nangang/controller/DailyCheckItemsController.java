package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.DailyCheckItemsService;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/dailyChecks")
public class DailyCheckItemsController extends BaseController{
    @Autowired
    private DailyCheckItemsService service;

    /**
     * 更新值班检查项
     */
    @PutMapping("/checkItem")
    public ResultEntity updateCheckItem(
            @Validated
            @NotNull(message = "DailyCheckItemsVo不能为null")
            @RequestBody DailyCheckItemsVo dailyCheckItemsVo, Principal principal) {

        service.update(dailyCheckItemsVo,principal);
        return ResultUtils.success();
    }
    /**
     * 根据值班检查表ID获取检查项
     */
    @GetMapping("/getCheckItems/{id}")
    public ResultEntity getCheckItem(
            @Validated
            @NotNull(message = "id不能为null")
            @PathVariable(value = "id")String id) {

        List<DailyCheckItemsVo> vo = service.findCheckId(id);
        return ResultUtils.success(vo);
    }
}
