package com.taiji.emp.nangang.msgService;

import com.taiji.base.common.constant.msg.MsgConfig;
import com.taiji.base.common.constant.msg.MsgSendGlobal;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskExeorgVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskVo;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.msgService.feign.MsgNoticeClient;
import com.taiji.emp.nangang.msgService.feign.UserClient;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 用于组装信息报送模块的消息service
 * @author qizhijie-pc
 * @date 2018年11月28日16:52:34
 */
@Component
@AllArgsConstructor
public class InfoMsgService {

    MsgNoticeClient msgClient;
    UserClient userClient;

    /**
     * 组装发送消息并入库 -- 报送分发
     */
    public void sendInfoMsg(AccDealVo dealVo){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if(null!=dealVo){
                String title = MsgSendGlobal.MSG_INFO_TITLE_SEND;
                String sendOrg = dealVo.getCreateOrgId();
                String sendOrgName = dealVo.getCreateOrgName();
                String dealTime = DateUtil.getDateTimeStr(dealVo.getDealTime());
                StringBuilder builder = new StringBuilder();
                builder.append(dealTime)
                        .append(",")
                        .append(sendOrgName)
                        .append("发送了一条接报信息,")
                        .append("标题为").append(dealVo.getImAccept().getEventName());
                String msgContent = builder.toString();
                String entityId = dealVo.getImAccept().getId();
                String dealOrgId = dealVo.getDealOrgId();
                ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(dealOrgId);
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

                String msgType = MsgSendGlobal.MSG_TYPE_INFO_NEWS_ID;  //常量  --消息类型 id
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
     * 组装发送消息并入库 -- 信息退回
     */
    public void returnInfoMsg(AccDealVo dealVo){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if(null!=dealVo){
                String title = MsgSendGlobal.MSG_INFO_TITLE_RETURN;
                String sendOrg = dealVo.getCreateOrgId();
                String sendOrgName = dealVo.getCreateOrgName();
                String dealTime = DateUtil.getDateTimeStr(dealVo.getDealTime());
                StringBuilder builder = new StringBuilder();
                builder.append(dealTime)
                        .append(",")
                        .append(sendOrgName)
                        .append("退回了一条接报信息,")
                        .append("标题为").append(dealVo.getImAccept().getEventName());
                String msgContent = builder.toString();
                String entityId = dealVo.getImAccept().getId();
                String dealOrgId = dealVo.getDealOrgId();
                ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(dealOrgId);
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

                String msgType = MsgSendGlobal.MSG_TYPE_INFO_NEWS_ID;
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
     * 添加应急任务下发系统消息
     * @param taskVo
     */
    public void sendSystemDispatchMsg(TaskVo taskVo) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (null != taskVo){
                String title = MsgSendGlobal.MSG_INFO_TASK_TITLE;
                String sendOrg = taskVo.getCreateOrgId();
                String sendOrgName = taskVo.getCreateOrgName();
                TaskExeorgVo taskExeorg = taskVo.getTaskExeorg();
                Assert.notNull(taskExeorg,"taskExeorg不能为空");
                String dealTime = DateUtil.getDateTimeStr(taskExeorg.getSendTime());
                StringBuilder builder = new StringBuilder();
                builder.append(dealTime)
                        .append(",")
                        .append(sendOrgName)
                        .append("发送了一条任务,")
                        .append("标题为").append(taskVo.getName());
                String msgContent = builder.toString();
                String entityId = taskVo.getId();
                String dealOrgId = taskExeorg.getOrgId();
                ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(dealOrgId);
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
                String msgType = MsgSendGlobal.MSG_TYPE_TASK_NEWS_ID;
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
     * 添加应急任务反馈系统消息
     * @param taskVo
     * @param feedbackVo
     */
    public void sendSystemFeedBackMsg(TaskVo taskVo, TaskFeedbackVo feedbackVo,String sendOrgName,String sendOrg) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (null != taskVo && null != feedbackVo){
                String title = MsgSendGlobal.MSG_INFO_FEEDBACK_TITLE;
                String dealTime = DateUtil.getDateTimeStr(feedbackVo.getFeedbackTime());
                StringBuilder builder = new StringBuilder();
                builder.append(dealTime)
                        .append(",")
                        .append(sendOrgName)
                        .append("针对")
                        .append(taskVo.getName())
                        .append("进行反馈,反馈内容为").append(feedbackVo.getContent());
                String msgContent = builder.toString();
                String entityId = taskVo.getId();
                String dealOrgId = taskVo.getCreateOrgId();
                ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(dealOrgId);
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
                String msgType = MsgSendGlobal.MSG_TYPE_TASK_NEWS_ID;
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
        String userName = NangangGlobal.INFO_USER_NAME;
        ResponseEntity<UserVo> userClientByName = userClient.findByName(userName);
        UserVo userVo = ResponseEntityUtils.achieveResponseEntityBody(userClientByName);
        return userVo;
    }
}
