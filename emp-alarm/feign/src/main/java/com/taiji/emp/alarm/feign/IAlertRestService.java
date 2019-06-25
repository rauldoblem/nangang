package com.taiji.emp.alarm.feign;

import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.emp.alarm.vo.AlertVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 监测预警 -- 预警信息feign接口服务类
 * @author qizhijie-pc
 * @date 2018年12月12日15:01:34
 */
@FeignClient(value = "micro-alarm-alert")
public interface IAlertRestService {

    /**
     * 新增预警信息 AlertVo
     * @param vo
     * @return ResponseEntity<AlertVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<AlertVo> create(@RequestBody AlertVo vo);

    /**
     * 根据id 获取预警信息AlertVo
     * @param id id不能为空
     * @return ResponseEntity<AlertVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<AlertVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新预警信息AlertVo
     * @param vo,
     * @param id 要更新AlertVo id
     * @return ResponseEntity<AlertVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<AlertVo> update(@RequestBody AlertVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id删除一条预警信息
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据参数获取AlertVo多条记录,分页信息
     * 查询参数 headline(可选),severityId(可选),eventTypeIds(可选),source(可选),sendStartTime,sendEndTime,noticeFlags,orgId
     *          page,size
     *  @param alertPageVo
     *  @return ResponseEntity<RestPageImpl<AlertVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<AlertVo>> findPage(@RequestBody AlertPageSearchVo alertPageVo);

}
