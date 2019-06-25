package com.taiji.emp.alarm.feign;

import com.taiji.emp.alarm.searchVo.AlertNoticePageSearchVo;
import com.taiji.emp.alarm.vo.AlertNoticeVo;
import com.taiji.emp.alarm.vo.NoticeFeedbackVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 监测预警 -- 预警通知 feign接口服务类(含通知反馈)
 * @author qizhijie-pc
 * @date 2018年12月12日15:01:34
 */
@FeignClient(value = "micro-alarm-alertNotice")
public interface IAlertNoticeRestService {

    /**
     * 新增单条 预警通知信息 AlertNoticeVo
     * @param vo
     * @return ResponseEntity<AlertNoticeVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<AlertNoticeVo> create(@RequestBody AlertNoticeVo vo);

    /**
     * 根据id获取 单条预警通知信息 AlertNoticeVo
     * @param id
     * @return ResponseEntity<AlertNoticeVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<AlertNoticeVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新单条 预警通知信息 AlertNoticeVo
     * @param vo
     * @return ResponseEntity<AlertNoticeVo>
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<AlertNoticeVo> update(@RequestBody AlertNoticeVo vo,@PathVariable(value = "id") String id);

    /**
     * 新增多条 预警通知信息 AlertNoticeVo
     * @param vos
     * @return ResponseEntity<List<AlertNoticeVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<AlertNoticeVo>> createList(@RequestBody List<AlertNoticeVo> vos);

    /**
     * 根据条件查询预警通知信息 AlertNoticeVo  -- 分页
     * 预警通知接收列表、预警已办查看反馈使用
     * @param alertNoticePageVo
     * @return ResponseEntity<List<AlertNoticeVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<AlertNoticeVo>> findPage(@RequestBody AlertNoticePageSearchVo alertNoticePageVo);

    /**
     * 根据条件查询预警通知信息 AlertNoticeVo  -- 不分页
     * @param params
     * @return ResponseEntity<List<AlertNoticeVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<AlertNoticeVo>> findList(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 保存预警通知反馈对象信息 NoticeFeedbackVo
     * @param vo
     * @return ResponseEntity<NoticeFeedbackVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/feedback")
    @ResponseBody
    ResponseEntity<NoticeFeedbackVo> createFeedBack(@RequestBody NoticeFeedbackVo vo);

}
