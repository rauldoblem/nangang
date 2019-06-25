package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Telrecord;
import com.taiji.emp.base.repository.TelrecordRepository;
import com.taiji.emp.base.searchVo.telrecord.TelrecordPageVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TelrecordService extends BaseService<Telrecord,String> {
    @Autowired
    private TelrecordRepository telrecordRepository;
        //提供给controller使用的分页List查询方法
    public Page<Telrecord> findPage(TelrecordPageVo telrecordPageVo, Pageable pageable){
        Page<Telrecord> result = telrecordRepository.findPage(telrecordPageVo,pageable);
        return result;
    }
}
