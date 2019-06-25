package com.taiji.emp.event.redis.feign;

import com.taiji.emp.event.redis.vo.ProcessRedisNodeVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "micro-process-node")
public interface IProcessNodeRedisRestService {

    /**
     * 查询在线过程所以数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/node/list")
    @ResponseBody
    ResponseEntity<List<ProcessRedisNodeVo>> findNodeList();


    /**
     * 根据ID查询
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/node/{id}")
    @ResponseBody
    ResponseEntity<ProcessRedisNodeVo> findNodeOne(@PathVariable(value = "id")String id);


}
