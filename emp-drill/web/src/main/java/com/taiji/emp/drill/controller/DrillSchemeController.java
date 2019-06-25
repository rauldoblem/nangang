package com.taiji.emp.drill.controller;

import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
import com.taiji.emp.drill.searchVo.DrillSchemeSearchVo;
import com.taiji.emp.drill.service.DrillSchemeReceiveService;
import com.taiji.emp.drill.service.DrillSchemeService;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.emp.drill.vo.DrillSchemeSaveVo;
import com.taiji.emp.drill.vo.DrillSchemeVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.service.UtilsService;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/drillschemes")
public class DrillSchemeController extends BaseController{

    @Autowired
    DrillSchemeService service;

    @Autowired
    DrillSchemeReceiveService schemeReceiveService;

    @Autowired
    UtilsService utilsService;

    /**
     * 新增演练方案(含附件)
     * @param saveVo
     * @param principal
     * @return
     */
    @PostMapping
    public ResultEntity createDrillScheme(
            @Validated
            @NotNull(message = "saveVo不能为null")
            @RequestBody DrillSchemeSaveVo saveVo, Principal principal){
        service.createDrillScheme(saveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除演练方案信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteDrillScheme(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 根据id修改演练方案信息
     * @param saveVo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateDrillScheme(
            @NotNull(message = "vo不能为null")
            @RequestBody DrillSchemeSaveVo saveVo,
            Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.update(id,saveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取一条演练方案信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        DrillSchemeVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据条件查询演练方案列表——分页
     * @param searchVo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody DrillSchemeSearchVo searchVo){
        RestPageImpl<DrillSchemeVo> pageVo = service.findPage(searchVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 根据条件查询演练方案接收列表——分页
     * @param searchVo
     * @param principal
     * @return
     */
    @PostMapping(path = "/searchRVo")
    public ResultEntity findReceivePage(@RequestBody DrillSchemeReceiveSearchVo searchVo,Principal principal){
        RestPageImpl<DrillSchemeReceiveVo> pageVo = schemeReceiveService.findPage(searchVo,principal);
        return ResultUtils.success(pageVo);
    }

    /**
     * 上报/下发演练方案
     * @param searchVo
     * @param principal
     * @return
     */
    @PostMapping(path = "/send")
    public ResultEntity send(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillSchemeReceiveSearchVo searchVo,
            Principal principal){
        //判断要上报、下发是否已存在
        List<DrillSchemeReceiveVo> resultVo = schemeReceiveService.findIsExist(searchVo);
        List<String> orgIds = resultVo.stream().map(temp -> temp.getOrgId()).collect(Collectors.toList());

        String drillSchemeId = searchVo.getDrillSchemeId();
        DrillSchemeVo drillSchemeVo = service.findOne(drillSchemeId);

        if (CollectionUtils.isEmpty(orgIds)) {
            List<DrillSchemeReceiveVo> voList = schemeReceiveService.create(searchVo, principal);
            //更改状态
            service.updateStatusById(drillSchemeVo,searchVo,principal,voList);
        }else {
            List<DrillSchemeReceiveVo> orgIdANames = searchVo.getOrgIdANames();//传过来的机构
            List<DrillSchemeReceiveVo> newOrgList = new ArrayList<>();
            for (DrillSchemeReceiveVo receiveVo : orgIdANames){
                String orgId = receiveVo.getOrgId();
                if (!orgIds.contains(orgId)){
                    DrillSchemeReceiveVo vo = new DrillSchemeReceiveVo();
                    vo.setOrgId(orgId);
                    vo.setOrgName(receiveVo.getOrgName());
                    newOrgList.add(vo);
                }
            }
            searchVo.setOrgIdANames(newOrgList);
            List<DrillSchemeReceiveVo> voList = schemeReceiveService.create(searchVo, principal);
            //更改状态
            service.updateStatusById(drillSchemeVo,searchVo,principal,voList);
        }
        return ResultUtils.success();
    }

    /**
     * 接收演练方案
     * @param searchVo
     * @param principal
     * @return
     */
    @PostMapping(path = "/receive")
    public ResultEntity receive(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillSchemeReceiveSearchVo searchVo,
            Principal principal){
        String drillSchemeId = searchVo.getDrillSchemeId();
        DrillSchemeReceiveVo vo = schemeReceiveService.findByDrillSchemeId(drillSchemeId);
        schemeReceiveService.update(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询 方案接受部门的 接受状态信息列表
     * @param searchVo
     * @return
     */
    @PostMapping(path = "/receive/searchAll")
    public ResultEntity findList(@RequestBody DrillSchemeReceiveSearchVo searchVo){
        List<DrillSchemeReceiveVo> listVo = schemeReceiveService.findList(searchVo);
        return ResultUtils.success(listVo);
    }
}
