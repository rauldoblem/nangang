package com.taiji.base.msg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.enums.ReadFlagEnum;
import com.taiji.base.msg.feign.IMsgNoticeRestService;
import com.taiji.base.msg.mapper.MsgNoticeMapper;
import com.taiji.base.msg.queue.MsgSendService;
import com.taiji.base.msg.service.MsgNoticeService;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:MsgNoticeController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:26</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/msg/notice")
@AllArgsConstructor
public class MsgNoticeController extends BaseController implements IMsgNoticeRestService {

    MsgNoticeService service;
    MsgNoticeMapper mapper;
    ObjectMapper objectMapper;
    MsgSendService msgSendService;

    /**
     * 新增MsgNoticeVo，MsgNoticeVo不能为空。
     *
     * @param vo 消息vo
     *
     * @return ResponseEntity<MsgNoticeVo>
     */
    @Override
    public ResponseEntity<MsgNoticeVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody MsgNoticeVo vo) {
        List<Receiver> receivers = vo.getReceivers();

        MsgNotice   tempEntity = mapper.voToEntity(vo);

        if(!CollectionUtils.isEmpty(receivers))
        {
            List<MsgNoticeRecord> msgNoticeRecordList = new ArrayList<>();
            for (Receiver receiver: receivers) {
                MsgNoticeRecord msgNoticeRecord = new MsgNoticeRecord();
                msgNoticeRecord.setReceiverId(receiver.getId());
                msgNoticeRecord.setReceiverName(receiver.getName());
                msgNoticeRecord.setReadFlag(ReadFlagEnum.UNREAD.getCode());
                msgNoticeRecordList.add(msgNoticeRecord);
            }
            tempEntity.setMsgNoticeRecordList(msgNoticeRecordList);
        }

        tempEntity.setDelFlag(DelFlagEnum.NORMAL.getCode());

        MsgNotice   entity     = service.save(tempEntity);
        MsgNoticeVo tempVo     = mapper.entityToVo(entity);

        msgSendService.sendMsgNotice(entity);

        return ResponseEntity.ok(tempVo);
    }
}
