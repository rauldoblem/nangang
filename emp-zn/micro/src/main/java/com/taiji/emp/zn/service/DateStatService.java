package com.taiji.emp.zn.service;

import com.taiji.emp.zn.repository.DateStatRepository;
import com.taiji.emp.zn.vo.EventAndAlertDateStatVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@AllArgsConstructor
public class DateStatService {

    DateStatRepository repository;

    //按时间统计突发事件(事发时间--occurTime)总数和预警(创建时间--createTime)总数
    public EventAndAlertDateStatVo statEventAndAlertDate(MultiValueMap<String, Object> params){
        return repository.statEventAndAlertDate(params);
    }

}
