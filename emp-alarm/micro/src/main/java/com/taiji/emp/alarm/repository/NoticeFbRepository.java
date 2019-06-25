package com.taiji.emp.alarm.repository;

import com.taiji.emp.alarm.entity.NoticeFeedback;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Repository
@Transactional(
        readOnly = true
)
public class NoticeFbRepository extends BaseJpaRepository<NoticeFeedback,String> {

    @Override
    @Transactional
    public NoticeFeedback save(NoticeFeedback entity){
        if(!StringUtils.isEmpty(entity.getId())){
            entity.setId(null);
        }
        return super.save(entity);
    }

}
