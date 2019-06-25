package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.PlanOrgResponDetail;
import com.taiji.emp.res.feign.IPlanOrgResponDetailRestService;
import com.taiji.emp.res.mapper.PlanOrgResponDetailMapper;
import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.service.PlanOrgResponDetailService;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
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
@RequestMapping("api/planOrgResponDetails")
public class PlanOrgResponDetailController extends BaseController implements IPlanOrgResponDetailRestService {

    PlanOrgResponDetailService service;
    PlanOrgResponDetailMapper mapper;

    /**
     * 根据参数获取PlanOrgResponDetailVo多条记录
     * 查询参数为
     *  @param planOrgResponDetailVo
     *  @return ResponseEntity<List<PlanOrgResponVo>>
     */
    @Override
    public ResponseEntity<List<PlanOrgResponDetailVo>> findList(@RequestBody PlanOrgResponDetailVo planOrgResponDetailVo) {
        List<PlanOrgResponDetail> list = service.findList(planOrgResponDetailVo);
        List<PlanOrgResponDetailVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 新增预案责任人、单位管理详情PlanOrgResponDetailVo，PlanOrgResponDetailVo不能为空
     * @param vo
     * @return ResponseEntity<PlanOrgResponDetailVo>
     */
    @Override
    public ResponseEntity<PlanOrgResponDetailVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanOrgResponDetailListVo vo) {
        PlanOrgResponDetail result = service.createOrUpdate(vo,null);
        PlanOrgResponDetailVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新预案责任人、单位管理详情PlanOrgResponDetailVo，PlanOrgResponDetailVo不能为空
     * @param vo,
     * @param id 要更新PlanOrgResponDetailVo id
     * @return ResponseEntity<PlanOrgResponDetailVo>
     */
    @Override
    public ResponseEntity<List<PlanOrgResponDetailVo>> updateDetail(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanOrgResponDetailListVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        List<PlanOrgResponDetailVo> planOrgResponDetailVo = vo.getPlanOrgResponDetailVo();
        List<PlanOrgResponDetail> planOrgResponDetail = mapper.voListToEntityList(planOrgResponDetailVo);
        List<PlanOrgResponDetail> result = service.updateDetail(planOrgResponDetail,id);
        List<PlanOrgResponDetailVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案责任人、单位管理详情PlanOrgResponDetailVo
     * @param id id不能为空
     * @return ResponseEntity<PlanOrgResponDetailVo>
     */
    @Override
    public ResponseEntity<PlanOrgResponDetailVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanOrgResponDetail result = service.findOne(id);
        PlanOrgResponDetailVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param ids
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody List<String> ids ){
        service.deleteLogic(ids, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取PlanOrgResponDetailListVo多条记录
     * 查询参数为
     *  @param planOrgResponDetailListVo
     *  @return ResponseEntity<List<PlanOrgResponVo>>
     */
    @Override
    public ResponseEntity<List<PlanOrgResponDetailVo>> findList(@RequestBody PlanOrgResponDetailListVo planOrgResponDetailListVo) {
        List<PlanOrgResponDetail> list = service.findList(planOrgResponDetailListVo);
        List<PlanOrgResponDetailVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }
}
