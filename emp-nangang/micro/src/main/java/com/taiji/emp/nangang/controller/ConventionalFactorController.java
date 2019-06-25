package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.ConventionalFactor;
import com.taiji.emp.nangang.feign.IConventionalFactorService;
import com.taiji.emp.nangang.mapper.ConventionalFactorMapper;
import com.taiji.emp.nangang.service.ConventionalFactorService;
import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/conventionalFactors")
@AllArgsConstructor
public class ConventionalFactorController extends BaseController implements IConventionalFactorService {
    ConventionalFactorService conventionalFactorService;
    ConventionalFactorMapper conventionalFactorMapper;
    /**
     * 获取一条记录
     **/
    @Override
    public ResponseEntity<ConventionalFactorVo> find() {
        ConventionalFactor result = conventionalFactorService.getConventionalFactor();
        ConventionalFactorVo resultVo = conventionalFactorMapper.entityToVo(result);
        return new ResponseEntity(resultVo,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestPageImpl<ConventionalFactorVo>> findPage(){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",0);
        map.add("size",7);
        Pageable page = PageUtils.getPageable(map);
        Page<ConventionalFactor> pageResult = conventionalFactorService.findpage(page);
        RestPageImpl<ConventionalFactorVo> voPage = conventionalFactorMapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }
}

