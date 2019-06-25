package com.taiji.emp.drill.controller;

import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.searchVo.DrillPlanSearchVo;
import com.taiji.emp.drill.service.DrillPlanReceiveService;
import com.taiji.emp.drill.service.DrillPlanService;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
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
@RequestMapping("/drillplans")
public class DrillPlanController extends BaseController {

    @Autowired
    DrillPlanService service;

    @Autowired
    DrillPlanReceiveService receiveService;

    @Autowired
    UtilsService utilsService;

    /**
     * 新增演练计划
     * @param vo
     * @param principal
     * @return
     */
    @PostMapping
    public ResultEntity createDrillPlan(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DrillPlanVo vo, Principal principal){
        service.create(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除演练计划信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteNotice(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 更新演练计划
     * @param vo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updateNotice(
            @NotNull(message = "vo不能为null")
            @RequestBody DrillPlanVo vo
            , Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        service.update(id,vo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取演练计划信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findNoticeById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        DrillPlanVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据条件查询演练计划列表——分页
     * @param searchVo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody DrillPlanSearchVo searchVo){
        RestPageImpl<DrillPlanVo> pageVo = service.findPage(searchVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 根据条件查询演练计划接收列表——分页
     * @param searchVo
     * @param principal
     * @return
     */
    @PostMapping(path = "/searchRVo")
    public ResultEntity findReceivePage(@RequestBody DrillPlanReceiveSearchVo searchVo, Principal principal){
        RestPageImpl<DrillPlanReceiveVo> pageVo = receiveService.findPage(searchVo,principal);
        return ResultUtils.success(pageVo);
    }

    /**
     * 上报/下发演练计划
     * @param vo
     * @return
     */
    @PostMapping(path = "/send")
    public ResultEntity send(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillPlanReceiveSearchVo vo,
            Principal principal){
        //判断要上报、下发是否已存在
        List<DrillPlanReceiveVo> resultVo = receiveService.findIsExist(vo);
        List<String> orgIds = resultVo.stream().map(temp -> temp.getOrgId()).collect(Collectors.toList());

        String drillPlanId = vo.getDrillPlanId();
        DrillPlanVo drillPlanVo = service.findOne(drillPlanId);

        if (CollectionUtils.isEmpty(orgIds)) {
            List<DrillPlanReceiveVo> voList = receiveService.create(vo, principal);
            //更改状态
            service.updateStatusById(drillPlanVo, vo, principal, voList);
        }else {
            List<DrillPlanVo> orgIdANames = vo.getOrgIdANames();//传过来的机构
            List<DrillPlanVo> newOrgList = new ArrayList<>();
            for (DrillPlanVo planVo : orgIdANames){
                String orgId = planVo.getOrgId();
                if (!orgIds.contains(orgId)){
                    DrillPlanVo drillP = new DrillPlanVo();
                    drillP.setOrgId(orgId);
                    drillP.setOrgName(planVo.getOrgName());
                    newOrgList.add(drillP);
                }
            }
            vo.setOrgIdANames(newOrgList);
            List<DrillPlanReceiveVo> voList = receiveService.create(vo, principal);
            //更改状态
            service.updateStatusById(drillPlanVo, vo, principal, voList);
        }
        return ResultUtils.success();
    }

    /**
     * 接收演练计划
     * @param entity
     * @return
     */
    @PostMapping(path = "/receive")
    public ResultEntity receive(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillPlanReceiveSearchVo entity,
            Principal principal){
        String drillPlanId = entity.getDrillPlanId();
        DrillPlanReceiveVo vo = receiveService.findByDrillPlanId(drillPlanId,principal);
        receiveService.update(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询 计划接收部门的 接收状态信息列表
     * @param searchVo
     * @return
     */
    @PostMapping(path = "/receive/searchAll")
    public ResultEntity findList(@RequestBody DrillPlanReceiveSearchVo searchVo){
        List<DrillPlanReceiveVo> listVo = receiveService.findList(searchVo);
        return ResultUtils.success(listVo);
    }
}
