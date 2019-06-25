package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.feign.IPersonRestService;
import com.taiji.emp.duty.mapper.PersonMapper;
import com.taiji.emp.duty.searchVo.PersonListVo;
import com.taiji.emp.duty.searchVo.PersonPageVo;
import com.taiji.emp.duty.service.PersonService;
import com.taiji.emp.duty.vo.PersonVo;
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
@RequestMapping("/api/person")
public class PersonController extends BaseController implements IPersonRestService {

    PersonService service;
    PersonMapper mapper;

    /**
     * 新增值班人员信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<PersonVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody PersonVo vo) {
        Person entity = mapper.voToEntity(vo);
        Person result = service.create(entity);
        PersonVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);

    }

    /**
     * 根据id删除一条记录
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody PersonListVo vo) {
        service.deleteLogic(vo, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 修改值班人员信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<PersonVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PersonVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        vo.setId(id);
        Person entity = mapper.voToEntity(vo);
        Person result = service.update(entity);
        PersonVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id查询某条值班人员信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<PersonVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Person result = service.findOne(id);
        PersonVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询值班人员列表
     * @param listVo
     * @return
     */
    @Override
    public ResponseEntity<List<PersonVo>> findList(
            @RequestBody PersonListVo listVo) {
        List<Person> list = service.findList(listVo);
        List<PersonVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据条件查询值班人员列表——分页
     * @param pageVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<PersonVo>> findPage(
            @RequestBody PersonPageVo pageVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",pageVo.getPage());
        params.add("size",pageVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<Person> pageResult = service.findPage(pageVo,page);
        RestPageImpl<PersonVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }
}
