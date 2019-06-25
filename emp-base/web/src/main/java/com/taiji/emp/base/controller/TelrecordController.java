package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.telrecord.TelrecordPageVo;
import com.taiji.emp.base.service.TelrecordService;
import com.taiji.emp.base.vo.TelrecordVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/telrecords")
public class TelrecordController extends BaseController{
    @Autowired
    TelrecordService telrecordService;

    /**
     * 获取电话录音列表——分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findTelrecords(@RequestBody TelrecordPageVo telrecordPageVo) {
        RestPageImpl<TelrecordVo> pageVo = telrecordService.findPage(telrecordPageVo);
        return ResultUtils.success(pageVo);
    }
}
