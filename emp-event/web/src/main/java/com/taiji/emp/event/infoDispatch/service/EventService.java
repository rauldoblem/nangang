package com.taiji.emp.event.infoDispatch.service;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.EventEvaProcessClient;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.infoDispatch.feign.AcceptClient;
import com.taiji.emp.event.infoDispatch.feign.EventClient;
import com.taiji.emp.event.infoDispatch.feign.UtilsFeignClient;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.emp.event.msgService.InfoMsgService;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventService extends BaseService {

    @Autowired
    private EventClient client;
    @Autowired
    private AcceptClient acceptClient;
    @Autowired
    private UtilsFeignClient utilsFeignClient;
    @Autowired
    private EventEvaProcessClient processClient;
    @Autowired
    private InfoMsgService infoMsgService;

    //生成新事件(这里适用于 直接在事件管理模块新增事件或外部对接事件)
    public EventVo create(EventVo vo, Principal principal){

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        String account = userVo.getAccount();
        UserProfileVo profileVo = userVo.getProfile();
        vo.setCreateBy(account);
        vo.setUpdateBy(account);
        vo.setCreateOrgId(profileVo.getOrgId());

        String reportOrgId = vo.getReportOrgId(); //报送单位Id
        if(!StringUtils.isEmpty(reportOrgId)){
            OrgVo orgVo = getOrgVoById(reportOrgId);
            Assert.notNull(orgVo,"orgVo不能为null");
            vo.setReportOrgId(reportOrgId);
            vo.setReportOrgName(orgVo.getOrgName()); //报送单位名称
        }else{
            vo.setReportOrgId(profileVo.getOrgId());
            vo.setReportOrgName(profileVo.getOrgName());
        }

        //事件类型
        String eventTypeId = vo.getEventTypeId();//非空
        Assert.hasText(eventTypeId,"eventTypeId 不能为空");
        vo.setEventTypeName(getItemNameById(eventTypeId));
        //事件等级
        String eventGradeId = vo.getEventGradeId(); //可为空
        if(!StringUtils.isEmpty(eventGradeId)){
            vo.setEventGradeName(getItemNameById(eventGradeId));
        }
        //上报方式
        String methodId = vo.getMethodId(); //非空
        Assert.hasText(methodId,"methodId 不能为空");
        vo.setMethodName(getItemNameById(methodId));

        vo.setHBeginTime(utilsFeignClient.now().getBody()); //开始处置时间

        ResponseEntity<EventVo> resultVo = client.createEvent(vo);
        EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //--------------------- 发送系统消息 ------------------------------
        infoMsgService.sendInfoNewMsg(eventVo,profileVo);
        return eventVo;
    }

    //根据id获取单个事件信息
    public EventVo findOne(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<EventVo> resultVo = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //更新事件
    public EventVo update(EventVo vo,String id, Principal principal){

        Assert.hasText(id,"id 不能为空字符串");
        EventVo temp = client.findOne(id).getBody();
        Assert.notNull(temp,"temp不能为null");

        String reportOrgId = vo.getReportOrgId(); //报送单位Id
        if(!StringUtils.isEmpty(reportOrgId)&&reportOrgId.equals(temp.getReportOrgId())){ //报送单位Id与原有记录不一致时
            OrgVo orgVo = getOrgVoById(reportOrgId);
            Assert.notNull(orgVo,"orgVo不能为null");
            vo.setReportOrgId(reportOrgId);
            vo.setReportOrgName(orgVo.getOrgName()); //报送单位名称
        }

        //事件类型
        String eventTypeId = vo.getEventTypeId();//非空
        Assert.hasText(eventTypeId,"eventTypeId 不能为空");
        if(!eventTypeId.equals(temp.getEventTypeId())){//事件类型与原有记录不一致时
            vo.setEventTypeName(getItemNameById(eventTypeId));
        }
        //事件等级
        String eventGradeId = vo.getEventGradeId(); //可为空
        if(!StringUtils.isEmpty(eventGradeId)&&!eventGradeId.equals(temp.getEventGradeId())){//事件等级与原有记录不一致时
            vo.setEventGradeName(getItemNameById(eventGradeId));
        }
        //上报方式
        String methodId = vo.getMethodId(); //非空
        Assert.hasText(methodId,"methodId 不能为空");
        if(!methodId.equals(temp.getMethodId())){ //上报方式与原有记录不一致时
            vo.setMethodName(getItemNameById(methodId));
        }

        vo.setUpdateBy(principal.getName()); //account
        vo.setHandleFlag(EventGlobal.EVENT_HANDLE_DOING);//未处置
        ResponseEntity<EventVo> resultVo = client.updateEvent(vo,id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //根据条件查询事件列表-分页
    public RestPageImpl<EventVo> findPage(EventPageVo pageVo){
        ResponseEntity<RestPageImpl<EventVo>> resultVo = client.search(pageVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //事件处置结束/评估/归档，将事件处置状态置为已结束/已评估/已归档
    //状态:事件处置状态：1处置结束  2 已评估 3 已归档
    public void operateEvent(String eventId,String operateStatus,Principal principal){

        Assert.hasText(eventId,"eventId 不能为空字符串");
        ResponseEntity<EventVo> resultVo = client.findOne(eventId);
        EventVo entityVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        Assert.hasText(operateStatus,"operateStatus 不能为空字符串");

        entityVo.setUpdateBy(principal.getName()); //account

        Boolean isProcessCreate = false;
        if(EventGlobal.EVENT_HANDLE_FINISH.equals(operateStatus)){ //处置结束操作
            entityVo.setHEndTime(utilsFeignClient.now().getBody()); //处置结束时间
            isProcessCreate = true;
        }
        entityVo.setHandleFlag(operateStatus);

        ResponseEntity<EventVo> operateEvent = client.operate(entityVo);
        EventVo result = ResponseEntityUtils.achieveResponseEntityBody(operateEvent);

        //新增处置结束 过程在线节点
        createOperateEventProcess(result,isProcessCreate,principal);

        //事件处置结束，回写信息报告的状态
        if(isProcessCreate){
            acceptClient.finishInfoByEvent(result.getId());
        }
    }

    /**
     * 新增处置结束过程在线节点
     */
    private void createOperateEventProcess(EventVo eventVo,Boolean isProcessCreate,Principal principal){

        if(!isProcessCreate) { //非处置结束操作
            return;
        }

        String account = principal.getName();
        EventEvaProcessVo processVo = new EventEvaProcessVo();
        processVo.setEventId(eventVo.getId());
        processVo.setEventName(eventVo.getEventName());
        processVo.setOperator(account); //当前用户
        processVo.setOperationTime(utilsFeignClient.now().getBody()); //系统当前时间
        processVo.setOperation("该事件处置完成");

        processVo.setFirstNodeCode(ProcessNodeGlobal.DISPOSAL);
        processVo.setFirstNodeName(ProcessNodeGlobal.DISPOSAL_NAME);
        processVo.setSecNodeCode(ProcessNodeGlobal.DISPOSAL_FINISH);
        processVo.setSecNodeName(ProcessNodeGlobal.DISPOSAL_FINISH_NAME);
        processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
        processVo.setCreateBy(account);
        processVo.setUpdateBy(account);

        //保存处置结束过程在线节点
        ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);

    }

    //调整事件等级，与原事件等级比对，更新事件表中的事件等级
    public boolean adjustEventGrade(String eventId,String eventGradeId,Principal principal){

        Assert.hasText(eventId,"eventId 不能为空字符串");
        Assert.hasText(eventGradeId,"eventGradeId 不能为空字符串");
        ResponseEntity<EventVo> resultVo = client.findOne(eventId);
        EventVo entityVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        if(!eventGradeId.equals(entityVo.getEventGradeId())){ //传入的事件等级与原来的不一致
            entityVo.setEventGradeId(eventGradeId);
            entityVo.setEventGradeName(getItemNameById(eventGradeId));
            entityVo.setHandleFlag(EventGlobal.EVENT_HANDLE_DOING);//未处置
            ResponseEntity<EventVo> vo = client.updateEvent(entityVo,eventId);  //调整等级操作
            EventVo result = ResponseEntityUtils.achieveResponseEntityBody(vo);
            //添加过程在线节点 -- 调整等级
            createAdjustEventGradeProcess(result,principal);
            return true;
        }else{
            return false;
        }

    }

    /**
     * 新增调整等级过程在线节点
     */
    private void createAdjustEventGradeProcess(EventVo eventVo,Principal principal){
        String account = principal.getName();
        EventEvaProcessVo processVo = new EventEvaProcessVo();
        processVo.setEventId(eventVo.getId());
        processVo.setEventName(eventVo.getEventName());
        processVo.setOperator(account); //当前用户
        processVo.setOperationTime(utilsFeignClient.now().getBody()); //系统当前时间

        StringBuilder builder = new StringBuilder();
        builder.append("根据研判意见，事件等级调整为").append(eventVo.getEventGradeName());

        processVo.setOperation(builder.toString());
        processVo.setFirstNodeCode(ProcessNodeGlobal.ANALYSE);
        processVo.setFirstNodeName(ProcessNodeGlobal.ANALYSE_NAME);
        processVo.setSecNodeCode(ProcessNodeGlobal.ANALYSE_UPDATE_GRADE);
        processVo.setSecNodeName(ProcessNodeGlobal.ANALYSE_UPDATE_GRADE_NAME);
        processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
        processVo.setCreateBy(account);
        processVo.setUpdateBy(account);

        //保存调整等级过程在线节点
        ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);

    }

    /**
     * 根据条件导出事件word
     * @param response
     * @param eventId
     */
    public void exportToWord(HttpServletResponse response, String eventId, HttpServletRequest request) throws IOException {
        ResponseEntity<EventVo> resultVo = client.findOne(eventId);
        EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        Map<String, Object> content = getReplaceContent(eventVo);
        String templPath = EventGlobal.TEMPLPATH;
        exportWord(templPath,"D:/test","aaa.docx",content,request,response);
    }

    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     * @param templatePath word模板地址
     * @param temDir 生成临时文件存放地址
     * @param fileName 文件名
     * @param params 替换的参数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(templatePath,"模板路径不能为空");
//        Assert.notNull(temDir,"临时文件路径不能为空");
//        Assert.notNull(fileName,"导出文件名不能为空");
//        Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
//        if (!temDir.endsWith("/")){
//            temDir = temDir + File.separator;
//        }
//        File dir = new File(temDir);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
        try {
//            String userAgent = request.getHeader("user-agent").toLowerCase();
//            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
//                fileName = URLEncoder.encode(fileName, "UTF-8");
//            } else {
//                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
//            }
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
//            String tmpPath = temDir + fileName;
//            FileOutputStream fos = new FileOutputStream(tmpPath);
//            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
//            delAllFile(tmpPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除临时文件
    private static void delAllFile(String temDir){
        File dir = new File(temDir);
        if (dir.exists()) {
            dir.delete();
        }
    }

    /**
     * 获取模板中需要替换的文字
     * @param eventVo
     * @return
     */
    private Map<String,Object> getReplaceContent(EventVo eventVo) {
        Map<String, Object> content = new HashMap<String, Object>();
        LocalDateTime reportTime = eventVo.getReportTime();//上报时间
        String reportTimeStr = reportTime.getYear() + "年" + reportTime.getMonthValue() + "月"
                 + reportTime.getDayOfMonth() + "日" + reportTime.getHour() + "时"
                 + reportTime.getMinute() + "分";

        String reportOrgName = eventVo.getReportOrgName();//报送单位
        String methodName = eventVo.getMethodName();//报送方式

        LocalDateTime occurTime = eventVo.getOccurTime();//事发时间
        String occurTimeStr = occurTime.getYear() + "年" + occurTime.getMonthValue() + "月"
                + occurTime.getDayOfMonth() + "日" + occurTime.getHour() + "时"
                + occurTime.getMinute() + "分";

        String position = eventVo.getPosition();//事发地点
        String eventTypeName = eventVo.getEventTypeName();//事件类型名称
        Integer deathNum = eventVo.getDeathNum();//死亡人数
        Integer wondedNum = eventVo.getWondedNum();//受伤人数
        String eventCause = eventVo.getEventCause();//事发原因
        String eventDesc = eventVo.getEventDesc();//详细描述
        String protreatment = eventVo.getProtreatment();//已采取措施
        String request = eventVo.getRequest();//支援请求

        content.put("reportTimeStr",reportTimeStr);
        if (null != reportOrgName) {
            content.put("reportOrgName", reportOrgName);
        }else {
            content.put("reportOrgName", "无");
        }
        if (null != methodName) {
            content.put("methodName",methodName);
        }else {
            content.put("methodName", "无");
        }
        content.put("occurTimeStr",occurTimeStr);
        if (null != position) {
            content.put("position",position);
        }else {
            content.put("position", "无");
        }
        if (null != eventTypeName) {
            content.put("eventTypeName",eventTypeName);
        }else {
            content.put("eventTypeName", "无");
        }
        if (null != deathNum) {
            content.put("deathNum",deathNum);
        }else {
            content.put("deathNum", 0);
        }
        if (null != wondedNum) {
            content.put("wondedNum",wondedNum);
        }else {
            content.put("wondedNum", 0);
        }
        if (null != eventCause) {
            content.put("eventCause",eventCause);
        }else {
            content.put("eventCause", "无");
        }
        if (null != eventDesc) {
            content.put("eventDesc",eventDesc);
        }else {
            content.put("eventDesc", "无");
        }
        if (null != protreatment) {
            content.put("protreatment",protreatment);
        }else {
            content.put("protreatment", "无");
        }
        if (null != request) {
            content.put("request",request);
        }else {
            content.put("request", "无");
        }
        return content;
    }
}
