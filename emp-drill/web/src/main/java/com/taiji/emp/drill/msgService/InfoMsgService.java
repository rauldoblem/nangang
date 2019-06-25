package com.taiji.emp.drill.msgService;

import com.taiji.base.common.constant.msg.MsgConfig;
import com.taiji.base.common.constant.msg.MsgSendGlobal;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.drill.common.constant.DrillGlobal;
import com.taiji.emp.drill.feign.MsgNoticeClient;
import com.taiji.emp.drill.feign.UserClient;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.emp.drill.vo.DrillSchemeVo;
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
     * 发送演练计划------上报或下发系统消息
     * @param drillPlanVo
     */
    public void sendSystemPlanMsg(DrillPlanVo drillPlanVo,List<DrillPlanReceiveVo> voList) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (!CollectionUtils.isEmpty(voList)){
                for (DrillPlanReceiveVo receiveVo : voList){
                    String title = MsgSendGlobal.MSG_INFO_PLAN_TITLE;
                    String sendOrg = drillPlanVo.getOrgId();
                    String sendOrgName = drillPlanVo.getOrgName();
                    String dealTime = DateUtil.getDateTimeStr(receiveVo.getSendTime());
                    String sender = receiveVo.getSender();
                    StringBuilder builder = new StringBuilder();
                    builder.append(dealTime)
                            .append(",")
                            .append(sender)
                            .append("发送了一条通知,")
                            .append("标题为").append(drillPlanVo.getDrillName());
                    String msgContent = builder.toString();
                    String entityId = drillPlanVo.getId();
                    String receiveOrgId = receiveVo.getOrgId();

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
                        return;
                    }

                    MsgNoticeVo msgNoticeVo = new MsgNoticeVo();
                    msgNoticeVo.setTitle(title);
                    msgNoticeVo.setSendOrg(sendOrg);
                    msgNoticeVo.setOrgName(sendOrgName);
                    msgNoticeVo.setMsgContent(msgContent);

                    String msgType = MsgSendGlobal.MSG_TYPE_DRILL_PLAN_NEWS_ID;  //常量  --消息类型 id
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
     * 发送演练方案-----上报或下发系统消息
     * @param drillSchemeVo
     */
    public void sendSystemSchemeMsg(DrillSchemeVo drillSchemeVo,List<DrillSchemeReceiveVo> voList) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (!CollectionUtils.isEmpty(voList)){
                for (DrillSchemeReceiveVo receiveVo : voList){
                    String title = MsgSendGlobal.MSG_INFO_SCHEME_TITLE;
                    String sendOrg = drillSchemeVo.getOrgId();
                    String sendOrgName = drillSchemeVo.getOrgName();
                    String dealTime = DateUtil.getDateTimeStr(receiveVo.getSendTime());
                    String sender = receiveVo.getSender();
                    StringBuilder builder = new StringBuilder();
                    builder.append(dealTime)
                            .append(",")
                            .append(sender)
                            .append("发送了一条通知,")
                            .append("标题为").append(drillSchemeVo.getDrillName());
                    String msgContent = builder.toString();
                    String entityId = drillSchemeVo.getId();
                    String receiveOrgId = receiveVo.getOrgId();

                    ResponseEntity<List<UserVo>> result = userClient.findListByOrgId(receiveOrgId);
                    List<UserVo> userList = ResponseEntityUtils.achieveResponseEntityBody(result);

                    //要过滤的用户信息
                    UserVo userVoInfo = getUserVoInfo();
                    userList = userList.stream().filter(temp -> temp != userVoInfo).collect(Collectors.toList());

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

                    String msgType = MsgSendGlobal.MSG_TYPE_DRILL_SCHEME_NEWS_ID;  //常量  --消息类型 id
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
     * 获取特定的用户信息
     * @return
     */
    public UserVo getUserVoInfo(){
        String userName = DrillGlobal.INFO_USER_NAME;
        ResponseEntity<UserVo> userClientByName = userClient.findByName(userName);
        UserVo userVo = ResponseEntityUtils.achieveResponseEntityBody(userClientByName);
        return userVo;
    }
}
