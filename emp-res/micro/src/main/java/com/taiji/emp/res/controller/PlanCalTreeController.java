package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanCalTree;
import com.taiji.emp.res.feign.IPlanCalTreeRestService;
import com.taiji.emp.res.mapper.PlanCalTreeMapper;
import com.taiji.emp.res.service.PlanCalTreeService;
import com.taiji.emp.res.vo.PlanCalTreeVo;
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
@RequestMapping("/api/planCalTree")
public class PlanCalTreeController extends BaseController implements IPlanCalTreeRestService {

    PlanCalTreeService service;
    PlanCalTreeMapper mapper;

    /**
     * 根据参数获取PlanCalTreeVo多条记录
     *  @return ResponseEntity<List<PlanCalTreeVo>>
     */
    @Override
    public ResponseEntity<List<PlanCalTreeVo>> findList() {
        List<PlanCalTree> list = service.findList();
        List<PlanCalTreeVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 新增预案目录PlanCalTreeVo，PlanCalTreeVo不能为空
     * @param vo
     * @return ResponseEntity<PlanCalTreeVo>
     */
    @Override
    public ResponseEntity<PlanCalTreeVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanCalTreeVo vo) {
        vo.setOrders(0);
        PlanCalTree entity = mapper.voToEntity(vo);
        PlanCalTree result = service.createOrUpdate(entity);
        PlanCalTreeVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案目录PlanCalTreeVo，PlanCalTreeVo不能为空
     * @param vo,
     * @param id 要更新PlanCalTreeVo id
     * @return ResponseEntity<PlanCalTreeVo>
     */
    @Override
    public ResponseEntity<PlanCalTreeVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanCalTreeVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanCalTree entity = mapper.voToEntity(vo);
        PlanCalTree result = service.createOrUpdate(entity);
        PlanCalTreeVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案目录PlanCalTreeVo
     * @param id id不能为空
     * @return ResponseEntity<PlanCalTreeVo>
     */
    @Override
    public ResponseEntity<PlanCalTreeVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanCalTree result = service.findOne(id);
        PlanCalTreeVo resultVo = mapper.entityToVo(result);
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
