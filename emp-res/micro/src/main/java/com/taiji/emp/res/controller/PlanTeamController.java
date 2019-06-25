package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanTeam;
import com.taiji.emp.res.entity.Team;
import com.taiji.emp.res.feign.IPlanTeamRestService;
import com.taiji.emp.res.mapper.PlanTeamMapper;
import com.taiji.emp.res.mapper.TeamMapper;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamPageVo;
import com.taiji.emp.res.service.PlanTeamService;
import com.taiji.emp.res.vo.PlanTeamVo;
import com.taiji.emp.res.vo.TeamVo;
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
@RequestMapping("/api/planTeams")
public class PlanTeamController extends BaseController implements IPlanTeamRestService {

    PlanTeamService service;
    PlanTeamMapper mapper;
    TeamMapper teamMapper;

    /**
     * 根据参数获取PlanTeamVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planTeamListVo
     *  @return ResponseEntity<List<PlanTeamVo>>
     */
    @Override
    public ResponseEntity<List<PlanTeamVo>> findList(@RequestBody PlanTeamListVo planTeamListVo) {
        List<PlanTeam> list = service.findList(planTeamListVo);
        List<PlanTeamVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanTeamVo多条记录,分页信息
     * 查询参数为 查询参数为 planId(可选),eventGradeID(可选)
     *          page,size
     *  @param planTeamPageVo
     *  @return ResponseEntity<RestPageImpl<PlanTeamVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<PlanTeamVo>> findPage(@RequestBody PlanTeamPageVo planTeamPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",planTeamPageVo.getPage());
        map.add("size",planTeamPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<PlanTeam> pageResult = service.findPage(planTeamPageVo,pageable);
        RestPageImpl<PlanTeamVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 新增预案团队管理基础PlanTeamVo，PlanTeamVo不能为空
     * @param vo
     * @return ResponseEntity<PlanTeamVo>
     */
    @Override
    public ResponseEntity<PlanTeamVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanTeamVo vo) {
        PlanTeam result = service.createOrUpdate(vo);
        PlanTeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案团队管理基础PlanTeamVo，PlanTeamVo不能为空
     * @param vo
     * @param id 要更新PlanTeamVo id
     * @return ResponseEntity<PlanTeamVo>
     */
    @Override
    public ResponseEntity<PlanTeamVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanTeamVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanTeam entity = mapper.voToEntity(vo);
        PlanTeam result = service.createOrUpdate(vo);
        PlanTeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案团队管理基础PlanTeamVo
     * @param id id不能为空
     * @return ResponseEntity<PlanTeamVo>
     */
    @Override
    public ResponseEntity<PlanTeamVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanTeam result = service.findOne(id);
        PlanTeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据参数物理删除一条记录。PlanTeamVo，PlanTeamVo不能为空
     * planId、eventGradeID、teamId
     * @param vo
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deletePhysical(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanTeamVo vo)  {
        service.deletePhysical(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取TeamVo多条记录
     * 查询参数为 planIds
     *  @param planTeamListVo
     *  @return ResponseEntity<List<TeamVo>>
     */
    @Override
    public ResponseEntity<List<TeamVo>> findByPlanIds(@RequestBody PlanTeamListVo planTeamListVo) {
        List<Team> list = service.findTeamsByPlanIds(planTeamListVo);
        List<TeamVo> voList = teamMapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }
}
