package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Sendfax;
import com.taiji.emp.base.repository.SendfaxRepository;
import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
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
public class SendfaxService extends BaseService<Sendfax,String > {
    @Autowired
    private SendfaxRepository sendfaxRepository;
    //提供给controller使用的分页List查询方法
    public Page<Sendfax> findPage(SendfaxPageVo sendfaxPageVo, Pageable pageable) {
       Page<Sendfax> result = sendfaxRepository.findPage(sendfaxPageVo,pageable);
       return  result;
    }
}
