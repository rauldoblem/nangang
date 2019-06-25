package com.taiji.emp.event.infoDispatch.controller;

import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.entity.AcceptDeal;
import com.taiji.emp.event.infoDispatch.feign.IAcceptRestService;
import com.taiji.emp.event.infoDispatch.mapper.AcceptDealMapper;
import com.taiji.emp.event.infoDispatch.mapper.AcceptMapper;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.emp.event.infoDispatch.service.AcceptDealService;
import com.taiji.emp.event.infoDispatch.service.AcceptService;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/infoMags")
public class AcceptController extends BaseController implements IAcceptRestService{

    @Autowired
    AcceptDealService dealService;
    @Autowired
    AcceptDealMapper dealMapper;
    @Autowired
    AcceptService acceptService;
    @Autowired
    AcceptMapper acceptMapper;



    /**
     * 根据参数获取AccDealVo多条记录,分页信息
     * 参数key为 buttonType,eventName,eventTypeIds(数组),eventGradeId,startDate,endDate
     * page,size
     *
     * @param infoPageVo
     * @return ResponseEntity<RestPageImpl < AccDealVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<AccDealVo>> searchInfo(
            @NotNull(message = "infoPageVo 不能为null")
            @RequestBody InfoPageVo infoPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",infoPageVo.getPage());
        map.add("size",infoPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<AcceptDeal> dealResult = dealService.findPage(infoPageVo,page);
        RestPageImpl<AccDealVo> accDealVos = dealMapper.entityPageToVoPage(dealResult,page);

        return new ResponseEntity<>(accDealVos, HttpStatus.OK);
    }

    /**
     * 信息填报
     * 包括初报、续报，需同时更新IM_ACCEPT_DEAL表
     * @param acceptVo
     * @return ResponseEntity<AcceptVo>
     */
    @Override
    public ResponseEntity<AcceptVo> createInfo(
            @Validated
            @NotNull(message = "acceptVo 不能为空")
            @RequestBody AcceptVo acceptVo) {
        Accept entity = acceptMapper.voToEntity(acceptVo);
        Accept result = acceptService.createInfo(entity);
        AcceptVo resultVo = acceptMapper.entityToVo(result);

        AcceptDeal dealByDeal = dealService.findDealByDealId(resultVo.getDealId());
        AccDealVo accDealVo = dealMapper.entityToVo(dealByDeal);
        resultVo.setDeal(accDealVo);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }


    /**
     * 修改信息 -- 只更新主表
     *
     * @param acceptVo
     * @param id       信息id
     * @return ResponseEntity<AcceptVo>
     */
    @Override
    public ResponseEntity<AcceptVo> updateInfo(
            @Validated
            @NotNull(message = "acceptVo 不能为空")
            @RequestBody AcceptVo acceptVo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Accept entity = acceptMapper.voToEntity(acceptVo);
        Accept result = acceptService.editSave(entity,id);
        AcceptVo resultVo = acceptMapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 获取单条上报、接报信息
     * @param id 信息id
     * @return ResponseEntity<AcceptVo>
     */
    @Override
    public ResponseEntity<AcceptVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        Accept result = acceptService.findOne(id);
        AcceptVo resultVo = acceptMapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 逻辑删除信息 -- 只删除主表
     *
     * @param id 信息id
     * @return ResponseEntity<AcceptVo>
     */
    @Override
    public ResponseEntity<Void> deleteLogicInfo(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        acceptService.deleteLogic(id, DelFlagEnum.DELETE);
//        return ResponseEntity.ok().build();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 办理信息，包括发送、退回、办结、生成/更新事件
     *
     * @param accDealVo  web需要从 accept id --->firstReportId --->eventId
     * @param buttonFlag - 发送1、退回2、办结3、生成事件4、更新事件5
     */
    @Override
    public ResponseEntity<AccDealVo> dealInfo(
            @Validated
            @NotNull(message = "accDealVo 不能为空")
            @RequestBody AccDealVo accDealVo,
            @NotEmpty(message = "buttonFlag 不能为空字符串")
            @RequestParam(value = "buttonFlag") String buttonFlag) {
        AcceptDeal entity = dealMapper.voToEntity(accDealVo);
        if(EventGlobal.buttonTypeSet.contains(buttonFlag)){
            AcceptDeal result = dealService.operateInfo(entity,buttonFlag);
            AccDealVo resultVo = dealMapper.entityToVo(result);
            return new ResponseEntity<>(resultVo,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //报400，参数错误
        }

    }

    /**
     * 根据初报Id 获取已生成事件的 eventId
     * @param firstReportId 初报Id
     */
    @Override
    public ResponseEntity<String> getEventIdByInfoId(
            @NotEmpty(message = "firstReportId 不能为空字符串")
            @RequestParam(value = "firstReportId") String firstReportId){
        String eventId = dealService.getEventIdByFirstReportId(firstReportId);
        return new ResponseEntity<>(eventId,HttpStatus.OK);
    }

    /**
     * 查看退回原因
     * @param acceptDealId ---报送处理信息Id
     * @return ResponseEntity<AccDealVo>
     */
    @Override
    public ResponseEntity<AccDealVo> checkReturnReason(
            @NotEmpty(message = "acceptId 不能为空字符串")
            @PathVariable(value = "id") String acceptDealId) {
        AcceptDeal entity = dealService.checkReturnReason(acceptDealId);
        AccDealVo resultVo = dealMapper.entityToVo(entity);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据eventId查询所有初报续报信息
     * @param eventId 生成事件Id
     */
    @Override
    public ResponseEntity<List<AcceptVo>> searchInfoByEvent(
            @NotEmpty(message = "eventId 不能为空字符串")
            @RequestParam(value = "eventId") String eventId) {
        List<Accept> resultList = dealService.findListByEventId(eventId);
        List<AcceptVo> resultListVo = acceptMapper.entityListToVoList(resultList);
        return new ResponseEntity<>(resultListVo,HttpStatus.OK);
    }

    /**
     * 根据eventId办结所有初报续报记录  -- 当事件处置结束时，调用接口将所有报送记录置为已办结
     *
     * @param eventId 事件Id
     */
    @Override
    public ResponseEntity<Void> finishInfoByEvent(
            @NotEmpty(message = "eventId 不能为空字符串")
            @RequestParam(value = "eventId") String eventId) {
        dealService.finishInfosByEventId(eventId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AccDealVo> findDealByDealId(
            @NotEmpty(message = "dealId 不能为空字符串")
            @RequestParam(value = "dealId") String dealId) {
        AcceptDeal entity = dealService.findDealByDealId(dealId);
        AccDealVo accDealVo = dealMapper.entityToVo(entity);
        return new ResponseEntity<>(accDealVo,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestPageImpl<AccDealVo>> searchAllInfo(
            @NotNull(message = "infoPageVo 不能为null")
            @RequestBody InfoPageVo infoPageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",infoPageVo.getPage());
        map.add("size",infoPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<AcceptDeal> dealResult = dealService.findAllPage(infoPageVo,page);
        RestPageImpl<AccDealVo> accDealVos = dealMapper.entityPageToVoPage(dealResult,page);

        return new ResponseEntity<>(accDealVos, HttpStatus.OK);
    }
}
