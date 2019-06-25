package com.taiji.emp.event.cmd.service;

import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.event.cmd.feign.AnalyseClient;
import com.taiji.emp.event.cmd.feign.DocAttClient;
import com.taiji.emp.event.cmd.vo.AnalyseSaveVo;
import com.taiji.emp.event.cmd.vo.AnalyseVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.infoDispatch.feign.EventClient;
import com.taiji.emp.event.infoDispatch.feign.UtilsFeignClient;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class AnalyseService extends BaseService {

    @Autowired
    private DocAttClient docAttClient;
    @Autowired
    private AnalyseClient client;
    @Autowired
    private UtilsFeignClient utilsFeignClient;
    @Autowired
    private EventEvaProcessClient processClient;
    @Autowired
    private EventClient eventClient;

    //新增事件研判信息
    public void addEventAnalyse(AnalyseSaveVo vo,Principal principal){
        Assert.notNull(vo,"AnalyseSaveVo 不能为null");
        AnalyseVo eventAnalyse =vo.getEventAnalyse();
        eventAnalyse.setCreateBy(principal.getName());
        eventAnalyse.setUpdateBy(principal.getName());

        ResponseEntity<AnalyseVo> resultVo = client.create(eventAnalyse);
        AnalyseVo analyseVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = vo.getFileIds();
        List<String> docAttDelIds = vo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);

        //添加事件研判过程在线节点
        createEventAnalyseProcesss(analyseVo,principal);
    }

    /**
     * 添加事件研判过程在线节点
     */
    private void createEventAnalyseProcesss(AnalyseVo analyseVo,Principal principal){
        String account = principal.getName();
        EventEvaProcessVo processVo = new EventEvaProcessVo();

        String eventId = analyseVo.getEventId();
        Assert.hasText(eventId,"eventId 不能为空");
        ResponseEntity<EventVo> resultVo = eventClient.findOne(eventId);
        EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        Assert.notNull(eventVo,"eventVo 不能为null");
        String eventName = eventVo.getEventName();  //事件对象 -- 事件名称

        processVo.setEventId(eventId);
        processVo.setEventName(eventName);
        processVo.setOperator(account); //当前用户
        processVo.setOperationTime(utilsFeignClient.now().getBody()); //系统当前时间

        StringBuilder builder = new StringBuilder();
        builder.append("研判意见为：").append(analyseVo.getAnalyseResult());

        processVo.setOperation(builder.toString());
        processVo.setFirstNodeCode(ProcessNodeGlobal.ANALYSE);
        processVo.setFirstNodeName(ProcessNodeGlobal.ANALYSE_NAME);
        processVo.setSecNodeCode(ProcessNodeGlobal.ANALYSE_RESULT);
        processVo.setSecNodeName(ProcessNodeGlobal.ANALYSE_RESULT_NAME);
        processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
        processVo.setCreateBy(account);
        processVo.setUpdateBy(account);

        //保存事件研判过程在线节点
        ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //更新事件研判信息
    public void updateEventAnalyse(AnalyseSaveVo vo,String id,Principal principal){
        Assert.notNull(vo,"AnalyseSaveVo 不能为null");
        Assert.hasText(id,"id 不能为空字符串");
        AnalyseVo eventAnalyse =vo.getEventAnalyse();
        eventAnalyse.setUpdateBy(principal.getName());
        ResponseEntity<AnalyseVo> resultVo = client.update(eventAnalyse,id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = vo.getFileIds();
        List<String> docAttDelIds = vo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    //获取单条事件分析研判信息
    public AnalyseVo findEventAnalyseById(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<AnalyseVo> resultVo =client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //删除单条事件分析研判信息(逻辑删除)
    public void deleteEventAnalyse(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<Void> resultVo = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    public List<AnalyseVo> findEventAnalysesAll(Map<String,Object> paramsMap){
        ResponseEntity<List<AnalyseVo>> resultListVo=client.searchAll(convertMap2MultiValueMap(paramsMap));
        return ResponseEntityUtils.achieveResponseEntityBody(resultListVo);
    }

}

