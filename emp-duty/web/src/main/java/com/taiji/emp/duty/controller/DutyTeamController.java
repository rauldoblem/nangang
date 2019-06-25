package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.service.DutyTeamService;
import com.taiji.emp.duty.vo.DutyTeamVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dutyteams")
public class DutyTeamController extends BaseController {

    @Autowired
    private DutyTeamService service;

    /**
     * 新增值班人员分组
     * @param dutyTeamVo
     * @return
     */
    @PostMapping
    public ResultEntity createDutyTeam(
            @Validated
            @NotNull(message = "dutyTeamVo不能为null")
            @RequestBody DutyTeamVo dutyTeamVo,
            OAuth2Authentication principal){
        service.create(dutyTeamVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id删除某条值班人员分组信息
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteDutyTeam(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 修改值班人员分组信息
     * @param dutyTeamVo
     * @param principal
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultEntity updateDutyTeam(
            @NotNull(message = "dutyTeamVo不能为null")
            @RequestBody DutyTeamVo dutyTeamVo,
            Principal principal,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        dutyTeamVo.setId(id);
        service.update(dutyTeamVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id查询值班人员分组信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
       DutyTeamVo vo = service.findOne(id);
       return ResultUtils.success(vo);
    }

    /**
     *  根据条件查询值班人员分组列表
     * @param orgId
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestParam(value = "orgId") String orgId){
        List<DutyTeamVo> listVo = service.findList(orgId);
        return ResultUtils.success(listVo);
    }
}
