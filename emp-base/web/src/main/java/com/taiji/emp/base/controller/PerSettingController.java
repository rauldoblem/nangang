package com.taiji.emp.base.controller;

import com.taiji.emp.base.service.OrgTeamService;
import com.taiji.emp.base.vo.OrgTeamMidSaveVo;
import com.taiji.emp.base.vo.OrgTeamMidVo;
import com.taiji.emp.base.vo.OrgTeamVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 9:48
 */
@Slf4j
@RestController
@RequestMapping
public class PerSettingController {

    @Autowired
    private OrgTeamService service;

    /**
     * 新增分组
     * @param orgTeamVo
     * @param principal
     * @return
     */
    @PostMapping("/orgTeam")
    public ResultEntity createOrgTeam(
            @Validated
            @NotNull(message = "orgTeamVo不能为null")
            @RequestBody OrgTeamVo orgTeamVo, Principal principal){

        service.create(orgTeamVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改分组（用不到id）
     * @param orgTeamVo
     * @param principal
     * @return
     */
    @PutMapping("/orgTeam/{id}")
    public ResultEntity updateOrgTeam(
            @Validated
            @NotNull(message = "orgTeamVo不能为null")
            @RequestBody OrgTeamVo orgTeamVo, Principal principal){

        service.update(orgTeamVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取一条分组信息
     * @param id
     * @return
     */
    @GetMapping("/orgTeam/{id}")
    public ResultEntity getOrgTeam(
            @Validated
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        OrgTeamVo orgTeamVo = service.findOne(id);
        return ResultUtils.success(orgTeamVo);
    }

    /**
     * 根据id删除（逻辑）一条分组，并删除（物理）该分组下所有中间表信息
     * @param id
     * @return
     */
    @DeleteMapping("/orgTeam/{id}")
    public ResultEntity deleteOrgTeam(
            @Validated
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteOne(id);
        return ResultUtils.success();
    }

    /**
     * 查询该用户下的所有分组（不包括逻辑删除的）
     * @param
     * @return
     */
    @GetMapping("/orgTeam/searchAll")
    public ResultEntity<List<OrgTeamVo>> searchAllOrgTeam(Principal principal){
        List<OrgTeamVo> resultVo = service.searchAll(principal);
        return ResultUtils.success(resultVo);
    }

    /**
     *  设置分组内组织机构，后台每次均将前一次数据删除，再增加新关联
     * @param orgTeamMidSaveVo
     * @return
     */
    @PostMapping("/orgTeam/addAndRemove")
    public ResultEntity<List<OrgTeamVo>> addAndRemove(
            @Validated
            @NotNull(message = "orgTeamVo不能为null")
            @RequestBody OrgTeamMidSaveVo orgTeamMidSaveVo){
        service.createOrgTeamMids(orgTeamMidSaveVo);
        return ResultUtils.success();
    }

    /**
     * 根据分组ID获取分组下的组织机构信息
     * @param id
     * @return
     */
    @GetMapping("/orgTeam/searchOrgsByTeamId/{id}")
    public ResultEntity<List<OrgTeamMidVo>> searchOrgsByTeamId(
            @Validated
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        List<OrgTeamMidVo> resultVos = service.searchOrgsByTeamId(id);
        return ResultUtils.success(resultVos);
    }

}
