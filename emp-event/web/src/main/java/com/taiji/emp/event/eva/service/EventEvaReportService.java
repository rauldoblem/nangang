package com.taiji.emp.event.eva.service;

import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.common.constant.ProcessNodeGlobal;
import com.taiji.emp.event.eva.feign.*;
import com.taiji.emp.event.eva.vo.*;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.event.infoDispatch.feign.EventClient;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class EventEvaReportService extends BaseService {

    @Autowired
    EventClient eventClient;

    @Autowired
    EventEvaReportClient client;

    @Autowired
    EventEvaScoreClient eventEvaScoreClient;

    @Autowired
    EventEvaItemClient eventEvaItemClient;

    @Autowired
    private EventEvaProcessClient processClient;

    private DocAttClient docAttClient;

    /**
     * 新增事件评估报告
     * @param saveVo
     * @param principal
     */
    public void create(EventEvaReportSaveVo saveVo, Principal principal) {
        EventEvaReportDataVo vo = saveVo.getData().getEventEvaReport();
        Assert.notNull(vo,"vo不能为空");
        String principalName = principal.getName();
        //给对象set属性
        vo.setCreateBy(principalName);
        vo.setUpdateBy(principalName);
        List<EventEvaScoreVo> evaScoreVoList = vo.getEvaScore();
        for (EventEvaScoreVo scoreVo : evaScoreVoList){
            EventEvaItemVo eventEvaItem = scoreVo.getEventEvaItem();
            eventEvaItem.setCreateBy(principalName);
            eventEvaItem.setUpdateBy(principalName);
        }
        //更新事件处置状态
        updateEventStatus(vo,saveVo);
        EventEvaReportVo dataVo = saveVo.getData();
        //新增评估报告
        ResponseEntity<EventEvaReportVo> resultVo = client.create(dataVo);
        EventEvaReportVo reportVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String evaReportId = reportVo.getEventEvaReport().getId(); //评估报告ID
        List<EventEvaScoreVo> evaScoreList = vo.getEvaScore();
        String eventTypeId = evaScoreList.get(0).getEventEvaItem().getEventTypeId();
        ResponseEntity<List<EventEvaItemVo>> listByEventTypeId = eventEvaItemClient.findListByEventTypeId(eventTypeId);
        List<EventEvaItemVo> evaItemVos = ResponseEntityUtils.achieveResponseEntityBody(listByEventTypeId);
        for (int i = 0;i < evaScoreList.size();i++){
            EventEvaScoreVo scoreVo = evaScoreList.get(i);
            EventEvaItemVo eventEvaItemVo = evaItemVos.get(i);
            scoreVo.setReportId(evaReportId);
            scoreVo.setEventEvaItem(eventEvaItemVo);
        }
//        for (EventEvaScoreVo scoreVo : evaScoreList){
//            EventEvaItemVo evaItemVo = scoreVo.getEventEvaItem();
//            scoreVo.setReportId(evaReportId);
//            //新增评估项目
//            ResponseEntity<EventEvaItemVo> resultItemVo = eventEvaItemClient.create(evaItemVo);
//            EventEvaItemVo ItemVo = ResponseEntityUtils.achieveResponseEntityBody(resultItemVo);
//            scoreVo.setEventEvaItem(ItemVo);
//            if (null == evaItemVo.getId()) {
//            }
//        }
        //新增关联表
        eventEvaScoreClient.create(evaScoreList);

        //附件上传
        List<String> fileIds = dataVo.getFileIds();
        List<String> fileDeleteIds = dataVo.getFileDeleteIds();
        String entityId = resultVo.getBody().getEventEvaReport().getId();
        fileSave(entityId,fileIds,fileDeleteIds);

        String saveFlag = saveVo.getSaveFlag();
        if (saveFlag.equals("1")){
            //添加节点
            addEventEvaReportNode(vo,principalName);
        }
    }

    /**
     * 添加节点
     * @param vo
     */
    private void addEventEvaReportNode(EventEvaReportDataVo vo,String principalName) {
        if (null != vo){
            String eventId = vo.getEventId();
            ResponseEntity<EventVo> voResponseEntity = eventClient.findOne(eventId);
            EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(voResponseEntity);
            EventEvaProcessVo processVo = new EventEvaProcessVo();
            processVo.setEventId(eventId);
            processVo.setEventName(eventVo.getEventName());
            StringBuilder builder = new StringBuilder();
            String resultGrade = vo.getResultGrade();
            builder.append("完成事件评估，评估结果为：")
                    .append(resultGrade);
            processVo.setOperation(builder.toString());
            processVo.setOperator(principalName);//???
            processVo.setOperationTime(DateUtil.strToLocalDateTime(vo.getEvaluateTime()+" 00:00:00"));

            processVo.setFirstNodeCode(ProcessNodeGlobal.EVA);
            processVo.setFirstNodeName(ProcessNodeGlobal.EVA_NAME);
            processVo.setSecNodeCode(ProcessNodeGlobal.EVA_GEN_REPORT);
            processVo.setSecNodeName(ProcessNodeGlobal.EVA_GEN_REPORT_NAME);
            processVo.setProcessType(EventGlobal.PROCESS_SYS_CREATE);  //系统自动创建
            processVo.setCreateBy(principalName);
            processVo.setUpdateBy(principalName);

            //添加节点
            ResponseEntity<EventEvaProcessVo> result = processClient.create(processVo);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }
    }

    /**
     * 获取单条 事件评估报告信息（含各评估细项分数）
     * @param eventId
     * @return
     */
    public EventEvaReportVo findOne(String eventId) {
        Assert.hasText(eventId,"eventId不能为空字符串");
        ResponseEntity<EventEvaReportVo> resultVo = client.findOne(eventId);
        EventEvaReportVo reportVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if (null != reportVo) {
            String reportId = reportVo.getEventEvaReport().getId();
            ResponseEntity<List<EventEvaScoreVo>> scoreVo = eventEvaScoreClient.findByReportId(reportId);
            List<EventEvaScoreVo> scoreVos = ResponseEntityUtils.achieveResponseEntityBody(scoreVo);
            EventEvaReportDataVo eventEvaReport = reportVo.getEventEvaReport();
            eventEvaReport.setEvaScore(scoreVos);
            reportVo.setEventEvaReport(eventEvaReport);
            //获取上传附件信息
            /*ResponseEntity<List<DocAttVo>> clientList = docAttClient.findList(reportId);
            List<DocAttVo> voList = ResponseEntityUtils.achieveResponseEntityBody(clientList);
            List<String> fileIds = new ArrayList<>();
            if (!CollectionUtils.isEmpty(voList)){
                for (DocAttVo docAttVo : voList){
                    fileIds.add(docAttVo.getYwid());
                }
            }
            reportVo.setFileIds(fileIds);*/
        }
//        EventEvaReportVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return reportVo;
    }

    /**
     * 修改事件评估报告信息（含各评估细项分数），若不是暂存即直接评估完成需要更新事件处置状态
     * @param saveVo
     * @param principal
     */
    public void update(EventEvaReportSaveVo saveVo, Principal principal) {
        EventEvaReportDataVo vo = saveVo.getData().getEventEvaReport();
        Assert.notNull(vo,"vo不能为空");
        String principalName = principal.getName();
        vo.setUpdateBy(principalName);
        List<EventEvaScoreVo> evaScoreVoList = vo.getEvaScore();
        for (EventEvaScoreVo scoreVo : evaScoreVoList){
            EventEvaItemVo eventEvaItem = scoreVo.getEventEvaItem();
            eventEvaItem.setUpdateBy(principalName);
        }
        //更新事件处置状态
        updateEventStatus(vo,saveVo);
        EventEvaReportVo reportVo = saveVo.getData();
        //更新评估报告
        ResponseEntity<EventEvaReportVo> resultVo = client.update(reportVo);

        String reportId = reportVo.getEventEvaReport().getId();
        List<EventEvaScoreVo> evaScoreList = vo.getEvaScore();
        for (EventEvaScoreVo scoreVo : evaScoreList){
            scoreVo.setReportId(reportId);
            EventEvaItemVo evaItemVo = scoreVo.getEventEvaItem();
            eventEvaItemClient.update(evaItemVo,evaItemVo.getId());
        }
        //更新评估项目和关联表
        eventEvaScoreClient.update(evaScoreList);

        //附件上传
        List<String> fileIds = saveVo.getData().getFileIds();
        List<String> fileDeleteIds = saveVo.getData().getFileDeleteIds();
        String entityId = resultVo.getBody().getEventEvaReport().getId();
        fileSave(entityId,fileIds,fileDeleteIds);
    }

    /**
     * 更新事件处置状态
     * @param vo
     * @param saveVo
     */
    private void updateEventStatus(EventEvaReportDataVo vo,EventEvaReportSaveVo saveVo){
        String saveFlag = saveVo.getSaveFlag();
        Assert.hasText(saveFlag,"saveFlag不能为空");
        String eventId = vo.getEventId();
        Assert.hasText(eventId,"eventId不能为空");
        if (EventGlobal.SAVE_FLAG.equals(saveFlag)){
            //评估完成
            ResponseEntity<EventVo> voResponseEntity = eventClient.findOne(eventId);
            EventVo eventVo = ResponseEntityUtils.achieveResponseEntityBody(voResponseEntity);
            //已评估
            eventVo.setHandleFlag(EventGlobal.EVENT_HANDLE_EVA);
            //修改事件处置状态
            eventClient.updateEvent(eventVo,eventId);
        }
    }

}
