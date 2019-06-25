package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.emp.nangang.service.CompanyService;
import com.taiji.emp.nangang.vo.CompanyVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/companys")
public class CompanyController extends BaseController{
    @Autowired
    private CompanyService companyService;
    @GetMapping
    public ResultEntity getCompany(){
        List<CompanyVo> companyVo = companyService.findAll();
        return ResultUtils.success(companyVo);
    }

    //不分页
    @PostMapping(path = "/searchAll")
    public ResultEntity findOne(@RequestBody CompanyListVo companyListVo){
        List<CompanyVo> listVo = companyService.findList(companyListVo);
        return ResultUtils.success(listVo);
    }
}
