package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.searchVo.DailyLogSearchVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 值班日志 feign 接口服务类
 * @author qzp-pc
 * @date 2018年10月28日11:16:37
 */
@FeignClient(value = "micro-duty-dailyLog")
public interface IDailyLogRestService {
    /**
     * 新增值班日志信息 DailyLogVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DailyLogVo> create(@RequestBody DailyLogVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改值班日志信息
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DailyLogVo> update(@RequestBody DailyLogVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id查询某条值班日志信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DailyLogVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询值班日志列表
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<DailyLogVo>> findList(@RequestBody DailyLogSearchVo searchVo);

    /**
     * 根据条件查询值班日志列表——分页
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DailyLogVo>> findPage(@RequestBody DailyLogSearchVo searchVo);
}
