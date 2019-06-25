package com.taiji.emp.zn.feign;

import com.taiji.emp.zn.vo.EventAndAlertDateStatVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页--按时间统计接口服务类
 * @author qizhijie-pc
 * @date 2018年12月21日16:22:33
 */
@FeignClient(value = "micro-zn-dateStat")
public interface IDateStatRestService {

    /**
     * 按时间统计突发事件(事发时间--occurTime)总数和预警(创建时间--createTime)总数
     * @param params
     * statDate: List<String>  -- example:{'2018-02','2018-03',...}
     * alertStatus : List<String> 预警状态 -- example:{'2','3',...}
     * @return ResponseEntity<EventAndAlertDateStatVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/stats/statEvents")
    @ResponseBody
    ResponseEntity<EventAndAlertDateStatVo> statEventAndAlertDate(@RequestParam MultiValueMap<String,Object> params);

}
