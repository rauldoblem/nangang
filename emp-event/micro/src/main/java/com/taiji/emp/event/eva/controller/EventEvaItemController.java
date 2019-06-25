package com.taiji.emp.event.eva.controller;

import com.taiji.emp.event.eva.entity.EventEvaItem;
import com.taiji.emp.event.eva.feign.IEventEvaItemRestService;
import com.taiji.emp.event.eva.mapper.EventEvaItemMapper;
import com.taiji.emp.event.eva.service.EventEvaItemService;
import com.taiji.emp.event.eva.vo.EventEvaItemVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
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
@RequestMapping("/api/evaItem")
public class EventEvaItemController extends BaseController implements IEventEvaItemRestService {

    EventEvaItemService service;
    EventEvaItemMapper mapper;

    /**
     * 新增事件评估项
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<EventEvaItemVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaItemVo vo) {
        EventEvaItem entity = mapper.voToEntity(vo);
        EventEvaItem result = service.create(entity);
        EventEvaItemVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 删除事件评估项信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 修改事件评估项信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<EventEvaItemVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaItemVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        EventEvaItem entity = mapper.voToEntity(vo);
        EventEvaItem result = service.update(entity);
        EventEvaItemVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 获取单条事件评估项信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<EventEvaItemVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        EventEvaItem entity = service.findOne(id);
        EventEvaItemVo evaItemVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(evaItemVo);
    }

    /**
     * 根据事件类型获取事件评估项列表
     * @param eventTypeId
     * @return
     */
    @Override
    public ResponseEntity<List<EventEvaItemVo>> findListByEventTypeId(String eventTypeId) {
        List<EventEvaItem> list = service.findListByEventTypeId(eventTypeId);
        List<EventEvaItemVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
