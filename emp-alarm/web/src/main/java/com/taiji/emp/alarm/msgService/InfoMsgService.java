package com.taiji.emp.alarm.msgService;

import com.taiji.base.common.constant.msg.MsgConfig;
import com.taiji.base.common.constant.msg.MsgSendGlobal;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.alarm.common.constant.AlarmGlobal;
import com.taiji.emp.alarm.feign.MsgNoticeClient;
import com.taiji.emp.alarm.feign.UserClient;
import com.taiji.emp.alarm.vo.AlertNoticeVo;
import com.taiji.emp.alarm.vo.AlertVo;
import com.taiji.emp.alarm.vo.NoticeFeedbackVo;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 用于组装监测预警模块的消息service
 * @author qizhijie-pc
 * @date 2018年12月18日14:54:00
 */
@Component
@AllArgsConstructor
public class InfoMsgService {

    MsgNoticeClient msgClient;
    UserClient userClient;

    /**
     * 组装发送消息并入库 -- 预警通知
     */
    public void sendAlarmMsg(List<AlertNoticeVo> noticeVos, AlertVo alertVo){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if(!CollectionUtils.isEmpty(noticeVos)&&null!=alertVo){
                String title = MsgSendGlobal.MSG_INFO_ALARM_NOTICE_TITLE;
                String sendOrg = alertVo.getCreateOrgId();
                String sendOrgName = alertVo.getCreateOrgName();
                String noticeTime = DateUtil.getDateTimeStr(noticeVos.get(0).getNoticeTime()); //同一批次发出，因此选择最后一条记录的发布时间
                StringBuilder builder = new StringBuilder();
                builder.append(noticeTime)
                        .append(",")
                        .append(sendOrgName)
                        .append("发送了一条预警通知,")
                        .append("标题为").append(alertVo.getHeadline());
                String msgContent = builder.toString();
                String entityId = alertVo.getId();
                List<String> recOrgIds = noticeVos.stream().map(AlertNoticeVo::getReceiveOrgId).collect(Collectors.toList());
                ResponseEntity<List<UserVo>> result = userClient.findListByOrgIds(recOrgIds);
                List<UserVo> userList = ResponseEntityUtils.achieveResponseEntityBody(result);

                //要过滤的用户信息
                String id = getUserVoInfo().getId();
                userList = userList.stream().filter(temp -> !temp.getId().equals(id)).collect(Collectors.toList());
                
                List<Receiver> receivers = new ArrayList<>();
                if(!CollectionUtils.isEmpty(userList)){
                    for(UserVo userVo : userList){
                        Receiver receiver = new Receiver();
                        receiver.setId(userVo.getId());
                        receiver.setName(userVo.getAccount());
                        receivers.add(receiver);
                    }
                }else{
                    return;
                }
                MsgNoticeVo msgNoticeVo = new MsgNoticeVo();
                msgNoticeVo.setTitle(title);
                msgNoticeVo.setSendOrg(sendOrg);
                msgNoticeVo.setOrgName(sendOrgName);
                msgNoticeVo.setMsgContent(msgContent);

                String msgType = MsgSendGlobal.MSG_TYPE_ALERT_NEWS_ID;  //常量  --消息类型 id
                MsgConfig msgConfig = MsgSendGlobal.msgTypeMap.get(msgType);
                msgNoticeVo.setMsgType(msgType);
                msgNoticeVo.setTypeName(msgConfig.getModuleName());
                msgNoticeVo.setIcon(msgConfig.getIcon());
                msgNoticeVo.setPath(msgConfig.getPath());

                msgNoticeVo.setEntityId(entityId);
                msgNoticeVo.setReceivers(receivers);
                //发送消息
                msgClient.create(msgNoticeVo);
            }
        });
    }

    /**
     * 组装发送消息并入库 -- 预警通知反馈
     */
    public void sendAlarmFbMsg(NoticeFeedbackVo feedbackResultVo, AlertNoticeVo alertNoticeVo){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if(null!=feedbackResultVo&&null!=alertNoticeVo){
                String title = MsgSendGlobal.MSG_INFO_ALARM_NOTICE_FB_TITLE;
                String sendOrg = alertNoticeVo.getReceiveOrgId();
                String sendOrgName = alertNoticeVo.getReceiveOrgName();
                String feedbackTime = DateUtil.getDateTimeStr(feedbackResultVo.getFeedbackTime());
                String feedbackBy = feedbackResultVo.getFeedbackBy();
                StringBuilder builder = new StringBuilder();
                builder.append(feedbackTime)
                        .append(",")
                        .append(feedbackBy)
                        .append("针对")
                        .append(alertNoticeVo.getAlert().getHeadline())
                        .append("进行反馈,")
                        .append("反馈内容为").append(feedbackResultVo.getContent());
                String msgContent = builder.toString();
                String entityId = alertNoticeVo.getAlert().getId();
                String recOrgId = alertNoticeVo.getAlert().getCreateOrgId();
                ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(recOrgId);
                List<UserVo> userList = ResponseEntityUtils.achieveResponseEntityBody(result);

                //要过滤的用户信息
                String id = getUserVoInfo().getId();
                userList = userList.stream().filter(temp -> !temp.getId().equals(id)).collect(Collectors.toList());

                List<Receiver> receivers = new ArrayList<>();
                if(!CollectionUtils.isEmpty(userList)){
                    for(UserVo userVo : userList){
                        Receiver receiver = new Receiver();
                        receiver.setId(userVo.getId());
                        receiver.setName(userVo.getAccount());
                        receivers.add(receiver);
                    }
                }else{
                    return;
                }
                MsgNoticeVo msgNoticeVo = new MsgNoticeVo();
                msgNoticeVo.setTitle(title);
                msgNoticeVo.setSendOrg(sendOrg);
                msgNoticeVo.setOrgName(sendOrgName);
                msgNoticeVo.setMsgContent(msgContent);

                String msgType = MsgSendGlobal.MSG_TYPE_ALERT_NEWS_ID;  //常量  --消息类型 id
                MsgConfig msgConfig = MsgSendGlobal.msgTypeMap.get(msgType);
                msgNoticeVo.setMsgType(msgType);
                msgNoticeVo.setTypeName(msgConfig.getModuleName());
                msgNoticeVo.setIcon(msgConfig.getIcon());
                msgNoticeVo.setPath(msgConfig.getPath());

                msgNoticeVo.setEntityId(entityId);
                msgNoticeVo.setReceivers(receivers);
                //发送消息
                msgClient.create(msgNoticeVo);
            }
        });
    }

    /**
     * 获取特定的用户信息
     * @return
     */
    public UserVo getUserVoInfo(){
        String userName = AlarmGlobal.INFO_USER_NAME;
        ResponseEntity<UserVo> userClientByName = userClient.findByName(userName);
        UserVo userVo = ResponseEntityUtils.achieveResponseEntityBody(userClientByName);
        return userVo;
    }

}
