package com.taiji.emp.base.controller;

import com.taiji.emp.base.searchVo.contacts.ContactPageVo;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.emp.base.service.ContactsService;
import com.taiji.emp.base.vo.ContactMidSaveVo;
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
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
public class ContactsController extends BaseController {

    private ContactsService service;
    /**
     * 新增通讯录
     */
    @PostMapping
    public ResultEntity createContact(
            @Validated
            @NotNull(message = "ContactVo不能为null")
            @RequestBody ContactVo contactVo, Principal principal){
        service.create(contactVo,principal);
        return ResultUtils.success();
    }


    /**
     * 修改通讯录
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateContact(
            @NotEmpty(message = "id不能为空")
            @NotNull(message = "ContactVo不能为null")
            @PathVariable(name = "id") String id,
            @RequestBody ContactVo contactVo, Principal principal){
        contactVo.setId(id);
        service.update(contactVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条通讯录信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findContactById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        ContactVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条通讯录信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteContact(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询通讯录列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody ContactTeamsPageVo contactPageVo){
            RestPageImpl<ContactVo> pageVo = service.findPage(contactPageVo);
            return ResultUtils.success(pageVo);
    }

    /**
     * 查询通讯录列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody ContactPageVo contactPageVo){
        List<ContactVo> listVo = service.findList(contactPageVo);
        return ResultUtils.success(listVo);
    }


    /**
     * 将通讯录信息添加到组
     */
    @PostMapping(path = "/add")
    public ResultEntity addContactToTeam(
            @Validated
            @NotNull(message = "ContactMidSaveVo不能为null")
            @RequestBody ContactMidSaveVo contactMidSaveVo){
        service.addContactToTeam(contactMidSaveVo);
        return ResultUtils.success();
    }

    /**
     * 将通讯录信息移出到组
     */
    @PostMapping(path = "/remove")
    public ResultEntity removeContactToTeam(
            @Validated
            @NotNull(message = "ContactMidSaveVo不能为null")
            @RequestBody ContactMidSaveVo contactMidSaveVo){
        service.removeContactToTeam(contactMidSaveVo);
        return ResultUtils.success();
    }


}
