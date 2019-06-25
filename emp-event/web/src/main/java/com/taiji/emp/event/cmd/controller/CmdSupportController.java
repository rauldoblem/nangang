package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdSupportService;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.cmd.vo.CmdSupportVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/supports")
public class CmdSupportController {

    @Autowired
    private CmdSupportService service;

    /**
     * 新增关联社会依托资源
     * {
         "schemeId": "string",
         "supportIds": [
            "string"
         ]
     }
     */
    @PostMapping
    public ResultEntity addEcSupports(@RequestBody Map<String,Object> map, Principal principal){
        if(map.containsKey("supportIds")&&map.containsKey("schemeId")){
            String schemeId =map.get("schemeId").toString();
            List<String> supportIds = (List<String> )map.get("supportIds");
            if(null!=supportIds||supportIds.size()>0){
                service.addEcSupports(schemeId,supportIds,principal);
                return ResultUtils.success();
            }else{
                return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"关联的社会依托资源为空");
            }
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 删除关联的社会依托资源
     *
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEcSupport(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEcSupport(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询处置方案已关联的社会依托资源信息
     * {
     "schemeId": "string"
     }
     */
    @PostMapping(path ="/searchAll")
    public ResultEntity findSupportsAll(@RequestBody Map<String,Object> map){
        List<CmdSupportVo> resultVoList = service.findSupportsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据启动的预案数字化信息生成关联的社会依托资源信息
     */
    @PostMapping(path ="/plans")
    public ResultEntity addPlanSupports(
            @NotNull(message = "CmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo, Principal principal){
        service.addPlanSupports(cmdPlansVo,principal);
        return ResultUtils.success();
    }

    /**
     * 【2019年1月18日新增】按照地图格式要求获取方案中的社会依托资源信息
     */
    @PostMapping(path ="/getSupportForGis")
    public ResultEntity getSupportForGis(
            @NotNull(message = "cmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo){
        List<Map<String,Object>> resultVoList = service.getSupportForGis(cmdPlansVo);
        return ResultUtils.success(resultVoList);
    }
}
