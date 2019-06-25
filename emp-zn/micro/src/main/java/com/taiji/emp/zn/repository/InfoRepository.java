package com.taiji.emp.zn.repository;

import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.zn.entity.InfoStat;
import com.taiji.emp.zn.entity.QInfoStat;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@Repository
@Transactional(
        readOnly = true
)
public class InfoRepository extends BaseJpaRepository<InfoStat,String> {

    //根据条件查询预警/事件信息列表，并按视图view_alarm_event中的report_time倒序排列
    public Page<InfoStat> statInfo(MultiValueMap<String, Object> params, Pageable pageable){
        //由于暂时没有另外的查询条件，因此直接返回分页结果
        QInfoStat infoStat = QInfoStat.infoStat;
        JPQLQuery<InfoStat> query = from(infoStat);
        query.orderBy(infoStat.reportTime.desc());
        return findAll(query,pageable);
    }

}
