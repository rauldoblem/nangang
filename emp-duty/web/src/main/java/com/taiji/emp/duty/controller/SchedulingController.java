package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.searchVo.*;
import com.taiji.emp.duty.service.SchedulingService;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/schedulings")
public class SchedulingController extends BaseController {

    @Autowired
    private SchedulingService service;


    /**
     * 获取单个班次的值班人员列表 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @PostMapping(path = "/findSchedulingsById")
    public ResultEntity findList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo){
        List<SchedulingVo> listVo = service.findList(vo);
        return ResultUtils.success(listVo);
    }

    /**
     * 保存单个班次的值班人员列表 SchedulingListVo不能为空
     * @param vo
     * @return
     */
    @PostMapping(path = "/saveSchedulings")
    public ResultEntity createSchedulings(
            @NotNull(message = "SchedulingListVo")
            @RequestBody SchedulingListVo vo,OAuth2Authentication principal){
        service.createSchedulings(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 保存单个班次的值班人员的换班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @PostMapping(path = "/exchangeScheduling")
    public ResultEntity createExchangeSchedulings(
            @NotNull(message = "SchedulingVo")
            @RequestBody SchedulingVo vo){
        service.createExchangeSchedulings(vo);
        return ResultUtils.success();
    }

    /**
     * 获取值排班数据日历形式 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @PostMapping(path = "/getCalSchedulings")
    public ResultEntity findCalSchedulings(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo,
            OAuth2Authentication principal){
        MonthSchedulingVo listVo = service.findCalSchedulings(vo,principal);
        return ResultUtils.success(listVo);
    }

    /**
     * 获取值排班数据列表形式 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @PostMapping(path = "/getListSchedulings")
    public ResultEntity findListSchedulings(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo,
            OAuth2Authentication principal){
        List<SchedulingsListVo> listVo = service.findListSchedulings(vo,principal);
        return ResultUtils.success(listVo);
    }

    /**
     * 获取自动值排班数据 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @PostMapping(path = "/getAutoSchedulings")
    public ResultEntity findAutoSchedulings(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo,
            Principal principal){
        service.findAutoSchedulings(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据当前系统时间获取对应班次的值班人员列表，
     * 包括领导和值班员等，供南港的值班工作桌面调用
     * @param shiftFlag
     * @return
     */
    @GetMapping(path = "/currentPatterns")
    public ResultEntity findCurrentPatterns(
            @RequestParam(value = "shiftFlag")
            @NotEmpty(message = "shiftFlag不能为空")String shiftFlag,
            Principal principal){
        List<SchedulingVo> listVo = null;
        List<SchedulingVo> listVo2 = null;
        if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_CURRENT)){
            //根据这种模式和当前时间 查询 值班日期、值班分组ID、值班分组名称、班次名称、值班人员ID、值班人员姓名
//            listVo = service.findShiftListByMultiCondition(principal);
//            listVo2 = service.findDayListByMultiCondition(principal);

            listVo = service.findCurrentDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_PREVIOUS)){
//            listVo = service.findPerShiftListByMultiCondition(principal);
//            listVo2 = service.findPerDayListByMultiCondition(principal);

            listVo = service.findPrevDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_NEXT)){
//            listVo = service.findNextShiftListByMultiCondition(principal);
//            listVo2 = service.findNextDayListByMultiCondition(principal);

            listVo = service.findNextDutyPersons(principal,shiftFlag);
        }
