package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.sms.SmsListVo;
import com.taiji.emp.base.searchVo.sms.SmsPageVo;
import com.taiji.emp.base.service.SmsService;
import com.taiji.emp.base.vo.SmsRecieveVo;
import com.taiji.emp.base.vo.SmsVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import com.thoughtworks.xstream.io.path.Path;
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
@RequestMapping("/smses")
public class SmsController extends BaseController{
    @Autowired
    SmsService smsService;

    // 新增
    @PostMapping
    public ResultEntity addSms(
            @Validated
            @NotNull(message = "SmsVo不能为null")
            @RequestBody SmsVo smsVo,Principal principal){
        smsService.create(smsVo,principal);
        return ResultUtils.success();
    }

    //修改
    @PutMapping(path = "{id}")
    public ResultEntity updateSms(
            @NotNull(message = "SmsVo不能为null")
            @RequestBody SmsVo smsVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        smsService.update(smsVo,id,principal);
        return ResultUtils.success();
    }

    //获取单条
    @GetMapping(path = "{id}")
    public ResultEntity findSmsById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        SmsVo vo = smsService.findOne(id);
        return ResultUtils.success(vo);
    }

    //删除
    @DeleteMapping(path = "{id}")
    public ResultEntity deleteSms(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        smsService.deleteLogic(id);
        return ResultUtils.success();
    }

    //分页
    @PostMapping(path = "/search")
    public ResultEntity findSmss(@RequestBody SmsPageVo smsPageVo){
        RestPageImpl<SmsVo> pageVo = smsService.findPage(smsPageVo);
        return ResultUtils.success(pageVo);
    }

    //不分页
    @PostMapping(path = "/searchAll")
    public ResultEntity findSmsAll(@RequestBody SmsListVo smsListVo){
        List<SmsVo> listVo = smsService.findList(smsListVo);
        return ResultUtils.success(listVo);
    }

    //查看短信发送状态
    @GetMapping(path = "/sendStatus/{smsId}")
    public ResultEntity findSmsRecieveBySmsId(
            @NotEmpty(message = "smsId不能为空")
            @PathVariable(name = "smsId")String smsId){
        List<SmsRecieveVo> vo = smsService.findSmsRecieveBySmsId(smsId);
        return ResultUtils.success(vo);
    }


    //短信发送，后台需要根据各个发送人的发送接收情况依次写短信接收表
    @PostMapping(path = "/send")
    public ResultEntity sendMsg(@RequestBody SmsListVo smsListVo){
        //List<SmsVo> listVo = smsService.findList(smsListVo);
        return ResultUtils.success();
    }

}