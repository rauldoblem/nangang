package com.taiji.base.msg.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.entity.QMsgNoticeRecord;
import com.taiji.base.msg.enums.ReadFlagEnum;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Title:MsgNoticeRecordRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 17:14</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class MsgNoticeRecordRepository extends BaseJpaRepository<MsgNoticeRecord,String> {


    public List<MsgNoticeRecord> findAllByReceiverId(String receiverId){
        QMsgNoticeRecord qMsgNoticeRecord = QMsgNoticeRecord.msgNoticeRecord;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMsgNoticeRecord.receiverId.eq(receiverId));

        builder.and(qMsgNoticeRecord.readFlag.eq(ReadFlagEnum.UNREAD.getCode()));

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        return findAll(builder,sort);
    }

    @Override
    @Transactional
    public MsgNoticeRecord save(MsgNoticeRecord entity)
    {
        Assert.notNull(entity, "MsgNoticeRecord must not be null!");

        MsgNoticeRecord result;
        if(!StringUtils.hasText(entity.getId()))
        {
            result = super.save(entity);
        }
        else
        {
            MsgNoticeRecord tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }


    @Transactional
    public List<MsgNoticeRecord> saveList(Iterable<MsgNoticeRecord> entities) {
        List<MsgNoticeRecord> result = new ArrayList<>();
        if (entities == null) {
            return result;
        } else {

            for (MsgNoticeRecord entity : entities) {
                result.add(save(entity));
            }

            return result;
        }
    }
}

