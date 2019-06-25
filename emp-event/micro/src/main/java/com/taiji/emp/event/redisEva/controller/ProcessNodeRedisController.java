package com.taiji.emp.event.redisEva.controller;

import com.taiji.emp.event.redis.feign.IProcessNodeRedisRestService;
import com.taiji.emp.event.redis.vo.ProcessRedisNodeVo;
import com.taiji.emp.event.redisEva.entity.ProcessRedisNode;
import com.taiji.emp.event.redisEva.mapper.ProcessNodeRedisMapper;
import com.taiji.emp.event.redisEva.service.ProcessNodeServiceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title:DicGroupController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 8:25</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/process/node")
@AllArgsConstructor
public class ProcessNodeRedisController implements IProcessNodeRedisRestService {

    ProcessNodeServiceService service;
    ProcessNodeRedisMapper mapper;
    /**
     * 查询在线过程所以数据
     * @return
     */
    @Override
    public ResponseEntity<List<ProcessRedisNodeVo>> findNodeList() {
        List<ProcessRedisNode> list = service.findNodeList();
        return ResponseEntity.ok(mapper.entityListToVoList(list));
    }

    /**
     * 根据ID查询
     *
     * @return
     */
    @Override
    public ResponseEntity<ProcessRedisNodeVo> findNodeOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        ProcessRedisNode nodeOne = service.findNodeOne(id);
        return  ResponseEntity.ok(mapper.entityToVo(nodeOne));
    }


}
