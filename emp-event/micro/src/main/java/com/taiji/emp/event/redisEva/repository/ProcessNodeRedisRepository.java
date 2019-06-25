package com.taiji.emp.event.redisEva.repository;

import com.taiji.emp.event.redisEva.entity.ProcessRedisNode;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(
        readOnly = true
)
public class ProcessNodeRedisRepository extends BaseJpaRepository<ProcessRedisNode,String> {

}
