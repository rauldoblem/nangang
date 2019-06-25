package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.searchVo.DailyLogSearchVo;
import com.taiji.emp.duty.service.DailyLogService;
import com.taiji.emp.duty.service.DailyLogTreatmentService;
import com.taiji.emp.duty.vo.dailylog.DailyLogTYVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
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

@Slf4j
@RestController
@RequestMapping("/dailylogs")
public class DailyLogController extends BaseController {

    @Autowired
    private DailyLogService service;

    @Autowired
    private DailyLogTreatmentService dailyLogTreatmentService;

    /**
     * 新增值班日志
     * @param dailyLogVo
     * @return
     */
    @PostMapping
    public ResultEntity createDailyLog(
            @Validated
            @NotNull(message = "dailyLogVo不能为null")
            @RequestBody DailyLogVo dailyLogVo,
            Principal principal){
        service.create(dailyLogVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条值班日志信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteDailyLog(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改值班日志信息
     * @param dailyLogVo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updateDailyLog(
            @NotNull(message = "dailyLogVo不能为null")
            @RequestBody DailyLogVo dailyLogVo,
            Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        dailyLogVo.setId(id);
        service.update(dailyLogVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id查询值班日志信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
       DailyLogVo vo = service.findOne(id);
       return ResultUtils.success(vo);
    }

    /**
     *  根据条件查询值班日志列表
     * @param dailyLogSearchVo
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody DailyLogSearchVo dailyLogSearchVo){
        List<DailyLogVo> listVo = service.findList(dailyLogSearchVo);
        return ResultUtils.success(listVo);
    }

    /**
     *  根据条件查询今天和昨天的值班日志列表-不分页
     * @param dailyLogSearchVo
     * @return
     */
    @PostMapping(path = "/todayAndYesterday")
    public ResultEntity findTodayAndYesterdayList(@RequestBody DailyLogSearchVo dailyLogSearchVo){
        DailyLogTYVo listVo = service.findTodayAndYesterdayList(dailyLogSearchVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据条件查询值班日志列表——分页
     * @param dailyLogSearchVo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody DailyLogSearchVo dailyLogSearchVo){
        RestPageImpl<DailyLogVo> pageVo = service.findPage(dailyLogSearchVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 记录值班日志办理情况,更新值班日志为办理状态
     * @param dailyLogTreatmentVo
     * @return
     */
    @PostMapping(path = "/dlogTreat")
    public ResultEntity doLogTreat(
            @Validated
            @NotNull(message = "dailyLogTreatmentVo不能为空")
            @RequestBody DailyLogTreatmentVo dailyLogTreatmentVo){
        String dLogId = dailyLogTreatmentVo.getDLogId();
        dailyLogTreatmentService.create(dailyLogTreatmentVo);
        DailyLogVo entityVo = service.findOne(dLogId);
        service.updateTreatStatus(entityVo,dailyLogTreatmentVo);
        return ResultUtils.success();
    }

    /**
     * 根据值班日志ID获取办理状态列表
     * @return
     */
    @GetMapping(path = "/dlogTreats/{dlogId}")
    public ResultEntity findByDlogId(
            @NotNull(message = "dLogId不能为空")
            @PathVariable(value = "dlogId")String dlogId){
        List<DailyLogTreatmentVo> listVo = dailyLogTreatmentService.findByDlogId(dlogId);
        return ResultUtils.success(listVo);
    }

}
