package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanTask;
import com.taiji.emp.res.feign.IPlanTaskRestService;
import com.taiji.emp.res.mapper.PlanTaskMapper;
import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.searchVo.planTask.PlanTaskPageVo;
import com.taiji.emp.res.service.PlanTaskService;
import com.taiji.emp.res.vo.PlanTaskVo;
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
@RequestMapping("/api/planTasks")
public class PlanTaskController extends BaseController implements IPlanTaskRestService {

    PlanTaskService service;
    PlanTaskMapper mapper;

    /**
     * 根据参数获取PlanTaskVo多条记录
     * 查询参数为 princiOrgName(可选),title(可选),planId(可选),eventGradeId(可选),princiOrgId(可选)
     *  @param planTaskListVo
     *  @return ResponseEntity<List<PlanTaskVo>>
     */
    @Override
    public ResponseEntity<List<PlanTaskVo>> findList(@RequestBody PlanTaskListVo planTaskListVo) {
        List<PlanTask> list = service.findList(planTaskListVo);
        List<PlanTaskVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanTaskVo多条记录,分页信息
     * 查询参数为 princiOrgName(可选),title(可选),planId(可选),eventGradeId(可选),princiOrgId(可选)
     *          page,size
     *  @param planTaskPageVo
     *  @return ResponseEntity<RestPageImpl<PlanTaskVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<PlanTaskVo>> findPage(@RequestBody PlanTaskPageVo planTaskPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",planTaskPageVo.getPage());
        map.add("size",planTaskPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<PlanTask> pageResult = service.findPage(planTaskPageVo,page);
        RestPageImpl<PlanTaskVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 新增预案任务设置管理PlanTaskVo，PlanTaskVo不能为空
     * @param vo
     * @return ResponseEntity<PlanTaskVo>
     */
    @Override
    public ResponseEntity<PlanTaskVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanTaskVo vo) {
        PlanTask entity = mapper.voToEntity(vo);
        PlanTask result = service.createOrUpdate(entity);
        PlanTaskVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案任务设置管理PlanTaskVo，PlanTaskVo不能为空
     * @param vo,
     * @param id 要更新PlanTaskVo id
     * @return ResponseEntity<PlanTaskVo>
     */
    @Override
    public ResponseEntity<PlanTaskVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanTaskVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanTask entity = mapper.voToEntity(vo);
        PlanTask result = service.createOrUpdate(entity);
        PlanTaskVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案任务设置管理PlanTaskVo
     * @param id id不能为空
     * @return ResponseEntity<PlanTaskVo>
     */
    @Override
    public ResponseEntity<PlanTaskVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanTask result = service.findOne(id);
        PlanTaskVo resultVo = mapper.entityToVo(result);
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
    /**
     * 根据参数获取PlanTaskVo多条记录
     * 查询参数为 planIds
     *  @param planTaskListVo
     *  @return ResponseEntity<List<PlanTaskVo>>
     */
    @Override
    public ResponseEntity<List<PlanTaskVo>> findByPlanIds(@RequestBody PlanTaskListVo planTaskListVo) {
        List<PlanTask> list = service.findList(planTaskListVo);
        List<PlanTaskVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }
}
