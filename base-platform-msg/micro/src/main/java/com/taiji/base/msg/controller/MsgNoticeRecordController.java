package com.taiji.base.msg.controller;

import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.feign.IMsgNoticeRecordRestService;
import com.taiji.base.msg.mapper.CycleAvoidingMappingContext;
import com.taiji.base.msg.mapper.MsgNoticeRecordMapper;
import com.taiji.base.msg.service.MsgNoticeRecordService;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:MsgNoticeRecordController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:42</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/msg/noticeRecord")
@AllArgsConstructor
public class MsgNoticeRecordController extends BaseController implements IMsgNoticeRecordRestService {
    MsgNoticeRecordService service;
    MsgNoticeRecordMapper  mapper;

    /**
     * 根据参数获取MsgNoticeRecordVo多条记录。
     * <p>
     * <p>
     * params参数key为receiverId 当前用户Id（必选）。
     *
     * @param params 查询参数集合
     *
     * @return ResponseEntity<List<MsgNoticeRecordVo>>
     */
    @Override
    public ResponseEntity<List<MsgNoticeRecordVo>> findAll(@RequestParam MultiValueMap<String, Object> params) {
        String receiverId = "";

        if (params.containsKey("receiverId")) {
            receiverId = params.getFirst("receiverId").toString();
        }

        List<MsgNoticeRecord>   list   = service.findAllByReceiverId(receiverId);
        List<MsgNoticeRecordVo> voList = mapper.entityListToVoList(list);

        return ResponseEntity.ok(voList);
    }

    /**
     * 更新多条MsgNoticeRecordVo的readFlag为已读，vos不能为空。
     *
     * @param vos 多条消息通知
     *
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> updateMsgNoticeRecordList(
            @Validated
            @NotNull(message = "vos不能为null")
            @RequestBody List<MsgNoticeRecordVo> vos) {

        List<MsgNoticeRecord> entities = mapper.voListToEntityList(vos);
        service.updateList(entities);

        return ResponseEntity.ok().build();
    }

    /**
     * 更新MsgNoticeRecordVo的readFlag为已读，vo不能为空。
     *
     * @param vo 多条消息通知
     * @param id 消息id
     *
     * @return ResponseEntity<MsgNoticeRecordVo>
     */
    @Override
    public ResponseEntity<MsgNoticeRecordVo> updateMsgNoticeRecord(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody MsgNoticeRecordVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        MsgNoticeRecord   tempEntity = mapper.voToEntity(vo);
        MsgNoticeRecord   entity     = service.update(tempEntity, id);
        MsgNoticeRecordVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }
}
