package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.ConventionalFactorService;
import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.emp.nangang.vo.TotalConventionalFactorVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/conventionalFactors")
public class ConventionalFactorController {
    @Autowired
    private ConventionalFactorService conventionalFactorService;
    @GetMapping("/getConventionalFactor")
    public ResultEntity getConventionalFactor(){
        ConventionalFactorVo vo = conventionalFactorService.getConventionalFactor();
        return ResultUtils.success(vo);
    }
    @PostMapping("/search")
    public ResultEntity findPage(){
        RestPageImpl<ConventionalFactorVo> pageVo = conventionalFactorService.findPage();

        TotalConventionalFactorVo totalConventionalFactorVo = new TotalConventionalFactorVo();
        List<String> so2 = totalConventionalFactorVo.getSO2();
        List<String> pm2_5 = totalConventionalFactorVo.getPM2_5();
        List<String> o3 = totalConventionalFactorVo.getO3();
        List<String> PM10 = totalConventionalFactorVo.getPM10();
        List<String> NO2 = totalConventionalFactorVo.getNO2();

        List<String> time = totalConventionalFactorVo.getTime();

        List<ConventionalFactorVo> content = pageVo.getContent();
        for (int i = content.size() - 1; i >= 0; i--) {
            so2.add(content.get(i).getSo2());
            pm2_5.add(content.get(i).getPm2_5());
            o3.add(content.get(i).getO3());
            PM10.add(content.get(i).getPm10());
            NO2.add(content.get(i).getNo2());
            time.add(content.get(i).getCreateTime());

        }
        return ResultUtils.success(totalConventionalFactorVo);

    }

}
