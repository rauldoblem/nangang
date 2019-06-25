package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.searchVo.DailyShiftPageVo;
import com.taiji.emp.duty.vo.dailyShift.DailyShiftVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 交接班 feign 接口服务类
 * @author sun yi
 * @date 2018年11月2日
 */
@FeignClient(value = "micro-base-daily-shift")
public interface IDailyShiftRestService {


    /**
     * 新增交接班 DailyShiftVo，DailyShiftVo 不能为空
     * 包含 值班日志ID集合
     * @param vo
     * @return ResponseEntity<DailyShiftVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<DailyShiftVo> create(@RequestBody DailyShiftVo vo);


    /**
     * 根据id 获取交接班信息 DailyShiftVo
     * 包含 值班日志ID集合
     * @param id id不能为空
     * @return ResponseEntity<DailyShiftVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DailyShiftVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据参数获取 DailyShiftVo 多条记录,分页信息
     * params参数key为 fWatcherName(交班人姓名),toWatcherName（接班人姓名）,title（表题）
     * 包含值班日志ID集合
     *          page,size
     *  @param params
     *  @return ResponseEntity<RestPageImpl<DailyShiftVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DailyShiftVo>> findPage(@RequestBody DailyShiftPageVo dailyShiftPageVo);

}
