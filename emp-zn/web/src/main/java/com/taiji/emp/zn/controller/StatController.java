package com.taiji.emp.zn.controller;

import com.taiji.emp.zn.service.StatService;
import com.taiji.emp.zn.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 浙能首页 -- 统计分析
 * @author qizhijie-pc
 * @date 2018年12月20日14:07:20
 */
@Slf4j
@RestController
@RequestMapping("/stats")
public class StatController {

    @Autowired
    StatService service;

    @GetMapping(path = "/statTargets")
    public ResultEntity statTargets(){
        List<TargetTypeStatVo> list = service.statTargets();
        return ResultUtils.success(list);
    }

    @GetMapping(path = "/statExperts")
    public ResultEntity statExperts(){
        List<EventTypeStatVo> list = service.statExperts();
        return ResultUtils.success(list);
    }

    @GetMapping(path = "/statTeams")
    public ResultEntity statTeams(){
        List<TeamTypeStatVo> list = service.statTeams();
        return ResultUtils.success(list);
    }

    /**
     * 事件，预警按日期统计接口
     * 参数：{
         "statYears": [
         0
         ],
         "statMonths": [
         0
         ],
         "alertStatus": [
         "string"
         ]
       }
     */
    @PostMapping(path = "/statEvents")
    public ResultEntity statEventAlert(@RequestBody Map<String,Object> map){
        if(map.containsKey("statYears")&&map.containsKey("statMonths")&&map.containsKey("alertStatus")){
            List<DateStatDTO> list = service.statEventAlert(map);
            return ResultUtils.success(list);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 统计各板块的应急物资各大类数量
     * @return
     */
    @PostMapping(path = "/statMaterials")
    ResultEntity statMaterials(@RequestBody MaterialSearchVo vo){
        List<MaterialStatVo> list = service.statMaterials(vo);
        return ResultUtils.success(list);
    }

    /**
     * 统计各板块不同级别的重大风险源数量
     * @param vo
     * @return
     */
    @PostMapping(path = "/statHazards")
    ResultEntity statHazards(@RequestBody MaterialSearchVo vo){
        List<HazardStatVo> list = service.statHazards(vo);
        return ResultUtils.success(list);
    }
}
