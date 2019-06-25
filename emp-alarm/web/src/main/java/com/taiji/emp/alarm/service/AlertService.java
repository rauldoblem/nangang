package com.taiji.emp.alarm.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.emp.alarm.common.constant.AlarmGlobal;
import com.taiji.emp.alarm.feign.AlertClient;
import com.taiji.emp.alarm.feign.AlertNoticeClient;
import com.taiji.emp.alarm.feign.DocAttClient;
import com.taiji.emp.alarm.msgService.InfoMsgService;
import com.taiji.emp.alarm.searchVo.AlertNoticePageSearchVo;
import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.emp.alarm.vo.*;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertService extends BaseService{

    @Autowired
    AlertClient alertClient;
    @Autowired
    AlertNoticeClient noticeClient;
    @Autowired
    private DocAttClient docAttClient;
    @Autowired
    private InfoMsgService infoMsgService;

    //新增预警信息
    public void addAlert(AlertVo alertVo, OAuth2Authentication principal){

        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);

        String eventTypeId = alertVo.getEventTypeId();
        if(!StringUtils.isEmpty(eventTypeId)){  //预警类型(同事件类型)
            alertVo.setEventTypeName(getItemNameById(eventTypeId));
        }
        String severityId = alertVo.getSeverityId(); //严重程度（预警级别）
        if(!StringUtils.isEmpty(severityId)){
            alertVo.setSeverityName(getItemNameById(severityId));
        }

        alertVo.setAlertStatus(AlarmGlobal.ALAERT_NOT_HANDLE); //未处理
        alertVo.setCreateOrgId(userMap.get("orgId").toString()); //创建单位id
        alertVo.setCreateOrgName(userMap.get("orgName").toString()); //创建单位名称

        alertVo.setCreateBy(userMap.get("username").toString());//account
        alertVo.setUpdateBy(userMap.get("username").toString());//account

        alertVo.setIsNotice(AlarmGlobal.Alert_IS_NOTICE_NO);//设置初始为1 未通知

        ResponseEntity<AlertVo> result = alertClient.create(alertVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);

    }

    //修改预警信息
    public void updateAlert(AlertVo alertVo,String id,OAuth2Authentication principal){
        Assert.hasText(id,"id不能为空");
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String eventTypeId = alertVo.getEventTypeId();
        if(!StringUtils.isEmpty(eventTypeId)){  //预警类型(同事件类型)
            alertVo.setEventTypeName(getItemNameById(eventTypeId));
        }
        String severityId = alertVo.getSeverityId(); //严重程度（预警级别）
        if(!StringUtils.isEmpty(severityId)){
            alertVo.setSeverityName(getItemNameById(severityId));
        }
        alertVo.setUpdateBy(userMap.get("username").toString());//account
        ResponseEntity<AlertVo> result = alertClient.update(alertVo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据id获取单条预警信息
    public AlertVo findAlertById(String id){
        Assert.hasText(id,"id不能为空");
        ResponseEntity<AlertVo> result = alertClient.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据id逻辑删除单条预警信息
    public void deleteAlert(String id){
        Assert.hasText(id,"id不能为空");
        ResponseEntity<Void> result = alertClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //查询预警信息列表 --- 分页
    public RestPageImpl<AlertVo> findAlerts(AlertPageSearchVo alertPageVo){
        ResponseEntity<RestPageImpl<AlertVo>> result = alertClient.findPage(alertPageVo);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据预警信息Id获取预警通知信息
    public AlertNoticeDTOVo getNoticeByAlertId(String alertId){
        Assert.hasText(alertId,"alertId 不能为空");
        ResponseEntity<AlertVo> result = alertClient.findOne(alertId);
        AlertVo alertVo = ResponseEntityUtils.achieveResponseEntityBody(result);

        //根据预警id获取所有通知记录
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("alertId",alertId);
        ResponseEntity<List<AlertNoticeVo>> noticeListResult = noticeClient.findList(map);
        List<AlertNoticeVo> noticeVos = ResponseEntityUtils.achieveResponseEntityBody(noticeListResult);

        String content = "";
        String receiveOrgNameStr = "";
        List<String> receiveOrgIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(noticeVos)){
            for(AlertNoticeVo noticeVo : noticeVos){
                content = noticeVo.getRequire(); //通知内容
                StringBuilder builder = new StringBuilder();
                builder.append(noticeVo.getReceiveOrgName()).append(",");
                receiveOrgNameStr+=builder.toString();
                receiveOrgIds.add(noticeVo.getReceiveOrgId());
            }
            receiveOrgNameStr = receiveOrgNameStr.substring(0,receiveOrgNameStr.length()-1);
        }

        return new AlertNoticeDTOVo(alertVo,content,receiveOrgNameStr,receiveOrgIds);
    }

    //预警忽略/办结操作   忽略、处置中、已办结
    public void alertIgnore(Map<String,Object> map,OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String alertId = map.get("alertId").toString();
        Assert.hasText(alertId,"alertId 不能为空");
        ResponseEntity<AlertVo> result = alertClient.findOne(alertId);
        AlertVo alertVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        alertVo.setAlertStatus(map.get("alertStatus").toString());
        alertVo.setUpdateBy(userMap.get("username").toString());

        //更新alertStatus为入参的值
        ResponseEntity<AlertVo> resultVo = alertClient.update(alertVo,alertVo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //预警通知操作 -- 批量新增Notice
    /**
     * {
     "orgIds": [
     "string"
     ],
     "alertId": "string",
     "require": "string",
     "alertStatus": "string"      --  通知：2
     }
     * */
    public void alertNotice(Map<String,Object> map,OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        List<String> orgIds = (List<String>)map.get("orgIds");
        String alertId = map.get("alertId").toString();
        Assert.hasText(alertId,"alertId 不能为空");
        ResponseEntity<AlertVo> result = alertClient.findOne(alertId);
        AlertVo alertVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        if(!CollectionUtils.isEmpty(orgIds)){
            List<AlertNoticeVo> noticeVos = new ArrayList<>();
            for(String orgId : orgIds){
                AlertNoticeVo noticeVo = new AlertNoticeVo();
                noticeVo.setAlertId(alertId);
                noticeVo.setReceiveOrgId(orgId);
                noticeVo.setReceiveOrgName(getOrgVoById(orgId).getOrgName());
                noticeVo.setReceiveStatus(AlarmGlobal.Alert_Notice_ACCEPTED);
                noticeVo.setFeedbackStatus(AlarmGlobal.ALAERT_FB_NOT_START); //未开始
                noticeVo.setRequire(map.get("require").toString());
                noticeVos.add(noticeVo);
            }
            ResponseEntity<List<AlertNoticeVo>> resultList = noticeClient.createList(noticeVos);
            List<AlertNoticeVo> resultVoList = ResponseEntityUtils.achieveResponseEntityBody(resultList);

            //更新通知状态
            alertVo.setAlertStatus(map.get("alertStatus").toString());
            alertVo.setUpdateBy(userMap.get("username").toString());
            ResponseEntity<AlertVo> resultVo = alertClient.update(alertVo,alertVo.getId());
            AlertVo alertResultVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            //发送预警消息 -- 预警通知
            infoMsgService.sendAlarmMsg(resultVoList,alertResultVo);
        }
    }

    /**
     * 根据浙能需求新修改的alertNotice
     *      1.根据orgIds新增数据到中间表
     *      2.将预警信息的isNotice标记为置为0（已通知）
     * @param map
     * @param principal
     */
    public void alertNoticeNew(Map<String,Object> map,OAuth2Authentication principal){
        //获取用户信息
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);

        List<String> orgIds = (List<String>)map.get("orgIds");//从map中取出orgIds
        String alertId = map.get("alertId").toString();//从map中取出alertId
        Assert.hasText(alertId,"alertId 不能为空");
        //根据alertId查出预警信息
        ResponseEntity<AlertVo> result = alertClient.findOne(alertId);
        AlertVo alertVo = ResponseEntityUtils.achieveResponseEntityBody(result);

        if(!CollectionUtils.isEmpty(orgIds)){
            //中间表对应的voList
            List<AlertNoticeVo> noticeVos = new ArrayList<>();
            AlertNoticeVo noticeVo;
            for(String orgId : orgIds){
                noticeVo = new AlertNoticeVo();
                noticeVo.setAlertId(alertId);
                noticeVo.setReceiveOrgId(orgId);
                noticeVo.setReceiveOrgName(getOrgVoById(orgId).getOrgName());
                noticeVo.setReceiveStatus(AlarmGlobal.Alert_Notice_ACCEPTED);
                noticeVo.setFeedbackStatus(AlarmGlobal.ALAERT_FB_NOT_START); //未开始
                noticeVos.add(noticeVo);
            }
            //然后去中间表创建
            ResponseEntity<List<AlertNoticeVo>> resultList = noticeClient.createList(noticeVos);
            List<AlertNoticeVo> resultVoList = ResponseEntityUtils.achieveResponseEntityBody(resultList);

            //更新通知状态is_notice为0（已通知）
            alertVo.setIsNotice(AlarmGlobal.Alert_IS_NOTICE_YES);
            alertVo.setUpdateBy(userMap.get("username").toString());
            ResponseEntity<AlertVo> resultVo = alertClient.update(alertVo,alertVo.getId());
            AlertVo alertResultVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

            //发送预警消息 -- 预警通知
            infoMsgService.sendAlarmMsg(resultVoList,alertResultVo);
        }
    }

    //预警通知反馈查看
    public NoticeFbTotalVo viewFeedbackList(String alertId){

        NoticeFbTotalVo noticeFbTotalVo = new NoticeFbTotalVo();

        Assert.hasText(alertId,"预警信息Id不能为空字符串");
        //获取预警信息
        ResponseEntity<AlertVo> alertResult = alertClient.findOne(alertId);
        AlertVo alert = ResponseEntityUtils.achieveResponseEntityBody(alertResult);

        noticeFbTotalVo.setAlert(alert);

        //根据预警id获取所有通知记录
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("alertId",alertId);
        ResponseEntity<List<AlertNoticeVo>> noticeListResult = noticeClient.findList(map);
        List<AlertNoticeVo> noticeVos = ResponseEntityUtils.achieveResponseEntityBody(noticeListResult);

        String receiveOrg = "";  //接收单位
        String noticeContent = ""; //通知内容（追加通知的内容也不变）
        int total = 0; //通知总数
        int complete = 0; //已完成单位数
        int progress = 0; //进行中单位数
        int start = 0; //未开始单位数
        List<AlertFeedbacks> alertFeedbacks = new ArrayList<>();

        if(!CollectionUtils.isEmpty(noticeVos)){
            for(AlertNoticeVo vo:noticeVos){
                StringBuilder builder = new StringBuilder();
                String noticeTimeStr = DateUtil.getDateTimeStr(vo.getNoticeTime());  //通知时间
                String receiveOrgName = vo.getReceiveOrgName(); //通知接收单位
                builder.append(receiveOrgName).append("(").append(noticeTimeStr).append(")").append(",");
                receiveOrg+=builder.toString();
                noticeContent = vo.getRequire(); //通知要求

                switch (vo.getFeedbackStatus()){
                    case AlarmGlobal.ALAERT_FB_NOT_START: //未开始
                        start++;break;
                    case AlarmGlobal.ALAERT_FB_DOING://进行中
                        progress++;break;
                    case AlarmGlobal.ALAERT_FB_COMPLETED://已完成
                        complete++;break;
                }

                alertFeedbacks.add(getAlertFeedbackFromAlertNoticeVo(vo));

            }
            receiveOrg = receiveOrg.substring(0,receiveOrg.length()-1); //去掉最后一个逗号
            total = noticeVos.size();
        }

        if(null!=receiveOrg){
            noticeFbTotalVo.setAlertReceiveOrgs(new AlertReceiveOrgsVo(receiveOrg,noticeContent));
        }

        noticeFbTotalVo.setStatistic(new NoticeFbStatisticVo(total,complete,progress,start));

        noticeFbTotalVo.setAlertFeedbacks(alertFeedbacks);

        return noticeFbTotalVo;
    }

    //将通知的所有反馈合并到一个反馈包装类AlertFeedbacks中
    private AlertFeedbacks getAlertFeedbackFromAlertNoticeVo(AlertNoticeVo vo){
        List<NoticeFeedbackVo> feedbacks = vo.getFeedbacks();
        String receiveOrgName = vo.getReceiveOrgName(); //反馈单位
        String content = "";  //反馈内容
        LocalDateTime feedbackLasttime = vo.getFeedbackLasttime(); //最新反馈时间
        String feedbackStatus = vo.getFeedbackStatus(); //反馈状态
        List<String> feedbackIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(feedbacks)){
            for(NoticeFeedbackVo temp : feedbacks){
                String fbContent = temp.getContent();
                String fbTimeStr = DateUtil.getDateTimeStr(temp.getFeedbackTime());
                StringBuilder builder = new StringBuilder();
                builder.append(fbContent).append("(").append(fbTimeStr).append(")").append(",");
                content+=builder.toString();

                feedbackIds.add(temp.getId());
            }
            content = content.substring(0,content.length()-1);
        }
        return new AlertFeedbacks(receiveOrgName,content,feedbackLasttime,feedbackStatus,feedbackIds);
    }


    //根据条件查询本单位接收的预警通知列表-分页
    public RestPageImpl<AlertNoticeVo> findRecAlerts(AlertNoticePageSearchVo alertNoticePageVo){
        ResponseEntity<RestPageImpl<AlertNoticeVo>> resultPage = noticeClient.findPage(alertNoticePageVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultPage);
    }

    //接收的预警通知查看
    public AlertNoticeVo viewNoticeRec(String alertRecId){
        Assert.hasText(alertRecId,"预警通知Id不能为空字符串");
        ResponseEntity<AlertNoticeVo> resultVo = noticeClient.findOne(alertRecId);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //预警通知反馈，保存同时，修改接收表中的最新反馈状态、最近反馈内容、最近反馈时间
    public void feedbackAlert(NoticeFbSaveVo noticeFbSaveVo){

        String alertReceiveId = noticeFbSaveVo.getAlertReceiveId(); //预警通知对象id
        ResponseEntity<AlertNoticeVo> noticeVoResult = noticeClient.findOne(alertReceiveId);
        AlertNoticeVo noticeVo = ResponseEntityUtils.achieveResponseEntityBody(noticeVoResult);
        String oldFeedbackStatus = noticeVo.getFeedbackStatus(); //数据库中的预警通知的反馈状态
        if(!StringUtils.isEmpty(oldFeedbackStatus)
                &&AlarmGlobal.ALAERT_FB_COMPLETED.equals(oldFeedbackStatus)){//反馈状态为：已完成，则直接跳出(预防前台按钮控制问题)
            return;
        }

        NoticeFeedbackVo feedbackVo = new NoticeFeedbackVo();
        feedbackVo.setAlertReceiveId(alertReceiveId);
        feedbackVo.setContent(noticeFbSaveVo.getContent());
        feedbackVo.setFeedbackBy(noticeFbSaveVo.getFeedbackBy());
        feedbackVo.setQuestion(noticeFbSaveVo.getQuestion());

        ResponseEntity<NoticeFeedbackVo> feedbackVoResult = noticeClient.createFeedBack(feedbackVo);
        NoticeFeedbackVo feedbackResultVo =  ResponseEntityUtils.achieveResponseEntityBody(feedbackVoResult);

        //反馈附件
        String feedbackId = feedbackResultVo.getId();
        List<String> files = noticeFbSaveVo.getFileIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(feedbackId,files,null));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);

        String feedbackStatus = noticeFbSaveVo.getFeedbackStatus(); //页面填写反馈状态
        LocalDateTime feedbackTime = feedbackResultVo.getFeedbackTime();
        String feedbackContent = feedbackResultVo.getContent();
        noticeVo.setFeedbackStatus(feedbackStatus); //更新通知的反馈状态
        noticeVo.setFeedbackContent(feedbackContent); //更新通知的反馈内容
        noticeVo.setFeedbackLasttime(feedbackTime); //反馈时间
        ResponseEntity<AlertNoticeVo> result = noticeClient.update(noticeVo,noticeVo.getId());
        AlertNoticeVo alertNoticeVo = ResponseEntityUtils.achieveResponseEntityBody(result);

        //发送系统消息 -- 预警通知反馈
        infoMsgService.sendAlarmFbMsg(feedbackResultVo,alertNoticeVo);
    }

    //获取该条预警信息下发的单位列表及各单位的反馈情况,供浙能首页使用
    public List<AlertNoticeVo> getAllOrgsAndFeedback(String alarmId){
        Assert.hasText(alarmId,"预警信息Id不能为空字符串");
        //根据预警id获取所有通知记录
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("alertId",alarmId);
        ResponseEntity<List<AlertNoticeVo>> noticeListResult = noticeClient.findList(map);
        List<AlertNoticeVo> noticeVos = ResponseEntityUtils.achieveResponseEntityBody(noticeListResult);
        return noticeVos;
    }

    /**
     * 【20190104浙能新增】通过预警信息ID获取该预警信息已经通知的单位列表
     * @param alertId
     * @return
     */
    public List<AlertReceicerInfoVo> getReceiverListByAlertId(String alertId) {
        //查到List<AlertNoticeVo>
        List<AlertNoticeVo> alertNoticeVos = getAllOrgsAndFeedback(alertId);
        List<AlertReceicerInfoVo> alertReceicerInfoVos = null;
        if(!CollectionUtils.isEmpty(alertNoticeVos)){
            alertReceicerInfoVos = new ArrayList<>();
            AlertReceicerInfoVo alertReceicerInfoVo;
            for (AlertNoticeVo alertNoticeVo : alertNoticeVos) {
                String orgId = alertNoticeVo.getReceiveOrgId();
                String orgName = alertNoticeVo.getReceiveOrgName();
                alertReceicerInfoVo = new AlertReceicerInfoVo(orgId , orgName);
                alertReceicerInfoVos.add(alertReceicerInfoVo);
            }
        }
        return alertReceicerInfoVos;
    }
}
