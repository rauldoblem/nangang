package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.Company;
import com.taiji.emp.nangang.repository.CompanyRepository;
import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class CompanyService extends BaseService<Company, String > {
    @Autowired
    private CompanyRepository companyRepository;
    public List<Company> findAll(){
        List<Company> list = companyRepository.findAll();
        return list;
    }

    public List<Company> findList(CompanyListVo companyListVo){
        List<Company> result = companyRepository.findList(companyListVo);
        return result;
    }
//
//    public List<Company> findList(CompanyListVo companyListVo){
//        List<Company> result;
//        if (null == companyListVo){
//            result = companyRepository.findAll();
//        }else  {
//           result = companyRepository.findList(companyListVo);
//        }
//        return result;
//    }

}
