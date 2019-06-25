package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.searchVo.DailyShiftPageVo;
import com.taiji.emp.duty.service.DailyLogService;
import com.taiji.emp.duty.service.DailyShiftService;
import com.taiji.emp.duty.vo.DailyShiftAndLogVo;
import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import com.taiji.emp.duty.vo.dailyShift.DailyShiftVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dailyshifts")
public class DailyShiftController extends BaseController  {

    @Autowired
    DailyShiftService service;
    @Autowired
    DailyLogService dailyLogService;

    /**
     * 新增交接班 DailyShiftVo， 不能为空
     */
    @PostMapping
    public ResultEntity createDailyShift(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DailyShiftAndLogVo vo, Principal principal) {
        String status = service.create(vo, principal);
        return  ResultUtils.success(status);
    }

    /**
     * 根据id 获取交接班信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id) {
        DailyShiftVo result = service.findOne(id);
        List<DailyLogShiftVo>  dailyLogShiftVo = result.getDailyLogShift();
        //遍历查找值班日志
        for (DailyLogShiftVo vo:dailyLogShiftVo) {
            String dailyLogId = vo.getDailyLogId();
            DailyLogVo dailyLog = dailyLogService.findOne(dailyLogId);
            if (null != dailyLog){
                vo.setAffirtTypeId(dailyLog.getAffirtTypeId());
                vo.setAffirtTypeName(dailyLog.getAffirtTypeName());
                vo.setInputerName(dailyLog.getInputerName());
                vo.setLogContent(dailyLog.getLogContent());
                vo.setTreatStatus(dailyLog.getTreatStatus());
                vo.setTreatTime(dailyLog.getTreatTime());
            }
        }
        //拼装前台数据
        DailyShiftAndLogVo DailyShiftAndLogVo = new DailyShiftAndLogVo();
        DailyShiftAndLogVo.setDailyLogShift(dailyLogShiftVo);
        DailyShiftAndLogVo.setDailyShift(result);
        return ResultUtils.success(DailyShiftAndLogVo);
    }

    /**
     * 查询交接班列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody DailyShiftPageVo dailyShiftPageVo){
            RestPageImpl<DailyShiftVo> pageVo = service.findPage(dailyShiftPageVo);
            return ResultUtils.success(pageVo);
    }
}
