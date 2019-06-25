package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.SchemeVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  应急处置--处置方案 接口服务类
 * @author qizhijie-pc
 * @date 2018年11月1日16:57:29
 */
@FeignClient(value = "micro-event-scheme")
public interface ISchemeRestService {

    /**
     * 新增处置方案SchemeVo
     * @param vo
     * @return ResponseEntity<SchemeVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<SchemeVo> create(@RequestBody SchemeVo vo);

    /**
     * 根据事件id 获取处置方案SchemeVo
     * @param eventId 不能为空
     * @return ResponseEntity<SchemeVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findByEventId")
    @ResponseBody
    ResponseEntity<SchemeVo> findByEventId(@RequestParam(value = "eventId") String eventId);

    /**
     * 更新处置方案SchemeVo
     * @param vo,
     * @param id 要更新SchemeVo id
     * @return ResponseEntity<SchemeVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<SchemeVo> update(@RequestBody SchemeVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据schemeId 获取事件信息EventVo
     * @param schemeId 不能为空
     * @return ResponseEntity<EventVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findEventBySchemeId")
    @ResponseBody
    ResponseEntity<EventVo> findEventBySchemeId(@RequestParam(value = "schemeId") String schemeId);

    @RequestMapping(method = RequestMethod.GET, path = "/find/one/{id}")
    @ResponseBody
    ResponseEntity<SchemeVo> findOne(@PathVariable(value = "id") String id);
}
