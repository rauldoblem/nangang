package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanBase;
import com.taiji.emp.res.feign.IPlanBaseRestService;
import com.taiji.emp.res.mapper.PlanBaseMapper;
import com.taiji.emp.res.searchVo.planBase.PlanBaseListVo;
import com.taiji.emp.res.searchVo.planBase.PlanBasePageVo;
import com.taiji.emp.res.service.PlanBaseService;
import com.taiji.emp.res.vo.PlanBaseVo;
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
@RequestMapping("/api/planBases")
public class PlanBaseController extends BaseController implements IPlanBaseRestService {

    PlanBaseService service;
    PlanBaseMapper mapper;

    /**
     * 根据参数获取PlanBaseVo多条记录
     * 查询参数为 name(可选),eventTypeId(可选),planTypeId(可选),createOrgId
     *  @param planBaseListVo
     *  @return ResponseEntity<List<PlanBaseVo>>
     */
    @Override
    public ResponseEntity<List<PlanBaseVo>> findList(@RequestBody PlanBaseListVo planBaseListVo) {
        List<PlanBase> list = service.findList(planBaseListVo);
        List<PlanBaseVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanBaseVo多条记录,分页信息
     * 查询参数为 name(可选),eventTypeId(可选),planTypeId(可选),createOrgId
     *          page,size
     *  @param planBasePageVo
     *  @return ResponseEntity<RestPageImpl<PlanBaseVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<PlanBaseVo>> findPage(@RequestBody PlanBasePageVo planBasePageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",planBasePageVo.getPage());
        map.add("size",planBasePageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<PlanBase> pageResult = service.findPage(planBasePageVo,page);
        RestPageImpl<PlanBaseVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 新增预案管理基础PlanBaseVo，PlanBaseVo不能为空
     * @param vo
     * @return ResponseEntity<PlanBaseVo>
     */
    @Override
    public ResponseEntity<PlanBaseVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanBaseVo vo) {
        PlanBase entity = mapper.voToEntity(vo);
        PlanBase result = service.createOrUpdate(entity);
        PlanBaseVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案管理基础PlanBaseVo，PlanBaseVo不能为空
     * @param vo,
     * @param id 要更新PlanBaseVo id
     * @return ResponseEntity<PlanBaseVo>
     */
    @Override
    public ResponseEntity<PlanBaseVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanBaseVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanBase entity = mapper.voToEntity(vo);
        PlanBase result = service.createOrUpdate(entity);
        PlanBaseVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案管理基础PlanBaseVo
     * @param id id不能为空
     * @return ResponseEntity<PlanBaseVo>
     */
    @Override
    public ResponseEntity<PlanBaseVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanBase result = service.findOne(id);
        PlanBaseVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
