package com.taiji.emp.zn.feign;

import com.taiji.emp.zn.vo.InfoStatVo;
import com.taiji.emp.zn.vo.TargetTypeStatVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首页--事件信息接口服务类
 * @author qizhijie-pc
 * @date 2018年12月20日11:08:14
 */
@FeignClient(value = "micro-zn-info")
public interface IInfoRestService {

    /**
     * 根据条件查询预警/事件信息列表，并按视图view_alarm_event中的report_time倒序排列
     * 参数：page,size(默认10条)
     * @return ResponseEntity<RestPageImpl<InfoStatVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/infos/searchAll")
    @ResponseBody
    ResponseEntity<RestPageImpl<InfoStatVo>> statInfo(@RequestParam MultiValueMap<String,Object> params);

}
