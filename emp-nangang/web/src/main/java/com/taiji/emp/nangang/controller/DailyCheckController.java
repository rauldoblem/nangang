package com.taiji.emp.nangang.controller;

import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.emp.nangang.searchVo.dailyCheck.DailyCheckPageVo;
import com.taiji.emp.nangang.service.DailyCheckService;
import com.taiji.emp.nangang.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
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
public class DailyCheckController extends BaseController {

    @Autowired
    private DailyCheckService service;

    /**
     * 更新值班检查表/交接班（修改isShift状态）
     */
    @PutMapping("/{id}")
    public ResultEntity updateCheckItem(
            @Validated
            @NotNull(message = "id不能为null")
            @PathVariable(value = "id")String id, Principal principal) {

        service.updateDailyCheck(id,principal);
        return ResultUtils.success();
    }
    /**
     * 后台去查shiftPatternId，根据时间和shiftPatternId获取dailyCheck表中唯一一条数据的dailyCheckId,
     * 该条数据若存在，根据dailyCheckId去查询该Id关联的items列表（在dailyCheck_Items表） 返回展示这个items列表
     * 该条数据若不存在，则根据时间和shiftPatternId保存一条dailyCheck记录，返回dailycheckId
     * 去数据字典表中查询值班项的list，保存到dailyCheck_Items表中并关联上dailycheckId 返回展示这个items列表
     */
    @PostMapping(path = "/getCheckItem")
    public ResultEntity getCheckItem(
            @Validated
            @NotNull(message = "DailyCheckPageVo不能为null")
            @RequestBody ShiftDateVo shiftDateVo, Principal principal) {

        DailyCheckItemsListVo itemsVo = service.getCheckItem(shiftDateVo,principal);
        return ResultUtils.success(itemsVo);
    }

    /**
     * 根据条件查询工作检查管理列表-分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(
            @Validated
            @NotNull(message = "DailyCheckPageVo不能为null")
            @RequestBody DailyCheckPageVo dailyCheckPageVo){
        RestPageImpl<DailyCheckVo> pageVo = service.findPage(dailyCheckPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 新增值班日志（关联检查项id）
     */
    @PostMapping(path = "/addDailyLog")
    public ResultEntity addDailyLog(
            @Validated
            @NotNull(message = "dailyCheckDailyLogVo不能为null")
            @RequestBody DailyCheckDailyLogVo dailyCheckDailyLogVo , Principal principal){
        service.addDailyLog(dailyCheckDailyLogVo,principal);
        return ResultUtils.success();
    }

}
