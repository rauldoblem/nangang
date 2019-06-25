package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.CaseEntity;
import com.taiji.emp.res.feign.ICaseRestService;
import com.taiji.emp.res.mapper.CaseMapper;
import com.taiji.emp.res.searchVo.caseVo.CasePageVo;
import com.taiji.emp.res.service.CaseService;
import com.taiji.emp.res.vo.CaseEntityVo;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cases")
public class CaseController extends BaseController implements ICaseRestService {

    CaseService service;
    CaseMapper mapper;

    /**
     * 根据参数获取 CaseEntityVo 多条记录
     * params参数key为"title":"标题","eventTypeIds":["事件类型ID"],"eventGradeId": "事件等级ID",
     * "sourceFlag": "案例来源标识码","occurStartTime": "开始事发时间","occurEndTime": "结束事发时间",
     * "createOrgId": "创建单位ID"
     *  @return ResponseEntity<List<CaseEntityVo>>
     */
    @Override
    public ResponseEntity<List<CaseEntityVo>> findList(@RequestBody CasePageVo casePageVo) {
        List<CaseEntity> list = service.findList(casePageVo);
        List<CaseEntityVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取 CaseEntityVo 多条记录,分页信息
     * params参数key为 同上
     *  @return ResponseEntity<RestPageImpl<CaseEntityVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<CaseEntityVo>> findPage(@RequestBody CasePageVo casePageVo) {
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        int page = casePageVo.getPage();
        int size = casePageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        map.add("page",page);
        map.add("size",size);
        Pageable pageable = PageUtils.getPageable(map);

        Page<CaseEntity> pageResult = service.findPage(casePageVo,pageable);
        RestPageImpl<CaseEntityVo> voPage = mapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增案例信息 CaseEntityVo，CaseEntityVo 不能为空
     * @param vo
     * @return ResponseEntity<CaseEntityVo>
     */
    @Override
    public ResponseEntity<CaseEntityVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody CaseEntityVo vo) {
        CaseEntity entity = mapper.voToEntity(vo);
        CaseEntity result = service.create(entity);
        CaseEntityVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新案例信息 CaseEntityVo，CaseEntityVo 不能为空
     * @param vo,
     * @param id 要更新 CaseEntityVo 的 id
     * @return ResponseEntity<HazardVo>
     */
    @Override
    public ResponseEntity<CaseEntityVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody CaseEntityVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        vo.setId(id);
        CaseEntity entity = mapper.voToEntity(vo);
        CaseEntity result = service.update(entity);
        CaseEntityVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }



    /**
     * 根据id 获取案例信息 CaseEntityVo
     * @param id id不能为空
     * @return ResponseEntity<CaseEntityVo>
     */
    @Override
    public ResponseEntity<CaseEntityVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        CaseEntity result = service.findOne(id);
        CaseEntityVo resultVo = mapper.entityToVo(result);
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
}
