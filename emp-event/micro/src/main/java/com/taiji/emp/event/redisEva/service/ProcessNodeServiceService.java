package com.taiji.emp.event.redisEva.service;

import com.taiji.emp.event.redis.feign.ProcessNodeRedisService;
import com.taiji.emp.event.redisEva.entity.ProcessRedisNode;
import com.taiji.emp.event.redisEva.repository.ProcessNodeRedisRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统组织机构Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProcessNodeServiceService extends BaseService<ProcessRedisNode,String> {

    private ProcessNodeRedisRepository processNodeRepository;
    private ProcessNodeRedisService processNodeRedisService;

    public List<ProcessRedisNode> findNodeList(){
        List<ProcessRedisNode> result = processNodeRedisService.findAllProcessNode();
        if(result==null) {
            result = processNodeRepository.findAll();
        }
        return result;
    }
    public ProcessRedisNode findNodeOne(String id){
        ProcessRedisNode result = processNodeRedisService.findOneProcessNodeByIdAndName(id);
        if(result==null) {
            result = processNodeRepository.findOne(id);
        }
        return result;
    }

}
