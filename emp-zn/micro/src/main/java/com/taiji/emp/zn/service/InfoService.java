package com.taiji.emp.zn.service;

import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.zn.entity.InfoStat;
import com.taiji.emp.zn.entity.QInfoStat;
import com.taiji.emp.zn.repository.InfoRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@AllArgsConstructor
public class InfoService extends BaseService<InfoStat,String> {

    @Autowired
    InfoRepository repository;

    /**
     * 根据条件查询预警/事件信息列表，并按视图view_alarm_event中的report_time倒序排列
     * 参数：page,size(默认10条)
     */
    public Page<InfoStat> statInfo(MultiValueMap<String, Object> params, Pageable pageable){
        return repository.statInfo(params,pageable);
    }
}
