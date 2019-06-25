package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Expert;
import com.taiji.emp.res.feign.IExpertRestService;
import com.taiji.emp.res.mapper.ExpertMapper;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.service.ExpertService;
import com.taiji.emp.res.vo.ExpertVo;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/experts")
public class ExpertController extends BaseController implements IExpertRestService {

    ExpertService service;
    ExpertMapper mapper;

    /**
     * 新增应急专家ExpertVo，ExpertVo不能为空
     * @param vo
     * @return ResponseEntity<ExpertVo>
     */
    @Override
    public ResponseEntity<ExpertVo> create(
            @Validated
            @NotNull(message = "ExpertVo 不能为null")
            @RequestBody ExpertVo vo){
        Expert entity = mapper.voToEntity(vo);
        Expert result = service.createOrUpdate(entity);
        ExpertVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id 获取应急专家ExpertVo
     * @param id id不能为空
     * @return ResponseEntity<ExpertVo>
     */
    @Override
    public ResponseEntity<ExpertVo> findOne(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Expert result = service.findOne(id);
        ExpertVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新应急专家ExpertVo，ExpertVo不能为空
     * @param vo,
     * @param id 要更新ExpertVo id
     * @return ResponseEntity<ExpertVo>
     */
    @Override
    public ResponseEntity<ExpertVo> update(
            @Validated
            @NotNull(message = "ExpertVo 不能为null")
            @RequestBody ExpertVo vo,
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Expert entity = mapper.voToEntity(vo);
        Expert result = service.createOrUpdate(entity);
        ExpertVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取ExpertVo多条记录,分页信息
     * 参数key为 name,unit,eventTypeIds(数组),specialty,createOrgId
     *          page,size
     *  @param expertPageVo
     *  @return ResponseEntity<RestPageImpl<ExpertVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<ExpertVo>> findPage(@RequestBody ExpertPageVo expertPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",expertPageVo.getPage());
        map.add("size",expertPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<Expert> pageResult = service.findPage(expertPageVo,pageable);
        RestPageImpl<ExpertVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据参数获取ExpertVo多条记录
     * 参数key为 name,unit,eventTypeIds(数组),specialty,createOrgId
     *  @param expertListVo
     *  @return ResponseEntity<List<ExpertVo>>
     */
    @Override
    public ResponseEntity<List<ExpertVo>> findList(@RequestBody ExpertListVo expertListVo) {
        List<Expert> result = service.findList(expertListVo);
        List<ExpertVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

}
