package com.taiji.base.msg.repository;

import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * <p>Title:MsgNoticeRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 17:13</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class MsgNoticeRepository extends BaseJpaRepository<MsgNotice,String> {

    @Override
    @Transactional
    public MsgNotice save(MsgNotice entity)
    {
        Assert.notNull(entity, "MsgNotice must not be null!");

        MsgNotice result;
        if(!StringUtils.hasText(entity.getId()))
        {
            result = super.saveAndFlush(entity);
        }
        else
        {
            MsgNotice tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.saveAndFlush(tempEntity);
        }

        return result;
    }
}
