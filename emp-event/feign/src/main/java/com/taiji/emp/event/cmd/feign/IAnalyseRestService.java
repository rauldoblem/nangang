package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.AnalyseVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  应急处置--分析研判 接口服务类
 * @author qizhijie-pc
 * @date 2018年10月30日17:34:32
 */
@FeignClient(value = "micro-event-analyse")
public interface IAnalyseRestService {

    /**
     * 新增事件分析研判意见AnalyseVo
     * @param vo
     * @return ResponseEntity<AnalyseVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<AnalyseVo> create(@RequestBody AnalyseVo vo);

    /**
     * 根据id 获取事件分析研判意见AnalyseVo
     * @param id id不能为空
     * @return ResponseEntity<AnalyseVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<AnalyseVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新事件分析研判意见AnalyseVo
     * @param vo,
     * @param id 要更新AnalyseVo id
     * @return ResponseEntity<AnalyseVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<AnalyseVo> update(@RequestBody AnalyseVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id删除一条事件分析研判意见。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询事件分析研判结果列表-不分页AnalyseVo list
     * 参数keys：eventId
     * @param params
     * @return ResponseEntity<List<AnalyseVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<AnalyseVo>> searchAll(@RequestParam MultiValueMap<String,Object> params);

}
