package com.taiji.emp.nangang.service;

import com.taiji.base.sys.vo.*;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.emp.nangang.common.constant.EventGlobal;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.feign.*;
import com.taiji.emp.nangang.msgService.InfoMsgService;
import com.taiji.emp.nangang.vo.AcceptSaveVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InfoReportService extends BaseService{

    @Autowired
    private AcceptClient client;
    @Autowired
    private DocAttClient docAttClient;
    @Autowired
    private UtilsFeignClient utilsFeignClient;
    @Autowired
    private EventClient eventClient;
    @Autowired
    private EventEvaProcessClient processClient;
    @Autowired
    private InfoMsgService infoMsgService;
    @Autowired
    private DicItemClient dicItemClient;
    @Autowired
    private DicGroupClient dicGroupClient;

    //查询分页信息
    public RestPageImpl<AccDealVo> findPage(InfoPageVo infoPageVo,Principal principal){
        Assert.notNull(infoPageVo,"InfoPageVo 不能为null");
        List<String> buttonTypeList = new ArrayList<>(2);
        buttonTypeList.add(EventGlobal.INFO_DISPATCH_INFO_CREATE);
        buttonTypeList.add(EventGlobal.INFO_DISPATCH_INFO_DONE);
        infoPageVo.setButtonTypeList(buttonTypeList);
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        infoPageVo.setCreateOrgId(profileVo.getOrgId());
        ResponseEntity<RestPageImpl<AccDealVo>> resultVo = client.searchAllInfo(infoPageVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //信息填报:包括初报、续报
    public AcceptVo addInfo(AcceptSaveVo acceptSaveVo, Principal principal){
        AcceptVo acceptVo = acceptSaveVo.getImAccept();

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        String account = userVo.getAccount();
        UserProfileVo profileVo = userVo.getProfile();
        acceptVo.setCreateUserId(userVo.getId()); //创建用户id(临时变量)
        acceptVo.setCreateBy(account);
        acceptVo.setUpdateBy(account);
        acceptVo.setCreateOrgId(profileVo.getOrgId());
        acceptVo.setCreateOrgName(profileVo.getOrgName()); //创建单位名称(临时变量)
        acceptVo.setReporter(account);
        acceptVo.setReporterTel(profileVo.getMobile());

        if(null==acceptVo.getReportTime()){ //如果是空，即需要后台生成
            acceptVo.setReportTime(utilsFeignClient.now().getBody()); //报告时间
        }

        String reportOrgId = acceptVo.getReportOrgId(); //报送单位Id
        if(!StringUtils.isEmpty(reportOrgId)){
            OrgVo orgVo = getOrgVoById(reportOrgId);
            Assert.notNull(orgVo,"orgVo不能为null");
            acceptVo.setReportOrgId(reportOrgId);
            acceptVo.setReportOrgName(orgVo.getOrgName()); //报送单位名称
        }else{
            acceptVo.setReportOrgId(profileVo.getOrgId());
            acceptVo.setReportOrgName(profileVo.getOrgName());
        }

        //事件类型
        String eventTypeId = acceptVo.getEventTypeId();//非空
        Assert.hasText(eventTypeId,"eventTypeId 不能为空");
        acceptVo.setEventTypeName(getItemNameById(eventTypeId));
        //事件等级
        String eventGradeId = acceptVo.getEventGradeId(); //可为空
        if(!StringUtils.isEmpty(eventGradeId)){
            acceptVo.setEventGradeName(getItemNameById(eventGradeId));
        }
        //上报方式
        String methodId = acceptVo.getMethodId(); //非空
        Assert.hasText(methodId,"methodId 不能为空");
        acceptVo.setMethodName(getItemNameById(methodId));
        ResponseEntity<AcceptVo> resultVo =null;
                String id = acceptVo.getId();
        if (StringUtils.isEmpty(id)) {
            resultVo = client.createInfo(acceptVo);
        }else {
            resultVo = client.updateInfo(acceptVo,id);
        }
        AcceptVo result = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String entityId = result.getId();
        List<String> docAttIds = acceptSaveVo.getFileIds();
        List<String> docAttDelIds = acceptSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
        return result;
    }

    //操作：办理信息，包括发送、退回、办结、生成/更新事件
    public void sendInfo(AcceptSaveVo acceptSaveVo,String sendFlag,Principal principal){

        //保存加上报
        //调用保存方法
        AcceptVo result = addInfo(acceptSaveVo,principal);
        dealInfo(acceptSaveVo,sendFlag,principal,result);

    }

    //操作：办理信息，包括发送、退回、办结、生成/更新事件
    public void dealInfo(AcceptSaveVo acceptSaveVo,String sendFlag,Principal principal,AcceptVo result){
        String dealId = result.getDealId();
        //去获取deal信息
        ResponseEntity<AccDealVo> accDealResponse =client.findDealByDealId(dealId);
        AccDealVo accDealVo = ResponseEntityUtils.achieveResponseEntityBody(accDealResponse);
        accDealVo.setImAccept(result);
        accDealVo.setAcceptId(result.getId());

        accDealVo.setDealOrgId(NangangGlobal.DEAL_ORG_ID);
        accDealVo.setDealOrgName(NangangGlobal.DEAL_ORG_NAME);

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        accDealVo.setDealPersonId(userVo.getId()); //办理人Id
        accDealVo.setDealPersonName(userVo.getAccount()); //办理人Name

        OrgVo orgVo = getOrgVoById(accDealVo.getCreateOrgId());
        Assert.notNull(orgVo,"orgVo不能为null");
        accDealVo.setCreateOrgName(orgVo.getOrgName());

        ResponseEntity<AccDealVo> resultVo =client.dealInfo(accDealVo,sendFlag);
        AccDealVo dealVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        /************************************************/
        /*String buttonFlag = "1";
        AccDealVo vo = new AccDealVo();
        vo.setId(dealVo.getId());
        vo.setAcceptId(dealVo.getAcceptId());
        vo.setCreateOrgId(dealVo.getCreateOrgId());
        vo.setCreateOrgName(dealVo.getCreateOrgName());
        vo.setDealOrgId("1");
        vo.setDealOrgName("应急指挥中心");
        vo.setDealPersonId(dealVo.getDealPersonId());
        vo.setDealPersonName(dealVo.getDealPersonName());
        vo.setDealFlag("0");
        vo.setDealTime(utilsFeignClient.now().getBody());
        //发送
        sendInfos(vo,buttonFlag,principal);*/

        //--------------------- 发送系统消息 ------------------------------
        if(EventGlobal.INFO_DISPATCH_BUTTON_SEND.equals(sendFlag)){ //报送分发
            infoMsgService.sendInfoMsg(dealVo);
        }

    }

    //操作：办理信息，包括发送、退回、办结、生成/更新事件
    public void sendInfos(AccDealVo accDealVo,String buttonFlag,Principal principal){

        String acceptId = accDealVo.getAcceptId();
        AcceptVo acceptVo = client.findOne(acceptId).getBody();
        Assert.notNull(acceptVo,"acceptVo 不能为null");
        accDealVo.setImAccept(acceptVo);

        String dealOrgId = accDealVo.getDealOrgId(); //发送时选择的单位
        Assert.hasText(dealOrgId,"dealOrgId 不能为空");

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        accDealVo.setDealPersonId(userVo.getId()); //办理人Id
        accDealVo.setDealPersonName(userVo.getAccount()); //办理人Name
        accDealVo.setCreateOrgId(profileVo.getOrgId());
        OrgVo orgVo = getOrgVoById(accDealVo.getCreateOrgId());
        Assert.notNull(orgVo,"orgVo不能为null");
        accDealVo.setCreateOrgName(orgVo.getOrgName());

        ResponseEntity<AccDealVo> resultVo =client.dealInfo(accDealVo,buttonFlag);
        AccDealVo dealVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        //--------------------- 发送系统消息 ------------------------------
        if(EventGlobal.INFO_DISPATCH_BUTTON_SEND.equals(buttonFlag)){ //报送分发
            infoMsgService.sendInfoMsg(dealVo);
        }

    }

    //修改信息
    public void updateInfo(AcceptSaveVo acceptSaveVo,String id, Principal principal){
        AcceptVo acceptVo = acceptSaveVo.getImAccept();

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        String account = userVo.getAccount();
        UserProfileVo profileVo = userVo.getProfile();
        acceptVo.setCreateUserId(userVo.getId()); //创建用户id(临时变量)
        acceptVo.setUpdateBy(account);
//        acceptVo.setCreateOrgId(profileVo.getOrgId());
//        acceptVo.setCreateOrgName(profileVo.getOrgName()); //创建单位名称(临时变量)

        AcceptVo temp = client.findOne(id).getBody();
        Assert.notNull(temp,"temp不能为null");

        String reportOrgId = acceptVo.getReportOrgId(); //报送单位Id
        if(!StringUtils.isEmpty(reportOrgId)&&reportOrgId.equals(temp.getReportOrgId())){ //报送单位Id与原有记录不一致时
            OrgVo orgVo = getOrgVoById(reportOrgId);
            Assert.notNull(orgVo,"orgVo不能为null");
            acceptVo.setReportOrgId(reportOrgId);
            acceptVo.setReportOrgName(orgVo.getOrgName()); //报送单位名称
        }

        //事件类型
        String eventTypeId = acceptVo.getEventTypeId();//非空
        Assert.hasText(eventTypeId,"eventTypeId 不能为空");
        if(!eventTypeId.equals(temp.getEventTypeId())){//事件类型与原有记录不一致时
            acceptVo.setEventTypeName(getItemNameById(eventTypeId));
        }
        //事件等级
        String eventGradeId = acceptVo.getEventGradeId(); //可为空
        if(!StringUtils.isEmpty(eventGradeId)&&!eventGradeId.equals(temp.getEventGradeId())){//事件等级与原有记录不一致时
            acceptVo.setEventGradeName(getItemNameById(eventGradeId));
        }
        //上报方式
        String methodId = acceptVo.getMethodId(); //非空
        Assert.hasText(methodId,"methodId 不能为空");
        if(!methodId.equals(temp.getMethodId())){ //上报方式与原有记录不一致时
            acceptVo.setMethodName(getItemNameById(methodId));
        }

        ResponseEntity<AcceptVo> resultVo = client.updateInfo(acceptVo,id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        String entityId = id;
        List<String> docAttIds = acceptSaveVo.getFileIds();
        List<String> docAttDelIds = acceptSaveVo.getFileDeleteIds();
        if(!(null!=docAttIds&&docAttIds.size()>0&&null!=docAttDelIds&&docAttDelIds.size()>0)){ //docAttIds和docAttDelIds同时为空时，不调用
            ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
            ResponseEntityUtils.achieveResponseEntityBody(docResult);
        }

    }

    //获取单条上报、接报信息
    public AcceptVo findOne(String id){
        Assert.hasText(id,"id 不能为空");
        ResponseEntity<AcceptVo> resultVo = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }


    /**
     * 添加过程在线节点：生成事件时，生成初报信息节点和生成事件节点；续报更新事件时，生成续报事件节点和更新事件节点
     */
//    private void generateProcess(AccDealVo accDealVo,Boolean isCreateProcess,Principal principal){
//
//        AcceptVo acceptVo = accDealVo.getImAccept();  //报送信息对象
//        String infoEventName = acceptVo.getEventName();  //报送信息 -- 事件名称
//        String infoEventTypeName = acceptVo.getEventTypeName(); //报送信息 -- 事件类型
//        String infoEventGradeName = acceptVo.getEventGradeName(); //报送信息 -- 事件等级
//
//        String eventId = accDealVo.getEventId();
//        Assert.hasText(eventId,"eventId 不能为空");
//        ResponseEntity<EventVo> resultVo = eventClient.findOne(eventId);
//        EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
//        Assert.notNull(eventVo,"eventVo 不能为null");
//        String eventName = eventVo.getEventName();  //事件对象 -- 事件名称
//        String eventGradeName = eventVo.getEventGradeName();  //事件对象 -- 事件等级
//
//        String processType = EventGlobal.PROCESS_SYS_CREATE;  //系统自动创建
//
//
//        EventEvaProcessVo infoProcess = new EventEvaProcessVo();  //新增信息节点
//        infoProcess.setEventId(eventId);
//        infoProcess.setEventName(eventName);
//        infoProcess.setOperator(accDealVo.getDealPersonName()); //信息记录的处理人
//        infoProcess.setOperationTime(acceptVo.getReportTime()); //报送时间
//
//        StringBuilder infoBuilder = new StringBuilder();
//
//        EventEvaProcessVo eventProcess = new EventEvaProcessVo(); //新增事件节点
//        eventProcess.setEventId(eventId);
//        eventProcess.setEventName(eventName);
//        eventProcess.setOperator(accDealVo.getDealPersonName()); //事件操作记录的处理人
//        eventProcess.setOperationTime(utilsFeignClient.now().getBody()); //系统当前时间
//        StringBuilder eventBuilder = new StringBuilder();
//
//        if(isCreateProcess){ //生成事件  -- 初报信息节点和生成事件节点
//            //拼接初报信息操作内容过程
//            infoBuilder.append("初报信息：").append(infoEventName)
//                    .append(",事件类型：").append(infoEventTypeName)
//                    .append(",事件等级：").append(infoEventGradeName);
//            infoProcess.setSecNodeCode(ProcessNodeGlobal.INFO_FIRST_REPORT);
//            infoProcess.setSecNodeName(ProcessNodeGlobal.INFO_FIRST_REPORT_NAME);
//
//            //拼接生成事件操作内容过程
//            eventBuilder.append("生成了").append(eventName)
//                    .append(",事件等级：").append(eventGradeName);
//
//        }else{ //续报事件 -- 续报事件节点和更新事件节点
//            //拼接续报信息操作内容过程
//            infoBuilder.append("续报信息：").append(infoEventName)
//                    .append(",事件类型：").append(infoEventTypeName)
//                    .append(",事件等级：").append(infoEventGradeName);
//            infoProcess.setSecNodeCode(ProcessNodeGlobal.INFO_CON_REPORT);
//            infoProcess.setSecNodeName(ProcessNodeGlobal.INFO_CON_REPORT_NAME);
//
//            //拼接更新事件操作内容过程
//            eventBuilder.append("更新了").append(eventName)
//                    .append(",事件等级：").append(eventGradeName);
//        }
//
//        infoProcess.setOperation(infoBuilder.toString());
//        infoProcess.setFirstNodeCode(ProcessNodeGlobal.INFO);
//        infoProcess.setFirstNodeName(ProcessNodeGlobal.INFO_NAME);
//        infoProcess.setProcessType(processType);
//        infoProcess.setCreateBy(principal.getName());
//        infoProcess.setUpdateBy(principal.getName());
//
//        eventProcess.setOperation(eventBuilder.toString());
//        eventProcess.setFirstNodeCode(ProcessNodeGlobal.INFO);
//        eventProcess.setFirstNodeName(ProcessNodeGlobal.INFO_NAME);
//        eventProcess.setSecNodeCode(ProcessNodeGlobal.INFO_EVENT_GEN);
//        eventProcess.setSecNodeName(ProcessNodeGlobal.INFO_EVENT_GEN_NAME);
//        eventProcess.setProcessType(processType);
//        eventProcess.setCreateBy(principal.getName());
//        eventProcess.setUpdateBy(principal.getName());
//
//        //保存报送信息节点
//        ResponseEntity<EventEvaProcessVo> infoResult = processClient.create(infoProcess);
//        ResponseEntityUtils.achieveResponseEntityBody(infoResult);
//        //保存生成/更新事件节点
//        ResponseEntity<EventEvaProcessVo> eventResult = processClient.create(eventProcess);
//        ResponseEntityUtils.achieveResponseEntityBody(eventResult);
//
//    }

    /**
     * 查询数据字典列表
     * @return
     */
    public List<DicGroupItemVo> findItemsAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<DicGroupItemVo>> result = dicItemClient.findList(super.convertMap2MultiValueMap(params));
        List<DicGroupItemVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 根据dicCode获取字典信息
     * @param dicCode
     * @return
     */
    public DicGroupVo findByDicCode(String dicCode){
        Assert.hasText(dicCode, "dicCode不能为null值或空字符串。");
        MultiValueMap<String,Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.set("dicCode",dicCode);

        ResponseEntity<DicGroupVo> result = dicGroupClient.findOne(valueMap);
        DicGroupVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 根据entityId获取附件集合
     * @param entityId
     * @return
     */
    public List<DocAttVo> findDocAtts(String entityId){
        Assert.hasText(entityId,"id不能为空字符串");
        ResponseEntity<List<DocAttVo>> list = docAttClient.findList(entityId);
        List<DocAttVo> listVos = ResponseEntityUtils.achieveResponseEntityBody(list);
        return listVos;
    }

    //逻辑删除信息
    public void deleteLogic(String id){
        Assert.hasText(id,"id 不能为空");
        ResponseEntity<Void> resultVo = client.deleteLogicInfo(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

}
