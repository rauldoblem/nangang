package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.Smoke;
import com.taiji.emp.nangang.feign.ISmokeService;
import com.taiji.emp.nangang.mapper.SmokeMapper;
import com.taiji.emp.nangang.service.SmokeService;
import com.taiji.emp.nangang.vo.SmokeVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/10 11:26
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/smokes")
public class SmokeController implements ISmokeService{

    @Autowired
    private SmokeService service;
    @Autowired
    private SmokeMapper mapper;

    @Override
    public ResponseEntity<SmokeVo> getSmoke() {
        Smoke entity = service.getSmoke();
        SmokeVo vo = mapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }

    @Override
    public ResponseEntity<RestPageImpl<SmokeVo>> findPage() {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",0);
        map.add("size",10);//页数待改
        Pageable page = PageUtils.getPageable(map);

        Page<Smoke> pageResult = service.findPage(page);
        RestPageImpl<SmokeVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }
}
