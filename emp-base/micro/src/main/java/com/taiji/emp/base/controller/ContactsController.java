package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.Contact;
import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.feign.IContactRestService;
import com.taiji.emp.base.mapper.ContactMapper;
import com.taiji.emp.base.searchVo.contacts.ContactPageVo;
import com.taiji.emp.base.service.ContactsMidService;
import com.taiji.emp.base.service.ContactsService;
import com.taiji.emp.base.vo.ContactMidVo;
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
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/contacts")
public class ContactsController extends BaseController implements IContactRestService {

    private ContactsService service;
    private ContactsMidService contactsMidservice;
    private ContactMapper mapper;

    /**
     * 根据参数获取 ContactVo 多条记录,不分页
     * @param contactPageVo
     * 参数 orgId(部门ID),dutyTypeId（职务ID）,name（姓名）,telephone（电话）
     * @return ResponseEntity<List<ContactVo>>
     */
    @Override
    public ResponseEntity<List<ContactVo>> findList(@RequestBody ContactPageVo contactPageVo) {
        List<Contact> list = service.findList(contactPageVo);
        List<ContactVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取 ContactVo 多条记录,分页信息
     * @param contactPageVo 参数 orgId(部门ID),dutyTypeId（职务ID）,name（姓名）,telephone（电话）
     *          page,size
     *  @return ResponseEntity<RestPageImpl<ContactVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<ContactVo>> findPage(@RequestBody ContactPageVo contactPageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        int page = contactPageVo.getPage();
        int size = contactPageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        map.add("page",page);
        map.add("size",size);
        Pageable pageable = PageUtils.getPageable(map);
        Page<Contact> pageResult = service.findPage(contactPageVo,pageable);
        RestPageImpl<ContactVo> voPage = mapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增通讯录 ContactVo，ContactVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<ContactVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody ContactVo vo) {
        Contact entity = mapper.voToEntity(vo);
        Contact result = service.create(entity);
        ContactVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新通讯录 ContactVo，ContactVo 不能为空
     * @param vo
     * @param id 要更新 ContactVo id
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<ContactVo> update(
            @NotNull(message = "vo不能为null")
            @RequestBody ContactVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        vo.setId(id);
        Contact entity = mapper.voToEntity(vo);
        Contact result = service.update(entity);
        ContactVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }



    /**
     * 根据id 获取通讯录 ContactVo
     * @param id id不能为空
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<ContactVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Contact result = service.findOne(id);
        ContactVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        List<ContactMid> list = contactsMidservice.findList(id);
        contactsMidservice.removeTeamList(list);
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 通讯录信息添加到分组 ContactMidVo，ContactMidVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<Void> addContactToTeam(
            @NotNull(message = "vo不能为null")
            @RequestBody ContactMidVo vo) {
        contactsMidservice.addContactToTeam(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 通讯录信息移出分组 ContactMidVo，ContactMidVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<Void> removeContactToTeam(
            @NotNull(message = "vo不能为null")
            @RequestBody ContactMidVo vo) {
        contactsMidservice.removeContactToTeam(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
