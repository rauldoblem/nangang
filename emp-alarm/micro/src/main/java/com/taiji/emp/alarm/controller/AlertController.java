package com.taiji.emp.alarm.controller;

import com.taiji.emp.alarm.entity.Alert;
import com.taiji.emp.alarm.feign.IAlertRestService;
import com.taiji.emp.alarm.mapper.AlertMapper;
import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.emp.alarm.service.AlertService;
import com.taiji.emp.alarm.vo.AlertVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
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

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/alerts")
public class AlertController extends BaseController implements IAlertRestService{

    AlertService service;
    AlertMapper mapper;

    /**
     * 新增预警信息 AlertVo
     *
     * @param vo
     * @return ResponseEntity<AlertVo>
     */
    @Override
    public ResponseEntity<AlertVo> create(
            @Validated
            @NotNull(message = "AlertVo 不能为null")
            @RequestBody AlertVo vo) {
        Alert entity = mapper.voToEntity(vo);
        Alert result = service.create(entity);
        AlertVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id 获取预警信息AlertVo
     *
     * @param id id不能为空
     * @return ResponseEntity<AlertVo>
     */
    @Override
    public ResponseEntity<AlertVo> findOne(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Alert result = service.findOne(id);
        AlertVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新预警信息AlertVo
     *
     * @param vo
     * @param id 要更新AlertVo id
     * @return ResponseEntity<AlertVo>
     */
    @Override
    public ResponseEntity<AlertVo> update(
            @Validated
            @NotNull(message = "AlertVo 不能为null")
            @RequestBody AlertVo vo,
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Alert entity = mapper.voToEntity(vo);
        Alert result = service.update(entity,id);
        AlertVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id删除一条预警信息
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据参数获取AlertVo多条记录,分页信息
     * 查询参数 headline(可选),severityId(可选),eventTypeIds(可选),source(可选),sendStartTime,sendEndTime,noticeFlags,orgId
     * page,size
     *
     * @param alertPageVo
     * @return ResponseEntity<RestPageImpl < AlertVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<AlertVo>> findPage(
            @NotNull(message = "alertPageVo 不能为null")
            @RequestBody AlertPageSearchVo alertPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",alertPageVo.getPage());
        map.add("size",alertPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<Alert> pageResult = service.findPage(alertPageVo,pageable);
        RestPageImpl<AlertVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);

        return ResponseEntity.ok(resultVo);
    }
}
