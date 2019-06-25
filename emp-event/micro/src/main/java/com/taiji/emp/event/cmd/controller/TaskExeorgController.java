package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.track.TaskExeorg;
import com.taiji.emp.event.cmd.feign.ITaskExeorgRestService;
import com.taiji.emp.event.cmd.mapper.CmdTaskExeorgMapper;
import com.taiji.emp.event.cmd.service.CmdTaskExeorgService;
import com.taiji.emp.event.cmd.vo.trackVo.TaskExeorgVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/task/exeorg")
public class TaskExeorgController extends BaseController implements ITaskExeorgRestService {

    CmdTaskExeorgMapper mapper;
    CmdTaskExeorgService service;

    @Override
    public ResponseEntity<TaskExeorgVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        TaskExeorg entity = service.findOne(id);
        TaskExeorgVo vo = mapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }
}
