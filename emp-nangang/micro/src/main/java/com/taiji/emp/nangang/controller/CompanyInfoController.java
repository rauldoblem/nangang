package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.CompanyInfo;
import com.taiji.emp.nangang.feign.ICompanyInfoService;
import com.taiji.emp.nangang.mapper.CompanyInfoMapper;
import com.taiji.emp.nangang.service.CompanyInfoService;
import com.taiji.emp.nangang.vo.CompanyInfoVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/20 11:19
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/companyInfo")
public class CompanyInfoController implements ICompanyInfoService{

    @Autowired
    private CompanyInfoService service;
    @Autowired
    private CompanyInfoMapper mapper;

    @Override
    public ResponseEntity<List<CompanyInfoVo>> getCompanyInfo() {

        List<CompanyInfo> result = service.getCompanyInfo();;
        List<CompanyInfoVo> resultVos = mapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVos);
    }
}
