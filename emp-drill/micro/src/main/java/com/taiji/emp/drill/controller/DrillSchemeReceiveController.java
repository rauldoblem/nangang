package com.taiji.emp.drill.controller;

import com.taiji.emp.drill.entity.DrillSchemeReceive;
import com.taiji.emp.drill.feign.IDrillSchemeReceiveRestService;
import com.taiji.emp.drill.mapper.DrillSchemeReceiveMapper;
import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
import com.taiji.emp.drill.service.DrillSchemeReceiveService;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/drillSchemeReceive")
public class DrillSchemeReceiveController extends BaseController implements IDrillSchemeReceiveRestService {

    DrillSchemeReceiveService service;
    DrillSchemeReceiveMapper mapper;

    /**
     * 根据条件查询演练方案接收列表——分页
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<DrillSchemeReceiveVo>> findPage(
            @RequestBody DrillSchemeReceiveSearchVo searchVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",searchVo.getPage());
        params.add("size",searchVo.getSize());
        Pageable page = PageUtils.getPageable(params);

        Page<DrillSchemeReceive> pageResult = service.findPage(searchVo,page);
        RestPageImpl<DrillSchemeReceiveVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 上报/下发演练方案
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeReceiveVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillSchemeReceiveVo vo) {
        DrillSchemeReceive entity = mapper.voToEntity(vo);
        DrillSchemeReceive result = service.create(entity);
        DrillSchemeReceiveVo entityToVo = mapper.entityToVo(result);
        return ResponseEntity.ok(entityToVo);
    }

    /**
     * 根据演练方案ID获取 演练方案接收信息
     * @param drillSchemeId
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeReceiveVo> findByDrillSchemeId(
            @NotEmpty(message = "drillSchemeId不能为空")
            @PathVariable(value = "drillSchemeId") String drillSchemeId) {
        DrillSchemeReceive entity = service.findByDrillSchemeId(drillSchemeId);
        DrillSchemeReceiveVo entityToVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(entityToVo);
    }

    /**
     * 接收演练方案
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeReceiveVo> updateStatus(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DrillSchemeReceiveVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        DrillSchemeReceive entity = mapper.voToEntity(vo);
        DrillSchemeReceive result = service.updateStatus(entity);
        DrillSchemeReceiveVo entityToVo = mapper.entityToVo(result);
        return ResponseEntity.ok(entityToVo);
    }

    /**
     * 根据条件查询 方案接受部门的 接受状态信息列表
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<List<DrillSchemeReceiveVo>> findList(@RequestBody DrillSchemeReceiveSearchVo searchVo) {
        List<DrillSchemeReceive> list = service.findList(searchVo);
        List<DrillSchemeReceiveVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 判断要上报、下发是否已存在
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<List<DrillSchemeReceiveVo>> findIsExist(@RequestBody DrillSchemeReceiveSearchVo searchVo) {
        List<DrillSchemeReceive> list = service.findIsExist(searchVo);
        List<DrillSchemeReceiveVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
