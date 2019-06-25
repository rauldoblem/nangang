package com.taiji.emp.event.infoDispatch.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.infoDispatch.feign.AcceptClient;
import com.taiji.emp.event.infoDispatch.feign.DocAttClient;
import com.taiji.emp.event.infoDispatch.feign.EventClient;
import com.taiji.emp.event.infoDispatch.feign.UtilsFeignClient;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.emp.event.infoDispatch.vo.*;
import com.taiji.emp.event.msgService.InfoMsgService;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.List;

@Service
public class AcceptService extends BaseService{

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

    //查询分页信息
    public RestPageImpl<AccDealVo> findPage(InfoPageVo infoPageVo){
        Assert.notNull(infoPageVo,"InfoPageVo 不能为null");
        ResponseEntity<RestPageImpl<AccDealVo>> resultVo = client.searchInfo(infoPageVo);
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

        ResponseEntity<AcceptVo> resultVo = client.createInfo(acceptVo);
        AcceptVo result = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String entityId = result.getId();
        List<String> docAttIds = acceptSaveVo.getFileIds();
        List<String> docAttDelIds = acceptSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
        return result;
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

    //逻辑删除信息
    public void deleteLogic(String id){
        Assert.hasText(id,"id 不能为空");
        ResponseEntity<Void> resultVo = client.deleteLogicInfo(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //操作：办理信息，包括发送、退回、办结、生成/更新事件
    public void dealInfo(AccDealVo accDealVo,String buttonFlag,Principal principal){

        String acceptId = accDealVo.getAcceptId();
        AcceptVo acceptVo = client.findOne(acceptId).getBody();
        Assert.notNull(acceptVo,"acceptVo 不能为null");
        accDealVo.setImAccept(acceptVo);

        String dealOrgId = accDealVo.getDealOrgId(); //发送时选择的单位
        Assert.hasText(dealOrgId,"dealOrgId 不能为空");

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        accDealVo.setDealPersonId(userVo.getId()); //办理人Id
        accDealVo.setDealPersonName(userVo.getAccount()); //办理人Name

        OrgVo orgVo = getOrgVoById(accDealVo.getCreateOrgId());
        Assert.notNull(orgVo,"orgVo不能为null");
        accDealVo.setCreateOrgName(orgVo.getOrgName());

        Boolean isCreateProcess = null;

        if(EventGlobal.INFO_DISPATCH_BUTTON_GENERATE_EVENT.equals(buttonFlag)){ //生成事件(初报生成事件)
//            //调用生成事件过程 -- start
//            EventVo eventVo =changeInfoToEvent(accDealVo,"create",userVo);
//            EventVo resultVo = eventClient.createEvent(eventVo).getBody();//调用新建事件接口
//            //调用生成事件过程 -- end
//            String eventId = resultVo.getId(); //从返回的event对象中获取Id
//            accDealVo.setEventId(eventId);
            accDealVo.setDealFlag(EventGlobal.INFO_DISPATCH_GENERATE_EVENT); //生成事件
            isCreateProcess = true;

        }else if(EventGlobal.INFO_DISPATCH_BUTTON_UPDATE_EVENT.equals(buttonFlag)){ //更新事件
//            //保证初报Id不能为空
//            String firstReportId = acceptVo.getFirstReportId();
//            Assert.hasText(firstReportId,"firstReportId不能为空");
            accDealVo.setDealFlag(EventGlobal.INFO_DISPATCH_GENERATE_EVENT); //生成事件
            isCreateProcess = false;
        }

        ResponseEntity<AccDealVo> resultVo =client.dealInfo(accDealVo,buttonFlag);
        AccDealVo dealVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        //注释原因：动作拆分，前台调用
        //更新事件操作需要调用事件更新接口
//        if(EventGlobal.INFO_DISPATCH_BUTTON_UPDATE_EVENT.equals(buttonFlag)){
//            EventVo eventVo =changeInfoToEvent(dealVo,"update",userVo);
//            //调用事件更新接口
//            ResponseEntity<EventVo> updateResultVo = eventClient.updateEvent(eventVo,eventVo.getId());
//            ResponseEntityUtils.achieveResponseEntityBody(updateResultVo);
//        }
        //--------------------- 生成过程在线节点 ------------------------------
        if(null!=isCreateProcess){ //生成节点
            generateProcess(dealVo,isCreateProcess,principal);
        }

        //--------------------- 发送系统消息 ------------------------------
        if(EventGlobal.INFO_DISPATCH_BUTTON_SEND.equals(buttonFlag)){ //报送分发
            infoMsgService.sendInfoMsg(dealVo);
        }else if(EventGlobal.INFO_DISPATCH_BUTTON_RETURN.equals(buttonFlag)){ //退回
            infoMsgService.returnInfoMsg(dealVo);
        }

    }

    /**
     * 添加过程在线节点：生成事件时，生成初报信息节点和生成事件节点；续报更新事件时，生成续报事件节点和更新事件节点
     */
    private void generateProcess(AccDealVo accDealVo,Boolean isCreateProcess,Principal principal){

        AcceptVo acceptVo = accDealVo.getImAccept();  //报送信息对象
        String infoEventName = acceptVo.getEventName();  //报送信息 -- 事件名称
        String infoEventTypeName = acceptVo.getEventTypeName(); //报送信息 -- 事件类型
        String infoEventGradeName = acceptVo.getEventGradeName(); //报送信息 -- 事件等级

        String eventId = accDealVo.getEventId();
        Assert.hasText(eventId,"eventId 不能为空");
        ResponseEntity<EventVo> resultVo = eventClient.findOne(eventId);
        EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        Assert.notNull(eventVo,"eventVo 不能为null");
        String eventName = eventVo.getEventName();  //事件对象 -- 事件名称
        String eventGradeName = eventVo.getEventGradeName();  //事件对象 -- 事件等级

        String processType = EventGlobal.PROCESS_SYS_CREATE;  //系统自动创建


        EventEvaProcessVo infoProcess = new EventEvaProcessVo();  //新增信息节点
        infoProcess.setEventId(eventId);
        infoProcess.setEventName(eventName);
        infoProcess.setOperator(accDealVo.getDealPersonName()); //信息记录的处理人
        infoProcess.setOperationTime(acceptVo.getReportTime()); //报送时间

        StringBuilder infoBuilder = new StringBuilder();

        EventEvaProcessVo eventProcess = new EventEvaProcessVo(); //新增事件节点
        eventProcess.setEventId(eventId);
        eventProcess.setEventName(eventName);
        eventProcess.setOperator(accDealVo.getDealPersonName()); //事件操作记录的处理人
        eventProcess.setOperationTime(utilsFeignClient.now().getBody()); //系统当前时间
        StringBuilder eventBuilder = new StringBuilder();

        if(isCreateProcess){ //生成事件  -- 初报信息节点和生成事件节点
            //拼接初报信息操作内容过程
            infoBuilder.append("初报信息：").append(infoEventName)
                    .append(",事件类型：").append(infoEventTypeName)
                    .append(",事件等级：").append(infoEventGradeName);
            infoProcess.setSecNodeCode(ProcessNodeGlobal.INFO_FIRST_REPORT);
            infoProcess.setSecNodeName(ProcessNodeGlobal.INFO_FIRST_REPORT_NAME);

            //拼接生成事件操作内容过程
            eventBuilder.append("生成了").append(eventName)
                    .append(",事件等级：").append(eventGradeName);

        }else{ //续报事件 -- 续报事件节点和更新事件节点
            //拼接续报信息操作内容过程
            infoBuilder.append("续报信息：").append(infoEventName)
                    .append(",事件类型：").append(infoEventTypeName)
                    .append(",事件等级：").append(infoEventGradeName);
            infoProcess.setSecNodeCode(ProcessNodeGlobal.INFO_CON_REPORT);
            infoProcess.setSecNodeName(ProcessNodeGlobal.INFO_CON_REPORT_NAME);

            //拼接更新事件操作内容过程
            eventBuilder.append("更新了").append(eventName)
                    .append(",事件等级：").append(eventGradeName);
        }

        infoProcess.setOperation(infoBuilder.toString());
        infoProcess.setFirstNodeCode(ProcessNodeGlobal.INFO);
        infoProcess.setFirstNodeName(ProcessNodeGlobal.INFO_NAME);
        infoProcess.setProcessType(processType);
        infoProcess.setCreateBy(principal.getName());
        infoProcess.setUpdateBy(principal.getName());

        eventProcess.setOperation(eventBuilder.toString());
        eventProcess.setFirstNodeCode(ProcessNodeGlobal.INFO);
        eventProcess.setFirstNodeName(ProcessNodeGlobal.INFO_NAME);
        eventProcess.setSecNodeCode(ProcessNodeGlobal.INFO_EVENT_GEN);
        eventProcess.setSecNodeName(ProcessNodeGlobal.INFO_EVENT_GEN_NAME);
        eventProcess.setProcessType(processType);
        eventProcess.setCreateBy(principal.getName());
        eventProcess.setUpdateBy(principal.getName());

        //保存报送信息节点
        ResponseEntity<EventEvaProcessVo> infoResult = processClient.create(infoProcess);
        ResponseEntityUtils.achieveResponseEntityBody(infoResult);
        //保存生成/更新事件节点
        ResponseEntity<EventEvaProcessVo> eventResult = processClient.create(eventProcess);
        ResponseEntityUtils.achieveResponseEntityBody(eventResult);

    }

    //将报送信息转化成事件信息（暂未用到）
    //flag: create(新增事件)，edit(更新事件)
    public EventVo changeInfoToEvent(AccDealVo accDealVo, String buttonFlag,UserVo userVo){
        AcceptVo acceptVo = accDealVo.getImAccept();
        EventVo eventVo = new EventVo();
        eventVo.setEventName(acceptVo.getEventName());
        eventVo.setPosition(acceptVo.getPosition());
        eventVo.setLonAndLat(acceptVo.getLonAndLat());
        eventVo.setOccurTime(acceptVo.getOccurTime());
        eventVo.setEventTypeId(acceptVo.getEventTypeId());
        eventVo.setEventTypeName(acceptVo.getEventTypeName());
        eventVo.setEventGradeId(acceptVo.getEventGradeId());
        eventVo.setEventGradeName(acceptVo.getEventGradeName());
        eventVo.setReportOrgId(acceptVo.getReportOrgId());
        eventVo.setReportOrgName(acceptVo.getReportOrgName());
        eventVo.setReporter(acceptVo.getReporter());
        eventVo.setReporterTel(acceptVo.getReporterTel());
        eventVo.setMethodId(acceptVo.getMethodId());
        eventVo.setMethodName(acceptVo.getMethodName());
        eventVo.setReportTime(acceptVo.getReportTime());
        eventVo.setEventCause(acceptVo.getEventCause());
        eventVo.setEventDesc(acceptVo.getEventDesc());
        eventVo.setProtreatment(acceptVo.getProtreatment());
        eventVo.setDeathNum(acceptVo.getDeathNum());
        eventVo.setWondedNum(acceptVo.getWondedNum());
        eventVo.setRequest(acceptVo.getRequest());

        eventVo.setUpdateBy(userVo.getAccount());

        if("create".equals(buttonFlag)){
            eventVo.setCreateOrgId(userVo.getProfile().getOrgId());
            eventVo.setCreateBy(userVo.getAccount());
            eventVo.setHBeginTime(utilsFeignClient.now().getBody()); //赋值开始处置时间(生成事件时赋值)
        }

        if("update".equals(buttonFlag)){
            eventVo.setId(accDealVo.getEventId());
        }

        return eventVo;
    }

    //根据初报Id 获取已生成事件的 eventId
    public String getEventIdByInfoId(String firstReportId){
        Assert.hasText(firstReportId,"acceptDealId 不能为空");
        ResponseEntity<String> result = client.getEventIdByInfoId(firstReportId);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //查看退回原因
    public AccDealVo checkReturnReason(String acceptDealId){
        Assert.hasText(acceptDealId,"acceptDealId 不能为空");
        ResponseEntity<AccDealVo> resultVo =client.checkReturnReason(acceptDealId);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //根据eventId查询所有初报续报信息
    public List<AcceptVo> findAccListByEventId(String eventId){
        Assert.hasText(eventId,"eventId 不能为空");
        ResponseEntity<List<AcceptVo>> resultVoList = client.searchInfoByEvent(eventId);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVoList);
    }

}
