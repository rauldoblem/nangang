package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanOrgRespon;
import com.taiji.emp.res.entity.PlanOrgResponDetail;
import com.taiji.emp.res.feign.IPlanOrgResponRestService;
import com.taiji.emp.res.mapper.PlanOrgResponDetailMapper;
import com.taiji.emp.res.mapper.PlanOrgResponMapper;
import com.taiji.emp.res.service.PlanOrgResponDetailService;
import com.taiji.emp.res.service.PlanOrgResponService;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.emp.res.vo.PlanOrgResponVo;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/planOrgRespons")
public class PlanOrgResponController extends BaseController implements IPlanOrgResponRestService {

    PlanOrgResponService service;
    PlanOrgResponMapper mapper;
    PlanOrgResponDetailService detailService;
    PlanOrgResponDetailMapper detailMapper;

    /**
     * 根据参数获取PlanOrgResponVo多条记录
     * 查询参数为 planOrgId
     *  @param planOrgResponVo
     *  @return ResponseEntity<List<PlanOrgResponVo>>
     */
    @Override
    public ResponseEntity<List<PlanOrgResponVo>> findList(@RequestBody PlanOrgResponVo planOrgResponVo) {
        List<PlanOrgRespon> list = service.findList(planOrgResponVo);
        List<PlanOrgResponVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanOrgResponVo多条记录
     * 查询参数为 planOrgIds
     *  @param planOrgResponVo
     *  @return ResponseEntity<List<PlanOrgResponVo>>
     */
    @Override
    public ResponseEntity<List<PlanOrgResponVo>> findByPlanOrgIds(@RequestBody PlanOrgResponVo planOrgResponVo) {
        List<PlanOrgRespon> list = service.findByPlanOrgIds(planOrgResponVo);
        List<PlanOrgResponVo> voList = new ArrayList<>();
        for(PlanOrgRespon temp:list){
            String tempId = temp.getId();
            //查询details
            PlanOrgResponDetailVo detailVo = new PlanOrgResponDetailVo();
            detailVo.setOrgResponId(tempId);
            List<PlanOrgResponDetail> details = detailService.findList(detailVo);

            PlanOrgResponVo vo = mapper.entityToVo(temp);
            if(null!=vo){
                vo.setDetails(detailMapper.entityListToVoList(details));
                voList.add(vo);
            }
        }
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }


    /**
     * 新增预案责任人、单位管理PlanOrgResponVo，PlanOrgResponVo不能为空
     * @param vo
     * @return ResponseEntity<PlanOrgResponVo>
     */
    @Override
    public ResponseEntity<PlanOrgResponVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanOrgResponVo vo) {
        PlanOrgRespon entity = mapper.voToEntity(vo);
        PlanOrgRespon result = service.createOrUpdate(entity);
        PlanOrgResponVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案责任人、单位管理PlanOrgResponVo，PlanOrgResponVo不能为空
     * @param vo,
     * @param id 要更新PlanOrgResponVo id
     * @return ResponseEntity<PlanOrgResponVo>
     */
    @Override
    public ResponseEntity<PlanOrgResponVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanOrgResponVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        vo.setId(id);
        PlanOrgRespon entity = mapper.voToEntity(vo);
        PlanOrgRespon result = service.createOrUpdate(entity);
        PlanOrgResponVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案责任人、单位管理PlanOrgResponVo
     * @param id id不能为空
     * @return ResponseEntity<PlanOrgResponVo>
     */
    @Override
    public ResponseEntity<PlanOrgResponVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanOrgRespon result = service.findOne(id);
        PlanOrgResponVo resultVo = mapper.entityToVo(result);
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
