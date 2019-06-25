package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdExpertService;
import com.taiji.emp.event.cmd.vo.CmdExpertVo;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/experts")
public class CmdExpertController {

    @Autowired
    private CmdExpertService service;

    /**
     * 新增关联应急专家
     * {
         "expertIds": [
         "string"
         ],
        "schemeId":"string"
        }
     */
    @PostMapping
    public ResultEntity addEcExperts(@RequestBody Map<String,Object> map, Principal principal){
        if(map.containsKey("expertIds")&&map.containsKey("schemeId")){
            String schemeId =map.get("schemeId").toString();
            List<String> expertIds = (List<String> )map.get("expertIds");
            if(null!=expertIds||expertIds.size()>0){
                service.addEcExperts(schemeId,expertIds,principal);
                return ResultUtils.success();
            }else{
                return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"关联的应急专家为空");
            }
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 删除关联的应急专家
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEcExpert(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEcExpert(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询处置方案已关联的应急专家信息
     * {
         "schemeId": "string"
       }
     */
    @PostMapping(path ="/searchAll")
    public ResultEntity findExpertsAll(@RequestBody Map<String,Object> map){
        List<CmdExpertVo> resultVoList = service.findExpertsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据启动的预案数字化信息生成关联的应急专家信息
     */
    @PostMapping(path ="/plans")
    public ResultEntity addPlanExperts(
            @NotNull(message = "CmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo,Principal principal){
        service.addPlanExperts(cmdPlansVo,principal);
        return ResultUtils.success();
    }

}
