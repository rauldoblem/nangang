package com.taiji.emp.event.redis.feign;

import com.taiji.emp.event.redis.utils.RedisUtil;
import com.taiji.emp.event.redisEva.entity.ProcessRedisNode;
import com.taiji.emp.event.redisEva.repository.ProcessNodeRedisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class ProcessNodeRedisService {


    ProcessNodeRedisRepository repository;
    RedisUtil redisUtil;

    private final static String processNodeKey = "processNode";
    /**
     * 将 processNode 所有数据缓存到redis
     */
    public void createFindAllProcessNode(){
        List<ProcessRedisNode> all = repository.findAll();
        for (ProcessRedisNode node:all) {
            String id = node.getId();
            redisUtil.set("processNode"+id,node);
        }
        redisUtil.set(processNodeKey,all);
    }


    /**
     * 获取缓存中的 key 为processNode 所有数据
     */
    public List<ProcessRedisNode> findAllProcessNode(){
           List<ProcessRedisNode> result = (List<ProcessRedisNode>)redisUtil.get(processNodeKey);
        return result;
    }


    /**
     * 获取缓存中的 key 为processNode 所有数据
     */
    public ProcessRedisNode findOneProcessNodeByIdAndName(String id){
        ProcessRedisNode result = (ProcessRedisNode)redisUtil.get("processNode"+id);
        return result;
    }




    /**
     * 清空缓存
     */
    public void clearAllProcessNode() {
        //1.清理item部分
        System.out.println(processNodeKey + "部分清理开始>>>>>>>>>>>>>>>>>>");
        List<ProcessRedisNode> all = repository.findAll();
        for (ProcessRedisNode node:all) {
            String id = node.getId();
            redisUtil.remove("processNode"+id);
        }
        redisUtil.remove(processNodeKey);
        System.out.println(processNodeKey + "部分清理结束>>>>>>>>>>>>>>>>>>");

    }



}
