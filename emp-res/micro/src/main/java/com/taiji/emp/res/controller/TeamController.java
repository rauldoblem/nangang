package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Team;
import com.taiji.emp.res.feign.ITeamRestService;
import com.taiji.emp.res.mapper.TeamMapper;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.service.TeamService;
import com.taiji.emp.res.vo.TeamVo;
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
@RequestMapping("/api/teams")
public class TeamController extends BaseController implements ITeamRestService{

    TeamService service;
    TeamMapper mapper;

    /**
     * 根据参数获取TeamVo多条记录
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *  @param teamListVo
     *  @return ResponseEntity<List<TeamVo>>
     */
    @Override
    public ResponseEntity<List<TeamVo>> findList(
            @NotNull
            @RequestBody TeamListVo teamListVo) {
        List<Team> resultList = service.findList(teamListVo);
        List<TeamVo> resultVo = mapper.entityListToVoList(resultList);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据参数获取TeamVo多条记录,分页信息
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *          page,size
     *  @param teamPageVo
     *  @return ResponseEntity<RestPageImpl<TeamVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<TeamVo>> findPage(
            @NotNull
            @RequestBody TeamPageVo teamPageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",teamPageVo.getPage());
        map.add("size",teamPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);
        Page<Team> resultPage = service.findPage(teamPageVo,pageable);
        RestPageImpl<TeamVo> resultVo = mapper.entityPageToVoPage(resultPage,pageable);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 新增救援队伍TeamVo，TeamVo不能为空
     * @param vo
     * @return ResponseEntity<TeamVo>
     */
    @Override
    public ResponseEntity<TeamVo> create(
            @Validated
            @NotNull( message = "TeamVo 不能为null")
            @RequestBody TeamVo vo) {
        Team team = mapper.voToEntity(vo);
        Team result = service.createOrUpdate(team);
        TeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新救援队伍TeamVo，TeamVo不能为空
     * @param vo,
     * @param id 要更新TeamVo id
     * @return ResponseEntity<TeamVo>
     */
    @Override
    public ResponseEntity<TeamVo> update(
            @Validated
            @NotNull( message = "TeamVo 不能为null")
            @RequestBody TeamVo vo,
            @NotEmpty( message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Team team = mapper.voToEntity(vo);
        Team result = service.createOrUpdate(team);
        TeamVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取救援队伍TeamVo
     * @param id id不能为空
     * @return ResponseEntity<TeamVo>
     */
    @Override
    public ResponseEntity<TeamVo> findOne(
            @NotEmpty( message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        Team team = service.findOne(id);
        TeamVo resultVo = mapper.entityToVo(team);
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
            @NotEmpty( message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 通过schemeId救援队伍信息
     * @param schemeId
     * @return
     */
    @Override
    public ResponseEntity<List<TeamVo>> findBySchemeId(@NotNull @RequestBody String schemeId) {
        List<TeamVo> resultVoList = service.findBySchemeId(schemeId);
        return new ResponseEntity<>(resultVoList, HttpStatus.OK);
    }
}
