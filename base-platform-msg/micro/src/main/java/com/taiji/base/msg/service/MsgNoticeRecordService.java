package com.taiji.base.msg.service;

import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.repository.MsgNoticeRecordRepository;
import com.taiji.base.msg.repository.MsgNoticeRepository;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>Title:MsgNoticeRecordService.java</p >
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
public class MsgNoticeRecordService extends BaseService<MsgNoticeRecord,String> {
    MsgNoticeRecordRepository repository;
    MsgNoticeRepository       noticeRepository;

    /**
     * 根据参数获取MsgNoticeRecordVo多条记录。
     *
     * @param receiverId 接收者
     * @return List<MsgNoticeRecord>
     */
    public List<MsgNoticeRecord> findAllByReceiverId(String receiverId){
        return repository.findAllByReceiverId(receiverId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateList(List<MsgNoticeRecord>  entities)
    {
//        for (MsgNoticeRecord entity: entities)
//        {
//            if(StringUtils.hasText(entity.getMsgNotice().getId()))
//            {
//                entity.setMsgNotice(null);
//            }
//        }
        repository.saveList(entities);
    }

    public MsgNoticeRecord update(MsgNoticeRecord entity, String id)
    {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id, "id不能为null或空字符串!");

        if(!id.equals(entity.getId()))
        {
            String msg = String.format("entity id属性值：%s和参数id值:%s不一致", entity.getId(),id);
            throw new IllegalArgumentException(msg);
        }

        return repository.save(entity);
    }
}
