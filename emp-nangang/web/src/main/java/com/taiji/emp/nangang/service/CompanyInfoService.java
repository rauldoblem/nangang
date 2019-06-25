package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.CompanyInfoClient;
import com.taiji.emp.nangang.vo.CompanyInfoVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/20 11:13
 */
@Service
public class CompanyInfoService {

    @Autowired
    private CompanyInfoClient client;

    public List<CompanyInfoVo> getCompanyInfo() {
        ResponseEntity<List<CompanyInfoVo>> result = client.getCompanyInfo();
        List<CompanyInfoVo> vos = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vos;
    }
}
