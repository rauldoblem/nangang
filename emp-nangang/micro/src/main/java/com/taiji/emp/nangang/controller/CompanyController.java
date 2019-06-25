package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.Company;
import com.taiji.emp.nangang.feign.ICompanyService;
import com.taiji.emp.nangang.mapper.CompanyMapper;
import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.emp.nangang.service.CompanyService;
import com.taiji.emp.nangang.vo.CompanyVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/companys")
public class CompanyController extends BaseController implements ICompanyService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResponseEntity<List<CompanyVo>> findAll(){
        List<Company> listCompany = companyService.findAll();
        List<CompanyVo> vos = companyMapper.entityListToVoList(listCompany);
        return ResponseEntity.ok(vos);
    }

    /**
     * 根据参数获取CompanyVo多条记录
     */
    @Override
    public ResponseEntity<List<CompanyVo>> findList(@RequestBody CompanyListVo companyListVo) {
        List<Company> list = companyService.findList(companyListVo);
        List<CompanyVo> voList = companyMapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
