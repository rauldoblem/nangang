package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.trackVo.TaskExeorgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "micro-event-task-feed-exeorg")
public interface ITaskExeorgRestService {

    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TaskExeorgVo> findOne(@PathVariable(value = "id") String id);
}
