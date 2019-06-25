package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.service.SchedulingService;
import com.taiji.emp.duty.vo.SchedulingCountVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dutystatistics")
public class DutyStatisticController extends BaseController {

    @Autowired
    SchedulingService schedulingService;

    /**
     * 值班统计，对某段时间内的人员值班情况进行统计
     * @param vo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findList(@RequestBody SchedulingVo vo){
        List<SchedulingCountVo> voList =schedulingService.findListCondition(vo);
        return ResultUtils.success(voList);
    }


    /**
     * 根据查询条件导出值班统计Excel
     * @param vo
     * @return
     */
    @PostMapping(path = "/exportToExcel")
    public ResultEntity exportToExcel(@RequestBody SchedulingVo vo, HttpServletResponse response)
            throws IOException,IndexOutOfBoundsException{
        List<SchedulingCountVo> voList =schedulingService.findListCondition(vo);
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("值班统计.xls", "utf-8"));
        OutputStream os = response.getOutputStream();
        schedulingService.exportToExcel(os,voList,vo);
        return ResultUtils.success();
    }

}
