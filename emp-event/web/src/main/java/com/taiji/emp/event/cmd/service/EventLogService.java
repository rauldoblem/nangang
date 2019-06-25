package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.EventLogClient;
import com.taiji.emp.event.cmd.vo.EventLogVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class EventLogService extends BaseService {

    @Autowired
    EventLogClient client;
    @Autowired
    private EventEvaProcessClient processClient;

    /**
     * 新增事件应急日志
     * @param vo
     * @param principal
     */
    public void addEventLog(EventLogVo vo, Principal principal) {
        String userName = principal.getName();
        //创建人
        vo.setCreateBy(userName);
        //更新人
        vo.setUpdateBy(userName);
        ResponseEntity<EventLogVo> resultVo = client.addEventLog(vo);
        EventLogVo eventLogVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //添加“过程再现”节点
        if (null != eventLogVo) {
            addNode(eventLogVo, userName);
        }
    }

    /**
     * 添加“过程再现”节点
     * @param eventLogVo
     * @param userName
     */
    private void addNode(EventLogVo eventLogVo, String userName) {
        EventEvaProcessVo processVo = new EventEvaProcessVo();
        processVo.setEventId(eventLogVo.getEventId());
        processVo.setEventName(eventLogVo.getEventName());
        StringBuilder builder = new StringBuilder();
        String operator = eventLogVo.getOperator();
        String logContent = eventLogVo.getLogContent();
        builder.append("为《")
                .append(operator)
                .append("创建应急应急日志，内容为：")
                .append(logContent)
                .append("》");
        processVo.setOperation(builder.toString());
        processVo.setOperationTime(eventLogVo.getOperatorTime());
        processVo.setFirstNodeCode(ProcessNodeGlobal.DISPOSAL);
        processVo.setFirstNodeName(ProcessNodeGlobal.DISPOSAL_NAME);
        processVo.setSecNodeCode(ProcessNodeGlobal.DISPOSAL_EVENT_LOG);
        processVo.setSecNodeName(ProcessNodeGlobal.DISPOSAL_EVENT_LOG_NAME);
        processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
        processVo.setOperator(userName);
        processVo.setCreateBy(userName);
        processVo.setUpdateBy(userName);
        processClient.create(processVo);
    }

    /**
     * 删除事件应急日志
     * @param id
     */
    public void deleteEventLog(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 修改事件应急日志
     * @param vo
     * @param id
     * @param principal
     */
    public void updateEventLog(EventLogVo vo, String id, Principal principal) {
        String userName = principal.getName();
        //创建人
        vo.setCreateBy(userName);
        //更新人
        vo.setUpdateBy(userName);
        ResponseEntity<EventLogVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 获取单条事件应急日志信息
     * @param id
     * @return
     */
    public EventLogVo findEventLogById(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<EventLogVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询事件应急日志列表
     * @return
     */
    public List<EventLogVo> findList(EventLogVo vo) {
        ResponseEntity<List<EventLogVo>> result = client.findList(vo);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
