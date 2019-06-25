package com.taiji.base.msg.service;

import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.repository.MsgNoticeRecordRepository;
import com.taiji.base.msg.repository.MsgNoticeRepository;
import com.taiji.micro.common.service.BaseService;
import com.taiji.micro.common.utils.BeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:MsgNoticeService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/31 11:31</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class MsgNoticeService extends BaseService<MsgNotice,String> {
    MsgNoticeRepository repository;
    MsgNoticeRecordRepository msgNoticeRecordRepository;

    @Transactional
    public MsgNotice save(MsgNotice entity)
    {
        Assert.notNull(entity, "MsgNotice must not be null!");

        MsgNotice result = repository.save(entity);
        if (!CollectionUtils.isEmpty(entity.getMsgNoticeRecordList()))
        {
            List<MsgNoticeRecord> msgNoticeRecordList = entity.getMsgNoticeRecordList();
            for (MsgNoticeRecord msgNoticeRecord : msgNoticeRecordList)
            {
//                MsgNotice temp = repository.findOne(result.getId());
                msgNoticeRecord.setMsgNotice(result);
            }

            msgNoticeRecordRepository.saveList(msgNoticeRecordList);
        }

        return result;
    }
}
