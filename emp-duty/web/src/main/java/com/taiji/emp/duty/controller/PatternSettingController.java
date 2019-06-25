package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.service.PatternSettingService;
import com.taiji.emp.duty.service.SchedulingService;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patternsettings")
public class PatternSettingController extends BaseController {

    @Autowired
    private PatternSettingService service;

    @Autowired
    private SchedulingService schedulingService;


    /**
     *  根据条件查询日历设置列表
     * @param id
     * @return
     */
    @GetMapping(path = "/searchPatterns")
    public ResultEntity findList(
            @NotEmpty(message = "id不能为空")
            @RequestParam(value = "orgId")String id,
            OAuth2Authentication principal){
        List<PatternSettingVo> listVo = service.findList(id,principal);
        return ResultUtils.success(listVo);
    }

    /**
     *  根据当前时间获取班次名称，供交接班使用
     * @return
     */
    @GetMapping(path = "/currentShiftName")
    public ResultEntity findCurrentShiftName(
            @RequestParam(value = "shiftFlag")
            @NotEmpty(message = "shiftFlag不能为空")String shiftFlag,
            Principal principal){
        List<SchedulingVo> listVo = null;
        if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_CURRENT)){
            listVo = schedulingService.findCurrentDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_PREVIOUS)){
            listVo = schedulingService.findPrevDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_NEXT)){
            listVo = schedulingService.findNextDutyPersons(principal,shiftFlag);
        }
        String shiftPatternName = listVo.get(1).getShiftPatternName();
        String dutyDate = listVo.get(1).getDutyDate();
        String shiftName = service.findCurrentShiftName(shiftPatternName,dutyDate,principal);
        return ResultUtils.success(shiftName);
    }

}
