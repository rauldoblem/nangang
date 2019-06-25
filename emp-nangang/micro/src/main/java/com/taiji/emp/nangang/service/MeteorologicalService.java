package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.Meteorological;
import com.taiji.emp.nangang.repository.MeteorologicalRepository;
import com.taiji.emp.nangang.searchVo.meteorological.MeteorologicalPageVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MeteorologicalService {
    @Autowired
    private MeteorologicalRepository meteorologicalRepository;

    public Meteorological getMeteorological() {
        return meteorologicalRepository.getMeteorological();
    }

//
//    //提供给controller使用的分页List查询方法
//    public Page<Meteorological> findPage(Pageable pageable){
//        Page<Meteorological> result = meteorologicalRepository.findPage(pageable);
//        return result;
//    }

}
