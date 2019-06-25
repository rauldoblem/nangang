package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.CompanyClient;
import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.emp.nangang.vo.CompanyVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends BaseService{
    @Autowired
    CompanyClient companyClient;

    public List<CompanyVo> findAll(){
        ResponseEntity<List<CompanyVo>> all = companyClient.findAll();
        List<CompanyVo> vos = ResponseEntityUtils.achieveResponseEntityBody(all);
        return vos;
    }

    public List<CompanyVo> findList(CompanyListVo companyListVo){
        ResponseEntity<List<CompanyVo>>resultVo = companyClient.findList(companyListVo);
        List<CompanyVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }
}
