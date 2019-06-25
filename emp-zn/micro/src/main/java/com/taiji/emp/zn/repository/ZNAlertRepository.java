package com.taiji.emp.zn.repository;

import com.taiji.emp.zn.entity.PushAlert;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yhcookie
 * @date 2018/12/22 20:56
 */
@Repository
@Transactional(readOnly = true)
public class ZNAlertRepository extends BaseJpaRepository<PushAlert,String>{
}
