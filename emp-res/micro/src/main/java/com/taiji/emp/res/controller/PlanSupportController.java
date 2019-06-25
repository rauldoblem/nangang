package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanSupport;
import com.taiji.emp.res.entity.Support;
import com.taiji.emp.res.feign.IPlanSupportRestService;
import com.taiji.emp.res.mapper.PlanSupportMapper;
import com.taiji.emp.res.mapper.SupportMapper;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportPageVo;
import com.taiji.emp.res.service.PlanSupportService;
import com.taiji.emp.res.vo.PlanSupportVo;
import com.taiji.emp.res.vo.SupportVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
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
@RequestMapping("/api/planSupports")
public class PlanSupportController extends BaseController implements IPlanSupportRestService {

    PlanSupportService service;
    PlanSupportMapper mapper;
    SupportMapper supportMapper;

    /**
     * 根据参数获取PlanSupportVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planSupportListVo
     *  @return ResponseEntity<List<PlanSupportVo>>
     */
    @Override
    public ResponseEntity<List<PlanSupportVo>> findList(@RequestBody PlanSupportListVo planSupportListVo) {
        List<PlanSupport> list = service.findList(planSupportListVo);
        List<PlanSupportVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanSupportVo多条记录,分页信息
     * 查询参数为 查询参数为 planId(可选),eventGradeID(可选)
     *          page,size
     *  @param planSupportPageVo
     *  @return ResponseEntity<RestPageImpl<PlanSupportVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<PlanSupportVo>> findPage(@RequestBody PlanSupportPageVo planSupportPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",planSupportPageVo.getPage());
        map.add("size",planSupportPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<PlanSupport> pageResult = service.findPage(planSupportPageVo,pageable);
        RestPageImpl<PlanSupportVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 新增预案社会依托资源管理基础PlanSupportVo，PlanSupportVo不能为空
     * @param vo
     * @return ResponseEntity<PlanSupportVo>
     */
    @Override
    public ResponseEntity<PlanSupportVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanSupportVo vo) {
        PlanSupport result = service.createOrUpdate(vo);
        PlanSupportVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案社会依托资源管理基础PlanSupportVo，PlanSupportVo不能为空
     * @param vo
     * @param id 要更新PlanSupportVo id
     * @return ResponseEntity<PlanSupportVo>
     */
    @Override
    public ResponseEntity<PlanSupportVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanSupportVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanSupport entity = mapper.voToEntity(vo);
        PlanSupport result = service.createOrUpdate(vo);
        PlanSupportVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取社会依托资源管理基础PlanSupportVo
     * @param id id不能为空
     * @return ResponseEntity<PlanSupportVo>
     */
    @Override
    public ResponseEntity<PlanSupportVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanSupport result = service.findOne(id);
        PlanSupportVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据参数物理删除一条记录。PlanSupportVo，PlanSupportVo不能为空
     * planId、eventGradeID、expertId
     * @param vo,
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deletePhysical(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanSupportVo vo)  {
        service.deletePhysical(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取SupportVo多条记录
     * 查询参数为 planIds
     *  @param planSupportListVo
     *  @return ResponseEntity<List<SupportVo>>
     */
    @Override
    public ResponseEntity<List<SupportVo>> findByPlanIds(@RequestBody PlanSupportListVo planSupportListVo) {
        List<Support> list = service.findSupportsByPlanIds(planSupportListVo);
        List<SupportVo> voList = supportMapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }
}
