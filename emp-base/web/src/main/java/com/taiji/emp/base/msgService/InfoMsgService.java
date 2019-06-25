package com.taiji.emp.base.msgService;

import com.taiji.base.common.constant.msg.MsgConfig;
import com.taiji.base.common.constant.msg.MsgSendGlobal;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.common.constant.NoticeGlobal;
import com.taiji.emp.base.feign.MsgNoticeClient;
import com.taiji.emp.base.feign.UserClient;
import com.taiji.emp.base.vo.NoticeFeedBackVo;
import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
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
     * 组装发送消息并入库 -- 公告发送
     */
    public void sendInfoMsg(List<NoticeReceiveOrgVo> receiveOrgVoList){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (!CollectionUtils.isEmpty(receiveOrgVoList)){
                for (NoticeReceiveOrgVo vo : receiveOrgVoList){
                    String title = MsgSendGlobal.MSG_INFO_NOTICE_TITLE_SEND;
                    String sendOrg = vo.getBuildOrgId();
                    String sendOrgName = vo.getBuildOrgName();
                    String dealTime = DateUtil.getDateTimeStr(vo.getSendTime());
                    String sendBy = vo.getSendBy();
                    StringBuilder builder = new StringBuilder();
                    builder.append(dealTime)
                            .append(",")
                            .append(sendBy)
                            .append("发送了一条通知信息,")
                            .append("标题为").append(vo.getTitle());
                    String msgContent = builder.toString();
                    String entityId = vo.getNoticeId();

                    String receiveOrgId = vo.getReceiveOrgId();
                    ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(receiveOrgId);
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
                        continue;
                    }

                    MsgNoticeVo msgNoticeVo = new MsgNoticeVo();
                    msgNoticeVo.setTitle(title);
                    msgNoticeVo.setSendOrg(sendOrg);
                    msgNoticeVo.setOrgName(sendOrgName);
                    msgNoticeVo.setMsgContent(msgContent);

                    String msgType = MsgSendGlobal.MSG_TYPE_NOTICE_NEWS_ID;  //常量  --消息类型 id
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
            }
        });
    }

    /**
     * 组装发送消息并入库 -- 公告反馈
     */
    public void returnInfoMsg(NoticeReceiveOrgVo vo, NoticeFeedBackVo noticeFeedBackVo){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (null != vo && null != noticeFeedBackVo){
                String title = MsgSendGlobal.MSG_INFO_NOTICE_TITLE_FEEDBACK;
                String sendOrg = vo.getReceiveOrgId();
                String sendOrgName = vo.getReceiveOrgName();
                String dealTime = DateUtil.getDateTimeStr(noticeFeedBackVo.getFeedbackTime());
                String feedbackBy = noticeFeedBackVo.getFeedbackBy();
                String title1 = vo.getTitle();
                StringBuilder builder = new StringBuilder();
                builder.append(dealTime)
                        .append(",")
                        .append(feedbackBy)
                        .append("针对")
                        .append(title1)
                        .append("进行反馈,反馈内容为").append(noticeFeedBackVo.getContent());
                String msgContent = builder.toString();
                String entityId = vo.getNoticeId();
                String dealOrgId = vo.getBuildOrgId();
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

                String msgType = MsgSendGlobal.MSG_TYPE_NOTICE_NEWS_ID;
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
        String userName = NoticeGlobal.INFO_USER_NAME;
        ResponseEntity<UserVo> userClientByName = userClient.findByName(userName);
        UserVo userVo = ResponseEntityUtils.achieveResponseEntityBody(userClientByName);
        return userVo;
    }
}
