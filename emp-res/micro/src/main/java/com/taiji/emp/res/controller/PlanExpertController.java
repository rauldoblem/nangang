package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Expert;
import com.taiji.emp.res.entity.PlanExpert;
import com.taiji.emp.res.feign.IPlanExpertRestService;
import com.taiji.emp.res.mapper.ExpertMapper;
import com.taiji.emp.res.mapper.PlanExpertMapper;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.service.PlanExpertService;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.emp.res.vo.PlanExpertVo;
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
@RequestMapping("/api/planExperts")
public class PlanExpertController extends BaseController implements IPlanExpertRestService {

    PlanExpertService service;
    PlanExpertMapper mapper;
    ExpertMapper expertMapper;

    /**
     * 根据参数获取PlanExpertVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planExpertListVo
     *  @return ResponseEntity<List<PlanExpertVo>>
     */
    @Override
    public ResponseEntity<List<PlanExpertVo>> findList(@RequestBody PlanExpertListVo planExpertListVo) {
        List<PlanExpert> list = service.findList(planExpertListVo);
        List<PlanExpertVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanExpertVo多条记录,分页信息
     * 查询参数为 查询参数为 planId(可选),eventGradeID(可选)
     *          page,size
     *  @param planExpertPageVo
     *  @return ResponseEntity<RestPageImpl<PlanExpertVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<PlanExpertVo>> findPage(@RequestBody PlanExpertPageVo planExpertPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",planExpertPageVo.getPage());
        map.add("size",planExpertPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<PlanExpert> pageResult = service.findPage(planExpertPageVo,pageable);
        RestPageImpl<PlanExpertVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 新增预案专家管理基础PlanExpertVo，PlanExpertVo不能为空
     * @param vo
     * @return ResponseEntity<PlanExpertVo>
     */
    @Override
    public ResponseEntity<PlanExpertVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanExpertVo vo) {
        PlanExpert result = service.createOrUpdate(vo);
        PlanExpertVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案专家管理基础PlanExpertVo，PlanExpertVo不能为空
     * @param vo
     * @param id 要更新PlanExpertVo id
     * @return ResponseEntity<PlanExpertVo>
     */
    @Override
    public ResponseEntity<PlanExpertVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanExpertVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanExpert entity = mapper.voToEntity(vo);
        PlanExpert result = service.createOrUpdate(vo);
        PlanExpertVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案专家管理基础PlanExpertVo
     * @param id id不能为空
     * @return ResponseEntity<PlanExpertVo>
     */
    @Override
    public ResponseEntity<PlanExpertVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanExpert result = service.findOne(id);
        PlanExpertVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据参数物理删除一条记录。PlanExpertVo，PlanExpertVo不能为空
     * planId、eventGradeID、expertId
     * @param vo,
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deletePhysical(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanExpertVo vo)  {
        service.deletePhysical(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取ExpertVo多条记录
     * 查询参数为 planIds
     *  @param planExpertListVo
     *  @return ResponseEntity<List<ExpertVo>>
     */
    @Override
    public ResponseEntity<List<ExpertVo>> findByPlanIds(@RequestBody PlanExpertListVo planExpertListVo) {
        List<Expert> list = service.findExpertsByPlanIds(planExpertListVo);
        List<ExpertVo> voList = expertMapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }
}