//        if(null != listVo && listVo.size() >0){
//            if(null != listVo2 && listVo2.size() > 0) {
//                listVo.addAll(listVo2);
//            }
//        }else{
//            if(null != listVo2 && listVo2.size() > 0) {
//                listVo = listVo2;
//            }
//        }
        if (!CollectionUtils.isEmpty(listVo)) {
            List<SchedulingVo> resultList = listVo.stream().filter((temp) -> temp.getPersonInfo() != null).collect(Collectors.toList());
            return ResultUtils.success(resultList);
        }else {
            return ResultUtils.success(listVo);
        }
    }

    /**
     * 根据时间获取对应的值班人员列表
     * @param searchDate
     * @return
     */
    @GetMapping(path = "/getDutysByDate")
    public ResultEntity findDutysByDate(
            @RequestParam(value = "searchDate")
            @NotEmpty(message = "searchDate不能为空")String searchDate,
            OAuth2Authentication principal){
        SchedulingSearchVo listVo = service.findDutysByDate(searchDate,principal);
        return ResultUtils.success(listVo);
    }

    /**
     * 获取当天参与交接班的值班人员列表
     */
    @GetMapping(path = "/currentDutys")
    public ResultEntity findCurrentDutys(
            @RequestParam(value = "shiftFlag")
            @NotEmpty(message = "shiftFlag不能为空")String shiftFlag,
            OAuth2Authentication principal){
        //List<SchedulingVo> listVo = service.findCurrentDutys(principal);
        List<SchedulingVo> listVo = null;
        if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_CURRENT)){
            listVo = service.findCurrentDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_PREVIOUS)){
            listVo = service.findPrevDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_NEXT)){
            listVo = service.findNextDutyPersons(principal,shiftFlag);
        }
        //处理数据
        List<SchedulingVo> collect = listVo.stream().filter(schedulingVo -> !SchedulingGlobal.DUTY_TEAM_NAME.equals(schedulingVo.getDutyTeamName())).collect(Collectors.toList());
        for (SchedulingVo sd:collect) {
            List<PersonVo> personInfo = sd.getPersonInfo();
            if (!CollectionUtils.isEmpty(personInfo)){
                PersonVo personVo = personInfo.get(0);
                sd.setPersonId(personVo.getId());
                sd.setPersonName(personVo.getAddrName());
            }
        }
        return ResultUtils.success(collect);
    }

    /**
     * 获取当天与下一天参与交接班的值班人员列表
     */
    @GetMapping(path = "/nextDutys")
    public ResultEntity findNextDutys(
            @RequestParam(value = "shiftFlag")
            @NotEmpty(message = "shiftFlag不能为空")String shiftFlag,
            OAuth2Authentication principal){
        //List<SchedulingVo> listVo = service.findNextDutys(principal);
        List<SchedulingVo> listVo = null;
        if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_CURRENT)){
            listVo = service.findCurrentDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_PREVIOUS)){
            listVo = service.findPrevDutyPersons(principal,shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_NEXT)){
            listVo = service.findNextDutyPersons(principal,shiftFlag);
        }
        //处理数据
        List<SchedulingVo> collect = listVo.stream().filter(schedulingVo -> !SchedulingGlobal.DUTY_TEAM_NAME.equals(schedulingVo.getDutyTeamName())).collect(Collectors.toList());
        for (SchedulingVo sd:collect) {
            List<PersonVo> personInfo = sd.getPersonInfo();
            if (!CollectionUtils.isEmpty(personInfo)){
                PersonVo personVo = personInfo.get(0);
                sd.setPersonId(personVo.getId());
                sd.setPersonName(personVo.getAddrName());
            }
        }
        return ResultUtils.success(collect);
    }

    /**
     * 根据日期获取当天的集团及各板块的值班人员列表，包括领导和值班员等，供浙能首页使用
     * @param vo
     * @return
     */
    @PostMapping(path = "/getAllDutysByDate")
    public ResultEntity getAllDutysByDate(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SearchAllDutyVo vo){
        List<CalSchedulingForOrg> listVo = service.getAllDutysByDate(vo);
        return ResultUtils.success(listVo);
    }

    /**
     * 根据查询条件导出值班列表Excel
     * @param vo
     * @return
     */
    @PostMapping(path = "/listSchedulingsToExcel")
    public ResultEntity getListSchedulingsToExcel(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo, HttpServletResponse response, HttpServletRequest request,
            OAuth2Authentication principal)
            throws Exception{
        OutputStream os = response.getOutputStream();
        service.exportToExcelForSchedulings(os,vo,principal);
        return ResultUtils.success();
    }

    /**
     * 获取当前班次人员的签入状态
     */
    @GetMapping(path = "/getSigninStatus")
    public ResultEntity getSigninStatus(
            @RequestParam(value = "shiftFlag")
            @NotEmpty(message = "shiftFlag不能为空")String shiftFlag,
            Principal principal){
        String flag = service.getSigninStatus(shiftFlag,principal);
        return ResultUtils.success(flag);
    }
}
