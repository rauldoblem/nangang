package com.taiji.emp.alarm.controller;

import com.taiji.emp.alarm.common.constant.AlarmGlobal;
import com.taiji.emp.alarm.searchVo.AlertNoticePageSearchVo;
import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.emp.alarm.service.AlertService;
import com.taiji.emp.alarm.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alerts")
public class AlertController {

    AlertService service;

    /**
     * 新增预警信息
     */
    @PostMapping
    public ResultEntity addAlert(
            @Validated
            @NotNull(message = "AlertVo 不能为null")
            @RequestBody AlertVo alertVo,OAuth2Authentication principal){
        service.addAlert(alertVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预警信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateAlert(
            @NotNull(message = "AlertVo不能为null")
            @RequestBody AlertVo alertVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            OAuth2Authentication principal){
        service.updateAlert(alertVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预警信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findAlertById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        AlertVo resultVo = service.findAlertById(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 根据id逻辑删除单条预警信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteAlert(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteAlert(id);
        return ResultUtils.success();
    }

    /**
     * 查询预警信息列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findAlerts(
            @NotNull(message = "alertPageVo 不能为null")
            @RequestBody AlertPageSearchVo alertPageVo){
        RestPageImpl<AlertVo> pageVo = service.findAlerts(alertPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 预警通知追加
     */
    @GetMapping(path = "/alertNotice/{alertId}")
    public ResultEntity getNoticeByAlertId(
            @NotEmpty(message = "alertId不能为空")
            @PathVariable(name = "alertId") String alertId){
        AlertNoticeDTOVo resultVo = service.getNoticeByAlertId(alertId);
        return ResultUtils.success(resultVo);
    }

    /**
     * 预警忽略/办结或通知
     * {
         "orgIds": [
         "string"
         ],
         "alertId": "string",
         "require": "string",
         "alertStatus": "string"      --  忽略：1,通知：2,办结:3
        }
     */
    /**
     * 原来的/send 改成现在的 /deal 然后微调
     * 入参：
     *  {
             "alertId": "string",
             "alertStatus": "string"
        }
     */
    @PostMapping(path = "/deal")
    public ResultEntity dealAlert(
            @RequestBody Map<String,Object> map,
            OAuth2Authentication principal){
        if(map.containsKey("alertId")&&map.containsKey("alertStatus")){ //预警id和状态是必须的
            String alertStatus = map.get("alertStatus").toString();//判别是忽略还是通知操作
            if(!StringUtils.isEmpty(alertStatus)){
                switch (alertStatus){
                    case AlarmGlobal.ALAERT_IGNORED:
                        //忽略操作
                        service.alertIgnore(map,principal);
                        return ResultUtils.success();
                    case AlarmGlobal.ALAERT_FINISHED:
                        //办结操作
                        service.alertIgnore(map,principal);
                        return ResultUtils.success();
                    case AlarmGlobal.ALAERT_NOTICED:
                        //通知操作 -> 2019-1-4 改为处置中
                        //service.alertNotice(map,principal);
                        service.alertIgnore(map,principal);
                        return ResultUtils.success();
                    default:
                        return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"alertStatus--预警状态字段传输异常");
                }
            }else{
                return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"alertStatus--预警状态字段为空"); //参数异常
            }
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR); //参数异常
        }
    }

    /**
     * 预警通知反馈查看
     * @param alertId 预警id
     */
    @GetMapping(path = "/viewAlertFeedbacks/{alertId}")
    public ResultEntity viewFeedbackList(
            @NotEmpty(message = "alertId不能为空")
            @PathVariable(name = "alertId") String alertId){
        NoticeFbTotalVo noticeFbTotalVo = service.viewFeedbackList(alertId);
        return ResultUtils.success(noticeFbTotalVo);
    }

    /**
     * 根据条件查询本单位接收的预警通知列表-分页
     */
    @PostMapping(path = "/recSearch")
    public ResultEntity findRecAlerts(
           @NotNull(message = "alertNoticePageVo 不能为null")
           @RequestBody AlertNoticePageSearchVo alertNoticePageVo){
        RestPageImpl<AlertNoticeVo> result = service.findRecAlerts(alertNoticePageVo);
        return ResultUtils.success(result);
    }

    /**
     * 接收的预警通知查看
     * @param alertRecId 预警通知Id
     */
    @GetMapping(path = "/viewRecFeedbacks/{alertRecId}")
    public ResultEntity viewNoticeRec(
            @NotEmpty(message = "alertRecId不能为空")
            @PathVariable(name = "alertRecId") String alertRecId){
        AlertNoticeVo result = service.viewNoticeRec(alertRecId);
        return ResultUtils.success(result);
    }

    /**
     * 预警通知反馈，保存同时，修改接收表中的最新反馈状态、最近反馈内容、最近反馈时间
     */
    @PostMapping(path = "/feedback")
    public ResultEntity feedbackAlert(
            @Validated
            @NotNull(message = "noticeFbSaveVo 不能为null")
            @RequestBody NoticeFbSaveVo noticeFbSaveVo){
        String feedbackStatus = noticeFbSaveVo.getFeedbackStatus(); //页面填写反馈状态
        if(AlarmGlobal.ALAERT_FB_DOING.equals(feedbackStatus)
                ||AlarmGlobal.ALAERT_FB_COMPLETED.equals(feedbackStatus)){
            service.feedbackAlert(noticeFbSaveVo);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"反馈状态参数传输异常");
        }
    }

    /**
     * 获取该条预警信息下发的单位列表及各单位的反馈情况,供浙能首页使用
     */
    @GetMapping(path = "/getAllOrgsAndFeedback/{alarmId}")
    public ResultEntity getAllOrgsAndFeedback(
            @NotEmpty(message = "alarmId不能为空")
            @PathVariable(name = "alarmId") String alarmId){
        List<AlertNoticeVo> result = service.getAllOrgsAndFeedback(alarmId);
        return ResultUtils.success(result);
    }

    /**
     * 2019-1-4 修改：
     *  预警通知：1.后台将is_notice标志位改为0已通知
     *           2.关联已发送单位和预警信息id到中间表
     *  入参{
                 "orgIds": [
                 "string"
                 ],
                 "alertId": "string"
            }
     */
    @PostMapping(path = "/send")
    public ResultEntity sendAlert(
            @RequestBody Map<String,Object> map,
            OAuth2Authentication principal){
        if(map.containsKey("orgIds") && map.containsKey("alertId")){//需包含通知单位的ids和预警信息id
            service.alertNoticeNew(map,principal);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR , "参数异常");
        }
    }

    /**
     * 【20190104浙能新增】通过预警信息ID获取该预警信息已经通知的单位列表
     * @param alertId
     * @return
     */
    @GetMapping(path = "/getReceiverListByAlertId")
    public ResultEntity getReceiverListByAlertId(
            @NotEmpty(message = "alertId不能为空")
            @RequestParam("alertId") String alertId){
        List<AlertReceicerInfoVo> result = service.getReceiverListByAlertId(alertId);
        if (!CollectionUtils.isEmpty(result)){
            return ResultUtils.success(result);
        }else {
            return ResultUtils.fail(ResultCodeEnum.OPERATE_FAIL,"未查到该预警信息的接收单位信息");
        }
    }
}
