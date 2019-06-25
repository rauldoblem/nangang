package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.CompanyInfoService;
import com.taiji.emp.nangang.vo.CompanyInfoVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/20 11:11
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/companyInfo")
public class CompanyInfoController {
    @Autowired
    private CompanyInfoService service;
    /**
     * 查询卡口名称以及车辆的出入量
     */
    @GetMapping
    public ResultEntity getCompanyInfo() {
        List<CompanyInfoVo> resultVos = service.getCompanyInfo();
        return ResultUtils.success(resultVos);
    }
}
