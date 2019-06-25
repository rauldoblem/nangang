package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdPlanService;
import com.taiji.emp.event.cmd.vo.CmdPlanVo;
import com.taiji.emp.event.cmd.vo.SchemeVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/plans")
public class CmdPlanController {

    @Autowired
    private CmdPlanService service;

    /**
     * 新增关联预案
     * @param vos
     * @return ResultEntity<List < CmdPlanVo>>
     */
    @PostMapping
    public ResultEntity addEcPlans(
            @NotNull(message = "vos 不能为null")
            @RequestBody List<CmdPlanVo> vos, Principal principal){
        if(vos.size()>0){
            List<CmdPlanVo> resultVo = service.addEcPlans(vos,principal);
            return ResultUtils.success(resultVo);
        }else{
            return ResultUtils.fail(ResultCodeEnum.OPERATE_FAIL,"新启动预案数量为0");
        }

    }

    /**
     * 根据条件查询处置方案已关联的应急预案信息
     *
     * @param map 参数：schemeId 方案id
     * @return ResultEntity<List < CmdPlanVo>>
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findPlansAll(@RequestBody Map<String,Object> map){
        if(map.containsKey("schemeId")){
            List<CmdPlanVo> resultVo =service.findPlansAll(map);
            return ResultUtils.success(resultVo);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

}
