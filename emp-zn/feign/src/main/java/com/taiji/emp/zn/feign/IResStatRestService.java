package com.taiji.emp.zn.feign;

import com.taiji.emp.zn.vo.EventTypeStatVo;
import com.taiji.emp.zn.vo.TargetTypeStatVo;
import com.taiji.emp.zn.vo.TeamTypeStatVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 应急资源统计接口服务类
 * @author qizhijie-pc
 * @date 2018年12月20日11:08:14
 */
@FeignClient(value = "micro-zn-resStat")
public interface IResStatRestService {

    /**
     * 统计重点防护目标各大类数量 -- 目标类型
     * @return ResponseEntity<List<TargetTypeStatVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/statTargets/type")
    @ResponseBody
    ResponseEntity<List<TargetTypeStatVo>> statTargetByType();

    /**
     * 统计应急专家各大类数量 -- 事件类型
     * @return ResponseEntity<List<EventTypeStatVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/statExperts/type")
    @ResponseBody
    ResponseEntity<List<EventTypeStatVo>> statExpertByType();

    /**
     * 统计应急队伍各大类数量 -- 队伍类型
     * @return ResponseEntity<List<TeamTypeStatVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/statTeams/type")
    @ResponseBody
    ResponseEntity<List<TeamTypeStatVo>> statTeamByType();

}
