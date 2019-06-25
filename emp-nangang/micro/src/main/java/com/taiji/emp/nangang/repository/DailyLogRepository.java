package com.taiji.emp.nangang.repository;

import com.taiji.emp.nangang.entity.DailyCheckDailyLog;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yhcookie
 * @date 2018/12/3 13:00
 */
@Repository
@Transactional(
        readOnly = true
)
public class DailyLogRepository extends BaseJpaRepository<DailyCheckDailyLog,String> {
}
