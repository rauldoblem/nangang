package com.taiji.emp.base.service;


import com.taiji.emp.base.entity.Recvfax;
import com.taiji.emp.base.entity.Sendfax;
import com.taiji.emp.base.repository.RecvfaxRepository;
import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class RecvfaxService  extends BaseService<Recvfax,String > {
    @Autowired
    private RecvfaxRepository recvfaxRepository;
    //提供给controller使用的分页List查询方法
    public Page<Recvfax> findPage(RecvfaxPageVo recvfaxPageVo, Pageable pageable) {
        Page<Recvfax> result = recvfaxRepository.findPage(recvfaxPageVo,pageable);
        return  result;
    }
}
