package com.taiji.emp.duty.repository;

import com.taiji.emp.duty.entity.dailyShift.DailyLogShift;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(
        readOnly = true
)
public class DailyLogShiftRepository extends BaseJpaRepository<DailyLogShift,String> {


}
