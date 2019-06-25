package com.taiji.emp.alarm.controller;

import com.taiji.emp.alarm.entity.AlertNotice;
import com.taiji.emp.alarm.entity.NoticeFeedback;
import com.taiji.emp.alarm.feign.IAlertNoticeRestService;
import com.taiji.emp.alarm.mapper.AlertNoticeMapper;
import com.taiji.emp.alarm.mapper.NoticeFeedbackMapper;
import com.taiji.emp.alarm.searchVo.AlertNoticePageSearchVo;
import com.taiji.emp.alarm.service.AlertNoticeService;
import com.taiji.emp.alarm.service.NoticeFeedbackService;
import com.taiji.emp.alarm.vo.AlertNoticeVo;
import com.taiji.emp.alarm.vo.NoticeFeedbackVo;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/alertNotices")
public class AlertNoticeController extends BaseController implements IAlertNoticeRestService{

    AlertNoticeService service;
    AlertNoticeMapper mapper;
    NoticeFeedbackService feedbackService;
    NoticeFeedbackMapper feedbackMapper;


    /**
     * 新增单条 预警通知信息 AlertNoticeVo
     *
     * @param vo
     * @return ResponseEntity<AlertNoticeVo>
     */
    @Override
    public ResponseEntity<AlertNoticeVo> create(
            @Validated
            @NotNull(message = "AlertNoticeVo 不能为null")
            @RequestBody AlertNoticeVo vo) {
        AlertNotice entity = mapper.voToEntity(vo);
        AlertNotice result = service.createOne(entity);
        AlertNoticeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取 单条预警通知信息 AlertNoticeVo
     *
     * @param id
     * @return ResponseEntity<AlertNoticeVo>
     */
    @Override
    public ResponseEntity<AlertNoticeVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        AlertNotice result = service.findOne(id);
        AlertNoticeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新单条 预警通知信息 AlertNoticeVo
     *
     * @param vo
     * @param id
     * @return ResponseEntity<AlertNoticeVo>
     */
    @Override
    public ResponseEntity<AlertNoticeVo> update(
            @Validated
            @NotNull(message = "AlertNoticeVo 不能为null")
            @RequestBody AlertNoticeVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        AlertNotice entity = mapper.voToEntity(vo);
        AlertNotice result = service.update(entity,id);
        AlertNoticeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 新增多条 预警通知信息 AlertNoticeVo
     *
     * @param vos
     * @return ResponseEntity<List < AlertNoticeVo>>
     */
    @Override
    public ResponseEntity<List<AlertNoticeVo>> createList(
            @NotNull(message = "vos 不能为null")
            @RequestBody List<AlertNoticeVo> vos) {
        List<AlertNotice> entities = mapper.voListToEntityList(vos);
        List<AlertNotice> resultList = service.createList(entities);
        List<AlertNoticeVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 根据条件查询预警通知信息 AlertNoticeVo  -- 分页
     * 预警通知接收列表使用
     *
     * @param alertNoticePageVo
     * @return ResponseEntity<List < AlertNoticeVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<AlertNoticeVo>> findPage(
            @NotNull(message = "alertNoticePageVo 不能为null")
            @RequestBody AlertNoticePageSearchVo alertNoticePageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",alertNoticePageVo.getPage());
        map.add("size",alertNoticePageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<AlertNotice> pageResult = service.findPage(alertNoticePageVo,pageable);
        RestPageImpl<AlertNoticeVo> pageVoResult = mapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(pageVoResult);
    }

    /**
     * 根据条件查询预警通知信息 AlertNoticeVo  -- 不分页
     *
     * @param params
     * @return ResponseEntity<List < AlertNoticeVo>>
     */
    @Override
    public ResponseEntity<List<AlertNoticeVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<AlertNotice> resultList = service.findList(params);
        List<AlertNoticeVo> voResultList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(voResultList);
    }

    /**
     * 保存预警通知反馈对象信息 NoticeFeedbackVo
     *
     * @param vo
     * @return ResponseEntity<NoticeFeedbackVo>
     */
    @Override
    public ResponseEntity<NoticeFeedbackVo> createFeedBack(
            @Validated
            @NotNull(message = "NoticeFeedbackVo 不能为null")
            @RequestBody NoticeFeedbackVo vo) {
        NoticeFeedback entity = feedbackMapper.voToEntity(vo);
        NoticeFeedback result = feedbackService.create(entity);
        NoticeFeedbackVo resultVo = feedbackMapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }
}
