package com.taiji.emp.event.infoDispatch.feign;

import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  信息接报接口服务类
 * @author qizhijie-pc
 * @date 2018年10月23日16:57:14
 */
@FeignClient(value = "micro-event-infoMags")
public interface IAcceptRestService {

    /**
     * 根据参数获取AccDealVo多条记录,分页信息
     * 参数key为 buttonType,eventName,eventTypeIds(数组),eventGradeId,startDate,endDate
     *          page,size
     *  @param infoPageVo
     *  @return ResponseEntity<RestPageImpl<AccDealVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<AccDealVo>> searchInfo(@RequestBody InfoPageVo infoPageVo);

    /**
     * 信息填报(信息暂存)
     * 包括初报、续报，需同时更新IM_ACCEPT_DEAL表
     * @param acceptVo
     * @return ResponseEntity<AcceptVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/info/create")
    @ResponseBody
    ResponseEntity<AcceptVo> createInfo(@RequestBody AcceptVo acceptVo);

    /**
     * 修改信息 -- 只更新主表
     * @param acceptVo
     * @param id 信息id
     * @return ResponseEntity<AcceptVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/info/update/{id}")
    @ResponseBody
    ResponseEntity<AcceptVo> updateInfo(@RequestBody AcceptVo acceptVo, @PathVariable(value = "id") String id);

    /**
     * 获取单条上报、接报信息
     * @param id 信息id
     * @return ResponseEntity<AcceptVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/info/find/{id}")
    @ResponseBody
    ResponseEntity<AcceptVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 逻辑删除信息 -- 只删除主表
     * @param id 信息id
     * @return ResponseEntity<AcceptVo>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/info/update/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogicInfo(@PathVariable(value = "id") String id);

    /**
     * 办理信息，包括发送、退回、办结、生成/更新事件
     * @param accDealVo
     * @param buttonFlag - 发送1、退回2、办结3、生成事件4、更新事件5
     */
    @RequestMapping(method = RequestMethod.POST, path = "/info/deal")
    @ResponseBody
    ResponseEntity<AccDealVo> dealInfo(@RequestBody AccDealVo accDealVo,@RequestParam(value = "buttonFlag") String buttonFlag);

    /**
     * 根据初报Id 获取已生成事件的 eventId
     * @param firstReportId 初报Id
     */
    @RequestMapping(method = RequestMethod.GET, path = "/info/getEventIdByInfoId")
    @ResponseBody
    ResponseEntity<String> getEventIdByInfoId(@RequestParam(value = "firstReportId") String firstReportId);

    /**
     * 查看退回原因
     * @param acceptDealId ---报送处理信息Id
     * @return  ResponseEntity<AccDealVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/info/checkReason/{id}")
    @ResponseBody
    ResponseEntity<AccDealVo> checkReturnReason(@PathVariable(value = "id") String acceptDealId);

    /**
     * 根据eventId查询所有初报续报信息
     * @param eventId 生成事件Id
     */
    @RequestMapping(method = RequestMethod.POST, path = "/info/findInfosByEvent")
    @ResponseBody
    ResponseEntity<List<AcceptVo>> searchInfoByEvent(@RequestParam(value = "eventId") String eventId);

    /**
     * 根据eventId办结所有初报续报记录  -- 当事件处置结束时，调用接口将所有报送记录置为已办结
     * @param eventId 事件Id
     */
    @RequestMapping(method = RequestMethod.POST, path = "/info/finishInfosByEvent")
    @ResponseBody
    ResponseEntity<Void> finishInfoByEvent(@RequestParam(value = "eventId") String eventId);

    @RequestMapping(method = RequestMethod.POST, path = "/find/deal")
    @ResponseBody
    ResponseEntity<AccDealVo> findDealByDealId(@RequestParam(value = "dealId") String dealId);

    @RequestMapping(method = RequestMethod.POST, path = "/find/all/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<AccDealVo>> searchAllInfo(@RequestBody InfoPageVo infoPageVo);
}
