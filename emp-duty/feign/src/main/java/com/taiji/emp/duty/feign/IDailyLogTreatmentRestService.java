package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 值班日志办理 feign 接口服务类
 * @author qzp-pc
 * @date 2018年10月28日11:16:37
 */
@FeignClient(value = "micro-duty-dLogTreatment")
public interface IDailyLogTreatmentRestService {

    /**
     * 记录值班日志办理情况——新增
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DailyLogTreatmentVo> create(DailyLogTreatmentVo vo);

    /**
     * 根据值班日志ID获取办理状态列表
     * @param dlogId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/list/{dlogId}")
    @ResponseBody
    ResponseEntity<List<DailyLogTreatmentVo>> findByDlogId(@PathVariable(value = "dlogId") String dlogId);
}
