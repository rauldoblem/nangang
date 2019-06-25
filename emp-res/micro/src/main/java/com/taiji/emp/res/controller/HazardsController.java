package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Hazard;
import com.taiji.emp.res.feign.IHazardRestService;
import com.taiji.emp.res.mapper.HazardMapper;
import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.res.service.HazardsService;
import com.taiji.emp.res.vo.HazardVo;
import com.taiji.emp.zn.vo.HazardStatVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
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
import org.springframework.util.Assert;
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
@RequestMapping("/api/hazards")
public class HazardsController extends BaseController implements IHazardRestService {

    HazardsService service;
    HazardMapper mapper;

    /**
     * 根据参数获取HazardVo多条记录
     * title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选)
     *  @param hazardPageVo 带查询条件的Vo
     *  @return ResponseEntity<List<HazardVo>>
     */
    @Override
    public ResponseEntity<List<HazardVo>> findList(
            @RequestBody HazardPageVo hazardPageVo) {
        List<Hazard> list = service.findList(hazardPageVo);
        List<HazardVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取HazardVo多条记录,分页信息
     *  @param hazardPageVo 带查询条件的Vo
     *  @return ResponseEntity<RestPageImpl<HazardVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<HazardVo>> findPage(
            @RequestBody HazardPageVo hazardPageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        int page = hazardPageVo.getPage();
        int size = hazardPageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        map.add("page",page);
        map.add("size",size);
        Pageable page1 = PageUtils.getPageable(map);

        Page<Hazard> pageResult = service.findPage(hazardPageVo,page1);
        RestPageImpl<HazardVo> voPage = mapper.entityPageToVoPage(pageResult,page1);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增危险源HazardVo，HazardVo不能为空
     * @param vo
     * @return ResponseEntity<HazardVo>
     */
    @Override
    public ResponseEntity<HazardVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody HazardVo vo) {
        Hazard entity = mapper.voToEntity(vo);
        Hazard result = service.create(entity);
        HazardVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新危险源HazardVo，HazardVo不能为空
     * @param vo,
     * @param id 要更新HazardVo 的 id
     * @return ResponseEntity<HazardVo>
     */
    @Override
    public ResponseEntity<HazardVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody HazardVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Hazard entity = mapper.voToEntity(vo);
        Hazard result = service.update(entity,id);
        HazardVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }



    /**
     * 根据id 获取危险源HazardVo
     * @param id id不能为空
     * @return ResponseEntity<HazardVo>
     */
    @Override
    public ResponseEntity<HazardVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Hazard result = service.findOne(id);
        HazardVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
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
     * 危险源级别ID集合
     * @return
     */
    @Override
    public ResponseEntity<List<HazardVo>> findGroupList(@RequestBody List<String> listCode) {
        List<Hazard> list = service.findGroupList(listCode);
        List<HazardVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    @Override
    public ResponseEntity<List<HazardStatVo>> findInfo(@RequestBody MaterialSearchVo vo) {
        List<HazardStatVo> result = service.findInfo(vo);
        return ResponseEntity.ok(result);
    }
}
