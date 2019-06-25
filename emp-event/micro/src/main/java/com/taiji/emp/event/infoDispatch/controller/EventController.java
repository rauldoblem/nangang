package com.taiji.emp.event.infoDispatch.controller;

import com.taiji.emp.event.infoDispatch.entity.Event;
import com.taiji.emp.event.infoDispatch.feign.IEventRestService;
import com.taiji.emp.event.infoDispatch.mapper.EventMapper;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.emp.event.infoDispatch.service.EventService;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/events")
public class EventController extends BaseController implements IEventRestService{

    @Autowired
    EventService service;
    @Autowired
    EventMapper mapper;

    /**
     * 生成新事件
     *
     * @param vo
     * @return ResponseEntity<EventVo>
     */
    @Override
    public ResponseEntity<EventVo> createEvent(
            @Validated
            @NotNull(message = "EventVo 不能为null")
            @RequestBody EventVo vo) {
        Event entity = mapper.voToEntity(vo);
        Event result = service.create(entity);
        EventVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id获取单个事件信息
     *
     * @param id
     * @return ResponseEntity<EventVo>
     */
    @Override
    public ResponseEntity<EventVo> findOne(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Event result = service.findEventById(id);
        EventVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新事件
     *
     * @param vo
     * @param id 事件id
     * @return ResponseEntity<EventVo>
     */
    @Override
    public ResponseEntity<EventVo> updateEvent(
            @Validated
            @NotNull(message = "EventVo 不能为null")
            @RequestBody EventVo vo,
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Event entity = mapper.voToEntity(vo);
        Event result = service.update(entity,id);
        EventVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据条件查询事件列表-分页
     *
     * @param pageVo
     * @return ResponseEntity<RestPageImpl < EventVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<EventVo>> search(@RequestBody EventPageVo pageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",pageVo.getPage());
        map.add("size",pageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<Event> pageResult = service.findPage(pageVo,pageable);
        RestPageImpl<EventVo> pageResultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(pageResultVo, HttpStatus.OK);
    }

    /**
     * 根据条件查询事件列表-不分页
     *
     * @param pageVo
     * @return ResponseEntity<List < EventVo>>
     */
    @Override
    public ResponseEntity<List<EventVo>> searchAll(@RequestBody EventPageVo pageVo) {
        List<Event> listResult = service.findList(pageVo);
        List<EventVo> pageResultVo = mapper.entityListToVoList(listResult);
        return new ResponseEntity<>(pageResultVo, HttpStatus.OK);
    }

    /**
     * 事件处置结束/评估/归档，将事件处置状态置为已结束/已评估/已归档状态
     *
     * @param vo
     * @return ResponseEntity<EventVo>
     */
    @Override
    public ResponseEntity<EventVo> operate(
            @Validated
            @NotNull(message = "EventVo 不能为null")
            @RequestBody EventVo vo) {
        Event entity = mapper.voToEntity(vo);
        Event result = service.operateEvent(entity);
        EventVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

}
