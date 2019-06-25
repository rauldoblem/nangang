package com.taiji.emp.base.controller;

import com.taiji.emp.base.common.constant.ContactsGlobal;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.emp.base.service.ContactsTeamService;
import com.taiji.emp.base.vo.ContactTeamVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/contactteams")
public class ContactsTeamController extends BaseController {

    private ContactsTeamService service;
    /**
     * 新增通讯录组
     */
    @PostMapping
    public ResultEntity createContactTeam(
            @Validated
            @NotNull(message = "ContactTeamVo不能为null")
            @RequestBody ContactTeamVo contactTeamVo){
        service.create(contactTeamVo);
        return ResultUtils.success();
    }


    /**
     * 修改通讯录组
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateContactTeam(
            @NotNull(message = "ContactTeamVo不能为null")
            @PathVariable(name = "id") String id,
            @RequestBody ContactTeamVo contactTeamVo){
        service.update(contactTeamVo,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条通讯录组信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findContactTeamById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        ContactTeamVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id删除单条通讯录组信息
     * 存在子节点一并删除
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteContactTeam(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteIdAll(id);
        return ResultUtils.success();
    }

    /**
     * 查询通讯录组 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(Principal principal){
        List<ContactTeamVo> listVo = service.findList(principal);
        return  ResultUtils.success(listVo);
    }



    /**
     * 查询通讯录 --- 分页
     */
    @PostMapping(path = "/searchContacts")
    public ResultEntity findContactsList(@RequestBody ContactTeamsPageVo contactTeamsPageVo){
        String teamId = contactTeamsPageVo.getTeamId();
        if(ContactsGlobal.CONTACT_YES.equals(teamId)){ contactTeamsPageVo.setTeamId(""); }
        RestPageImpl<ContactVo> listVo = service.findContactsList(contactTeamsPageVo);
            return ResultUtils.success(listVo);
    }

}
