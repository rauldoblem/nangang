package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.dailylog.DailyLog;
import com.taiji.emp.duty.feign.IDailyLogRestService;
import com.taiji.emp.duty.mapper.DailyLogMapper;
import com.taiji.emp.duty.searchVo.DailyLogSearchVo;
import com.taiji.emp.duty.service.DailyLogService;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
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
@RequestMapping("/api/dailyLog")
public class DailyLogController extends BaseController implements IDailyLogRestService {

    DailyLogService service;
    DailyLogMapper mapper;

    /**
     * 新增值班日志信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DailyLogVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DailyLogVo vo) {
        DailyLog entity = mapper.voToEntity(vo);
        DailyLog result = service.create(entity);
        DailyLogVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 修改值班日志信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DailyLogVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DailyLogVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DailyLog entity = mapper.voToEntity(vo);
        DailyLog result = service.update(entity);
        DailyLogVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id查询某条值班日志信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DailyLogVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DailyLog result = service.findOne(id);
        DailyLogVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询值班日志列表
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<List<DailyLogVo>> findList(
            @RequestBody DailyLogSearchVo searchVo) {
        List<DailyLog> list = service.findList(searchVo);
        List<DailyLogVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据条件查询值班日志列表——分页
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<DailyLogVo>> findPage(
            @RequestBody DailyLogSearchVo searchVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",searchVo.getPage());
        params.add("size",searchVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<DailyLog> pageResult = service.findPage(searchVo,page);
        RestPageImpl<DailyLogVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }
}
