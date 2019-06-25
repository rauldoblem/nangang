package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdMaterialService;
import com.taiji.emp.event.cmd.vo.CmdMaterialVo;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
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
@RequestMapping("/cmd/materials")
public class CmdMaterialController {

    @Autowired
    private CmdMaterialService service;

    /**
     * 新增关联应急物资
     * {
     "schemeId": "string",
     "materialIds": [
     "string"
     ]
     }
     */
    @PostMapping
    public ResultEntity addEcMaterials(@RequestBody Map<String,Object> map, Principal principal){
        if(map.containsKey("materialIds")&&map.containsKey("schemeId")){
            String schemeId =map.get("schemeId").toString();
            List<String> materialIds = (List<String> )map.get("materialIds");
            if(null!=materialIds||materialIds.size()>0){
                service.addEcMaterials(schemeId,materialIds,principal);
                return ResultUtils.success();
            }else{
                return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"关联的应急物资为空");
            }
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 删除关联的应急物资
     *
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEcMaterial(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEcMaterial(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询处置方案已关联的应急物资信息
     * {
     "schemeId": "string"
     }
     */
    @PostMapping(path ="/searchAll")
    public ResultEntity findMaterialsAll(@RequestBody Map<String,Object> map){
        List<CmdMaterialVo> resultVoList = service.findMaterialsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据启动的预案数字化信息生成关联的应急物资信息
     */
    @PostMapping(path ="/plans")
    public ResultEntity addPlanMaterials(
            @NotNull(message = "CmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo, Principal principal){
        service.addPlanMaterials(cmdPlansVo,principal);
        return ResultUtils.success();
    }

    /**
     * 【2019年1月18日新增】按照地图格式要求获取方案中的物资信息
     */
    @PostMapping(path ="/getMaterialsForGis")
    public ResultEntity getMaterialsForGis(
            @NotNull(message = "cmdPlansVo 不能为null")
            @RequestBody CmdPlansVo cmdPlansVo){
        List<Map<String,Object>> resultVoList = service.getMaterialsForGis(cmdPlansVo);
        return ResultUtils.success(resultVoList);
    }
}
