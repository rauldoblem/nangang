package com.taiji.emp.event.infoConfig.controller;

import com.taiji.emp.event.infoConfig.entity.AcceptInform;
import com.taiji.emp.event.infoConfig.feign.IAcceptInformService;
import com.taiji.emp.event.infoConfig.mapper.AcceptInformMapper;
import com.taiji.emp.event.infoConfig.service.AccInfoService;
import com.taiji.emp.event.infoConfig.vo.AcceptInformSearchVo;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
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
@RequestMapping("/api/acceptInforms")
public class AcceptInformController extends BaseController implements IAcceptInformService{

    AccInfoService service;
    AcceptInformMapper mapper;

    /**
     * 新增通知单位AcceptInformVo
     * @param vo
     * @return ResponseEntity<AcceptInformVo>
     */
    @Override
    public ResponseEntity<AcceptInformVo> create(
            @Validated
            @NotNull(message = "acceptInformVo 不能为null")
            @RequestBody AcceptInformVo vo) {
        AcceptInform entity = mapper.voToEntity(vo);
        AcceptInform result = service.create(entity);
        AcceptInformVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id 通知单位AcceptInformVo
     * @param id id不能为空
     * @return ResponseEntity<AcceptInformVo>
     */
    @Override
    public ResponseEntity<AcceptInformVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        AcceptInform result = service.findOne(id);
        AcceptInformVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新通知单位AcceptInformVo
     * @param vo,
     * @param id 要更新AcceptInformVo id
     * @return ResponseEntity<AcceptInformVo>
     */
    @Override
    public ResponseEntity<AcceptInformVo> update(
            @Validated
            @NotNull(message = "acceptInformVo 不能为null")
            @RequestBody AcceptInformVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        AcceptInform entity = mapper.voToEntity(vo);
        AcceptInform result = service.update(entity,id);
        AcceptInformVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> delete(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据查询条件查询AcceptInformVo list
     * 参数keys：eventTypeId
     * @param params
     * @return ResponseEntity<List<AcceptInformVo>>
     */
    @Override
    public ResponseEntity<List<AcceptInformVo>> searchAll(
            @RequestParam MultiValueMap<String,Object> params) {
        List<AcceptInform> resultList = service.searchAll(params);
        List<AcceptInformVo> resultVoList = mapper.entityListToVoList(resultList);
        return new ResponseEntity<>(resultVoList, HttpStatus.OK);
    }

    /**
     * 根据条件查询通知单位列表
     * @param acceptInformVo
     * @return
     */
    @Override
    public ResponseEntity<List<AcceptInformVo>> searchAcceptInforms(
            @Validated
            @NotNull(message = "acceptInformVo 不能为null")
            @RequestBody AcceptInformSearchVo acceptInformVo) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("eventTypeId",acceptInformVo.getEventTypeId());
        List<AcceptInform> resultList = service.searchAll(params);
        List<AcceptInformVo> resultVoList = mapper.entityListToVoList(resultList);
        return new ResponseEntity<>(resultVoList, HttpStatus.OK);
    }

}
