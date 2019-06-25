package com.taiji.emp.event.infoDispatch.feign;

import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  事件信息接口服务类
 * @author qizhijie-pc
 * @date 2018年10月25日18:45:44
 */
@FeignClient(value = "micro-event-eventInfo")
public interface IEventRestService {

    /**
     * 生成新事件
     * @param vo
     * @return ResponseEntity<EventVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/event/create")
    @ResponseBody
    ResponseEntity<EventVo> createEvent(@RequestBody EventVo vo);

    /**
     * 根据id获取单个事件信息
     * @param id
     * @return ResponseEntity<EventVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/event/find/{id}")
    @ResponseBody
    ResponseEntity<EventVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新事件
     * @param vo
     * @param id 事件id
     * @return ResponseEntity<EventVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/event/update/{id}")
    @ResponseBody
    ResponseEntity<EventVo> updateEvent(@RequestBody EventVo vo, @PathVariable(value = "id") String id);

    /**
     *根据条件查询事件列表-分页
     * @param pageVo
     * @return ResponseEntity<RestPageImpl<EventVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<EventVo>> search(@RequestBody EventPageVo pageVo);

    /**
     *根据条件查询事件列表-不分页
     * @param pageVo
     * @return ResponseEntity<List<EventVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<EventVo>> searchAll(@RequestBody EventPageVo pageVo);

    /**
     *事件处置结束/评估/归档，将事件处置状态置为已结束/已评估/已归档状态
     * @param vo
     * @return ResponseEntity<EventVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/event/operate")
    @ResponseBody
    ResponseEntity<EventVo> operate(@RequestBody EventVo vo);
}
