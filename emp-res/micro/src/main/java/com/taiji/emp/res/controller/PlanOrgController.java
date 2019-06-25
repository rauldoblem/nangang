package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanOrg;
import com.taiji.emp.res.feign.IPlanOrgRestService;
import com.taiji.emp.res.mapper.PlanOrgMapper;
import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.service.PlanOrgService;
import com.taiji.emp.res.vo.PlanOrgVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/planOrgs")
public class PlanOrgController extends BaseController implements IPlanOrgRestService {

    PlanOrgService service;
    PlanOrgMapper mapper;

    /**
     * 根据参数获取PlanOrgVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planOrgListVo
     *  @return ResponseEntity<List<PlanOrgVo>>
     */
    @Override
    public ResponseEntity<List<PlanOrgVo>> findList(@RequestBody PlanOrgListVo planOrgListVo) {
        List<PlanOrg> list = service.findList(planOrgListVo);
        List<PlanOrgVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanOrgVo多条记录
     * 查询参数为 planIds
     *  @param planOrgListVo
     *  @return ResponseEntity<List<PlanOrgVo>>
     */
    @Override
    public ResponseEntity<List<PlanOrgVo>> findListByPlanIds(@RequestBody PlanOrgListVo planOrgListVo) {
        List<PlanOrg> list = service.findListByPlanIds(planOrgListVo);
        List<PlanOrgVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 新增预案组织机构管理PlanOrgVo，PlanOrgVo不能为空
     * @param vo
     * @return ResponseEntity<PlanOrgVo>
     */
    @Override
    public ResponseEntity<PlanOrgVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanOrgVo vo) {
        PlanOrg entity = mapper.voToEntity(vo);
        PlanOrg result = service.createOrUpdate(entity);
        PlanOrgVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案组织机构管理PlanOrgVo，PlanOrgVo不能为空
     * @param vo,
     * @param id 要更新PlanOrgVo id
     * @return ResponseEntity<PlanOrgVo>
     */
    @Override
    public ResponseEntity<PlanOrgVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanOrgVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanOrg entity = mapper.voToEntity(vo);
        PlanOrg result = service.createOrUpdate(entity);
        PlanOrgVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案组织机构管理PlanOrgVo
     * @param id id不能为空
     * @return ResponseEntity<PlanOrgVo>
     */
    @Override
    public ResponseEntity<PlanOrgVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanOrg result = service.findOne(id);
        PlanOrgVo resultVo = mapper.entityToVo(result);
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
