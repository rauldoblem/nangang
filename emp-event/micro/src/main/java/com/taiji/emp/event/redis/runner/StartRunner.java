package com.taiji.emp.event.redis.runner;

import com.taiji.emp.event.redis.feign.ProcessNodeRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRunner implements CommandLineRunner {

    @Autowired
    ProcessNodeRedisService processNodeRedisService;

    @Override
    public void run(String... strings) throws Exception {
        processNodeRedisService.clearAllProcessNode();
        System.out.println("在线过程数据，开始加载到redis......");
        long timeStartProcess = System.currentTimeMillis();
        processNodeRedisService.createFindAllProcessNode();
        long timeEndProcess = System.currentTimeMillis();
        System.out.println("在线过程数据加载结束,时间共计"+(timeEndProcess-timeStartProcess)+"毫秒");
    }
}
