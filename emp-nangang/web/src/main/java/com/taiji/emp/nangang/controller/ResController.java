package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.ResService;
import com.taiji.emp.nangang.vo.ResVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/resSearch")
public class ResController extends BaseController{
    @Autowired
    private ResService resService;
    @GetMapping
    public ResultEntity getResVo(@RequestParam String setSearchName){
        List<Map<String, Object>> resVoList = resService.getResVo(setSearchName);
        return ResultUtils.success(resVoList);
    }
}
