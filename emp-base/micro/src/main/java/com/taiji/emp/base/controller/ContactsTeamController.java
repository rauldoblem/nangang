package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.entity.ContactTeam;
import com.taiji.emp.base.feign.IContactTeamRestService;
import com.taiji.emp.base.mapper.ContactMapper;
import com.taiji.emp.base.mapper.ContactTeamMapper;
import com.taiji.emp.base.searchVo.contacts.ContactTeamListVo;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.emp.base.service.ContactsTeamService;
import com.taiji.emp.base.vo.ContactTeamVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/contactteams")
public class ContactsTeamController extends BaseController implements IContactTeamRestService {

    private ContactsTeamService service;
    private ContactTeamMapper mapper;
    private ContactMapper contactMapper;

    /**
     * 根据参数获取 ContactTeamVo 多条记录
     * params参数key为teamId(可选),teamName(可选)
     *  @param contactTeamListVo
     *  @return ResponseEntity<List<ContactTeamVo>>
     */
    @Override
    public ResponseEntity<List<ContactTeamVo>> findList(@RequestBody ContactTeamListVo contactTeamListVo) {
        List<ContactTeam> list = service.findList(contactTeamListVo);
        List<ContactTeamVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestPageImpl<ContactVo>> findContactsList(@RequestBody ContactTeamsPageVo contactTeamsPageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",contactTeamsPageVo.getPage());
        map.add("size",contactTeamsPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);
        Page<ContactMid> list = service.findContactsList(contactTeamsPageVo,page);
        RestPageImpl<ContactVo> voList = contactMapper.entityPageMidToVoPage(list,page);
        return ResponseEntity.ok(voList);
    }

    /**
     * 新增通讯录 ContactTeamVo，ContactTeamVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactTeamVo>
     */
    @Override
    public ResponseEntity<ContactTeamVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ContactTeamVo vo) {
        ContactTeam entity = mapper.voToEntity(vo);
        ContactTeam result = service.create(entity);
        ContactTeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新通讯录 ContactTeamVo，ContactTeamVo 不能为空
     * @param vo,
     * @param id 要更新 ContactTeamVo 的 id
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<ContactTeamVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ContactTeamVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        vo.setId(id);
        ContactTeam entity = mapper.voToEntity(vo);
        ContactTeam result = service.update(entity);
        ContactTeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }



    /**
     * 根据id 获取通讯录 ContactTeamVo
     * @param id id不能为空
     * @return ResponseEntity<ContactTeamVo>
     */
    @Override
    public ResponseEntity<ContactTeamVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        ContactTeam result = service.findOne(id);
        ContactTeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据辑删除一条记录。
     *存在子节点一并删除
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deletes(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteIdAll(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
