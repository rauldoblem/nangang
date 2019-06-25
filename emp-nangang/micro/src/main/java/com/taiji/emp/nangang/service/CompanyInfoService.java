package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.CompanyInfo;
import com.taiji.emp.nangang.repository.CompanyInfoRepository;
import com.taiji.emp.nangang.repository.SmokeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/20 11:21
 */
@Slf4j
@Service
@AllArgsConstructor
public class CompanyInfoService {

    @Autowired
    private CompanyInfoRepository repository;

    public List<CompanyInfo> getCompanyInfo() {
        return repository.findAll();
    }
}
