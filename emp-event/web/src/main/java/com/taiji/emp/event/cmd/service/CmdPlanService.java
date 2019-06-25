package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.CmdPlanClient;
import com.taiji.emp.event.cmd.feign.SchemeClient;
import com.taiji.emp.event.cmd.vo.CmdPlanVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.infoDispatch.feign.UtilsFeignClient;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CmdPlanService extends BaseService {

    @Autowired
    CmdPlanClient client;
    @Autowired
    private UtilsFeignClient utilsFeignClient;
    @Autowired
    private EventEvaProcessClient processClient;
    @Autowired
    SchemeClient schemeClient;

    //新增关联预案
    public List<CmdPlanVo> addEcPlans(List<CmdPlanVo> vos, Principal principal){
        String account = principal.getName();
        if(null!=vos&&vos.size()>0){
            List<CmdPlanVo> list = new ArrayList<>();
            String planNames = "";
            String schemeId = "";
            for(CmdPlanVo tempVo : vos){
                tempVo.setCreateBy(account);
                tempVo.setUpdateBy(account);
                list.add(tempVo);
                planNames+=tempVo.getPlanName()+",";
                schemeId = tempVo.getSchemeId();
            }
            ResponseEntity<List<CmdPlanVo>> result = client.createList(list);
            List<CmdPlanVo> resultList = ResponseEntityUtils.achieveResponseEntityBody(result);

            //创建启动预案过程在线节点
            createCmdPlansProcess(planNames.substring(0,planNames.length()-1),schemeId,principal);

            return resultList;
        }else{
            return null;
        }

    }

    /**
     * 创建启动预案过程在线节点
     */
    private void createCmdPlansProcess(String planNames,String schemeId, Principal principal){
        String account = principal.getName();
        EventEvaProcessVo processVo = new EventEvaProcessVo();

        //通过方案id获取事件信息
        ResponseEntity<EventVo> eventResultVo = schemeClient.findEventBySchemeId(schemeId);
        EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(eventResultVo);

        String eventId = eventVo.getId();
        String eventName = eventVo.getEventName();

        processVo.setEventId(eventId);
        processVo.setEventName(eventName);
        processVo.setOperator(account); //当前用户
        processVo.setOperationTime(utilsFeignClient.now().getBody()); //系统当前时间

        StringBuilder builder = new StringBuilder();
        builder.append("启动了").append(planNames)
                .append(",生成").append(eventName).append("处置方案");

        processVo.setOperation(builder.toString());
        processVo.setFirstNodeCode(ProcessNodeGlobal.CMDPLAN);
        processVo.setFirstNodeName(ProcessNodeGlobal.CMDPLAN_NAME);
        processVo.setSecNodeCode(ProcessNodeGlobal.CMDPLAN_START);
        processVo.setSecNodeName(ProcessNodeGlobal.CMDPLAN_START_NAME);
        processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
        processVo.setCreateBy(account);
        processVo.setUpdateBy(account);

        //保存启动预案过程在线节点
        ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的应急预案信息
    public List<CmdPlanVo> findPlansAll(Map<String,Object> map){
        ResponseEntity<List<CmdPlanVo>> result = client.searchAll(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

}
