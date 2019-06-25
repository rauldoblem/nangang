package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.NoticeReceiveOrg;
import com.taiji.emp.base.entity.QNoticeReceiveOrg;
import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class NoticeReceiveOrgRepository extends BaseJpaRepository<NoticeReceiveOrg,String> {

    /**
     * 通知公告发送
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public NoticeReceiveOrg save(NoticeReceiveOrg entity){
        Assert.notNull(entity,"noticeReceiveOrg对象不能为null");
        NoticeReceiveOrg result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            NoticeReceiveOrg temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据 noticeId 查询通知公告反馈信息
     * @param noticeId
     * @return
     */
    public NoticeReceiveOrg findRecOne(String noticeId){
        QNoticeReceiveOrg noticeReceiveOrg = QNoticeReceiveOrg.noticeReceiveOrg;
        JPQLQuery<NoticeReceiveOrg> query = from(noticeReceiveOrg);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(noticeId)){
            builder.and(noticeReceiveOrg.notice.id.eq(noticeId));
        }
        NoticeReceiveOrg entity = query.where(builder).fetchOne();
        return entity;
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @param pageable
     */
    public Page<NoticeReceiveOrg> findNoticeReceivePage(NoticeReceiveVo noticeReceiveVo, Pageable pageable){
        JPQLQuery<NoticeReceiveOrg> query = buildQuery(noticeReceiveVo);
        return findAll(query,pageable);
    }

    private JPQLQuery<NoticeReceiveOrg> buildQuery(NoticeReceiveVo noticeReceiveVo){
        QNoticeReceiveOrg noticeReceiveOrg = QNoticeReceiveOrg.noticeReceiveOrg;
        JPQLQuery<NoticeReceiveOrg> query = from(noticeReceiveOrg);
        BooleanBuilder builder = new BooleanBuilder();

        String title = noticeReceiveVo.getTitle();
        String noticeTypeId = noticeReceiveVo.getNoticeTypeId();
        String sendTimeStart = noticeReceiveVo.getSendStartTime();
        String sendTimeEnd = noticeReceiveVo.getSendEndTime();
        List<String> buildOrgIds = noticeReceiveVo.getBuildOrgIds();
        String revOrgId = noticeReceiveVo.getRevOrgId();

        if (StringUtils.hasText(title)){
            builder.and(noticeReceiveOrg.notice.title.contains(title));
        }
        if (StringUtils.hasText(noticeTypeId)){
            builder.and(noticeReceiveOrg.notice.noticeTypeId.eq(noticeTypeId));
        }
        if (StringUtils.hasText(sendTimeStart) && StringUtils.hasText(sendTimeEnd)){
            builder.and(noticeReceiveOrg.notice.sendTime
                    .between(DateUtil.strToLocalDateTime(sendTimeStart),DateUtil.strToLocalDateTime(sendTimeEnd)));
        }
        if (StringUtils.hasText(sendTimeStart) && !StringUtils.hasText(sendTimeEnd)){
            builder.and(noticeReceiveOrg.notice.sendTime.gt(DateUtil.strToLocalDateTime(sendTimeStart)));
        }
        if (StringUtils.hasText(sendTimeEnd) && !StringUtils.hasText(sendTimeStart)){
            builder.and(noticeReceiveOrg.notice.sendTime.lt(DateUtil.strToLocalDateTime(sendTimeEnd)));
        }
        if (null!= buildOrgIds && buildOrgIds.size() > 0){
            builder.and(noticeReceiveOrg.notice.buildOrgId.in(buildOrgIds));
        }
        if (StringUtils.hasText(revOrgId)){
            builder.and(noticeReceiveOrg.receiveOrgId.eq(revOrgId));
        }

        builder.and(noticeReceiveOrg.notice.sendStatus.eq("1"));
        query.select(
                Projections.bean(NoticeReceiveOrg.class
                        ,noticeReceiveOrg.id
                        ,noticeReceiveOrg.notice
                        ,noticeReceiveOrg.sendBy
                        ,noticeReceiveOrg.receiveOrgId
                        ,noticeReceiveOrg.isFeedback
                        ,noticeReceiveOrg.sendTime
        )).where(builder).orderBy(noticeReceiveOrg.sendTime.desc());
        return query;
    }

    public NoticeReceiveOrg findByNoticeRecId(String noticeRecId) {
        QNoticeReceiveOrg noticeReceiveOrg = QNoticeReceiveOrg.noticeReceiveOrg;
        JPQLQuery<NoticeReceiveOrg> query = from(noticeReceiveOrg);
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(noticeRecId)) {
            builder.and(noticeReceiveOrg.id.eq(noticeRecId));
        }
        NoticeReceiveOrg result = query.select(
                Projections.bean(NoticeReceiveOrg.class
                        , noticeReceiveOrg.id
                        , noticeReceiveOrg.notice
                        ,noticeReceiveOrg.sendBy
                        ,noticeReceiveOrg.receiveOrgId
                        ,noticeReceiveOrg.isFeedback
                        ,noticeReceiveOrg.sendTime
                )).where(builder).orderBy(noticeReceiveOrg.sendTime.desc()).fetchOne();
        return result;
    }

    public List<NoticeReceiveOrg> finList(String noticeId) {
        QNoticeReceiveOrg noticeReceiveOrg = QNoticeReceiveOrg.noticeReceiveOrg;
        JPQLQuery<NoticeReceiveOrg> query = from(noticeReceiveOrg);
        BooleanBuilder builder = new BooleanBuilder();
        Assert.hasText(noticeId,"noticeId不能为空字符串");
        if (StringUtils.hasText(noticeId)) {
            builder.and(noticeReceiveOrg.notice.id.eq(noticeId));
        }
        query.where(builder);
        return findAll(query);
    }
}
