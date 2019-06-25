package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.Notice;
import com.taiji.emp.base.entity.QNotice;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

@Repository
@Transactional(readOnly = true)
public class NoticeRepository extends BaseJpaRepository<Notice,String>{

    /**
     * 新增或编辑--通知公告记录
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public Notice save(Notice entity){
        Assert.notNull(entity,"notice对象不能为null");
        Notice result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            Notice temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * @param params
     * @param pageable
     * @return
     */
    public Page<Notice> findPage(MultiValueMap<String,Object>params, Pageable pageable){
        JPQLQuery<Notice> query = buildQuery(params);
        return findAll(query,pageable);
    }


    private JPQLQuery<Notice> buildQuery(MultiValueMap<String,Object> params){
        QNotice notice = QNotice.notice;
        JPQLQuery<Notice> query = from(notice);
        BooleanBuilder builder = new BooleanBuilder();

        if (params.containsKey("title")){
            builder.and(notice.title.contains(params.getFirst("title").toString()));
        }

        if (params.containsKey("noticeTypeId")){
            builder.and(notice.noticeTypeId.eq(params.getFirst("noticeTypeId").toString()));
        }

        if (params.containsKey("sendStartTime") && params.containsKey("sendEndTime")){
            //发送起止时间
            builder.and(notice.sendTime.between(DateUtil.strToLocalDateTime(params.getFirst("sendStartTime").toString()),DateUtil.strToLocalDateTime(params.getFirst("sendEndTime").toString())));
        }

        if (!params.containsKey("sendStartTime") && params.containsKey("sendEndTime")){
            //发送结束时间
            builder.and(notice.sendTime.lt(DateUtil.strToLocalDateTime(params.getFirst("sendEndTime").toString())));
        }

        if (params.containsKey("sendStartTime") && !params.containsKey("sendEndTime")){
            //发送开始时间
            builder.and(notice.sendTime.gt(DateUtil.strToLocalDateTime(params.getFirst("sendStartTime").toString())));
        }

        if (params.containsKey("sendStatus")){
            builder.and(notice.sendStatus.eq(params.getFirst("sendStatus").toString()));
        }

        if (params.containsKey("buildOrgId")){
            builder.and(notice.buildOrgId.eq(params.getFirst("buildOrgId").toString()));
        }

        builder.and(notice.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Notice.class
                        ,notice.id
                        ,notice.title
                        ,notice.createBy
                        ,notice.createTime
                        ,notice.updateBy
                        ,notice.updateTime
                        ,notice.noticeTypeId
                        ,notice.noticeTypeName
                        ,notice.content
                        ,notice.buildOrgId
                        ,notice.buildOrgName
                        ,notice.sendStatus
                        ,notice.sendTime
                        ,notice.notes
                )
        ).where(builder).orderBy(notice.updateTime.desc());
        return query;
    }


}
