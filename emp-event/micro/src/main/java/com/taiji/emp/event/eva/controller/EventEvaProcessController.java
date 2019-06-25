package com.taiji.emp.event.eva.controller;


import com.taiji.emp.event.eva.entity.EventEvaProcess;
import com.taiji.emp.event.eva.feign.IEventEvaProcessRestService;
import com.taiji.emp.event.eva.mapper.EventEvaProcessMapper;
import com.taiji.emp.event.eva.service.EventEvaProcessService;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/process")
public class EventEvaProcessController extends BaseController implements IEventEvaProcessRestService {

    private EventEvaProcessService service;
    private EventEvaProcessMapper mapper;

    /**
     * 根据参数获取 EventEvaProcessVo 多条记录 不分页
     *  @param eventId 事件ID(可选),
     *  @return ResponseEntity<List<EventEvaProcessVo>>
     */
    @Override
    public ResponseEntity<List<EventEvaProcessVo>> findList(@RequestParam(value = "eventId")String eventId) {
        List<EventEvaProcess> list = service.findProcess(eventId);
        List<EventEvaProcessVo> voList = mapper.entityListToVoList(list);

        return ResponseEntity.ok(voList);
    }


    /**
     * 新增过程再现 EventEvaProcessVo，EventEvaProcessVo 不能为空
     * @param vo
     * @return ResponseEntity<EventEvaProcessVo>
     */
    @Override
    public ResponseEntity<EventEvaProcessVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaProcessVo vo) {
        EventEvaProcess entity = mapper.voToEntity(vo);
        EventEvaProcess result = service.create(entity);
        EventEvaProcessVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新过程再现 EventEvaProcessVo，EventEvaProcessVo 不能为空
     * @param vo,
     * @param id 要更新 EventEvaProcessVo 的 id
     * @return ResponseEntity<EventEvaProcessVo>
     */
    @Override
    public ResponseEntity<EventEvaProcessVo> update(
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaProcessVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        EventEvaProcess entity = mapper.voToEntity(vo);
        EventEvaProcess result = service.update(entity,id);
        EventEvaProcessVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }



    /**
     * 根据id 获取过程再现 ContactVo
     * @param id id不能为空
     * @return ResponseEntity<ContactVo>
     */
    @Override
    public ResponseEntity<EventEvaProcessVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        EventEvaProcess result = service.findOne(id);
        EventEvaProcessVo resultVo = mapper.entityToVo(result);
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
