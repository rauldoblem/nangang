package com.taiji.emp.drill.controller;

import com.taiji.emp.drill.entity.DrillPlanReceive;
import com.taiji.emp.drill.feign.IDrillPlanReceiveRestService;
import com.taiji.emp.drill.mapper.DrillPlanReceiveMapper;
import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.service.DrillPlanReceiveService;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
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
@RequestMapping("/api/drillReceive")
public class DrillPlanReceiveController extends BaseController implements IDrillPlanReceiveRestService {

    DrillPlanReceiveMapper mapper;
    DrillPlanReceiveService service;

    /**
     * 上报/下发演练计划
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanReceiveVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillPlanReceiveVo vo) {
        DrillPlanReceive entity = mapper.voToEntity(vo);
        DrillPlanReceive result = service.create(entity);
        DrillPlanReceiveVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 接收演练计划
     * @param drillPlanId
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanReceiveVo> update(String drillPlanId) {
        return null;
    }

    /**
     * 根据条件查询演练计划接收列表——分页
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<DrillPlanReceiveVo>> findPage(
            @RequestBody DrillPlanReceiveSearchVo searchVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",searchVo.getPage());
        params.add("size",searchVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<DrillPlanReceive> pageResult = service.findPage(searchVo,page);
        RestPageImpl<DrillPlanReceiveVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据条件查询 计划接收部门的 接收状态信息列表
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<List<DrillPlanReceiveVo>> findList(@RequestBody DrillPlanReceiveSearchVo searchVo) {
        List<DrillPlanReceive> list = service.findList(searchVo);
        List<DrillPlanReceiveVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据演练计划ID获取 演练计划接收信息
     * @param receiveVo
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanReceiveVo> findByDrillPlanId(@RequestBody  DrillPlanReceiveVo receiveVo) {
        DrillPlanReceive entity = service.findByDrillPlanId(receiveVo);
        DrillPlanReceiveVo resultVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 接收演练计划
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanReceiveVo> updateStatus(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DrillPlanReceiveVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        DrillPlanReceive entity = mapper.voToEntity(vo);
        DrillPlanReceive result = service.updateStatus(entity);
        DrillPlanReceiveVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 判断要上报、下发是否已存在
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<DrillPlanReceiveVo>> findIsExist(@RequestBody DrillPlanReceiveSearchVo vo) {
        List<DrillPlanReceive> list = service.findIsExist(vo);
        List<DrillPlanReceiveVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
