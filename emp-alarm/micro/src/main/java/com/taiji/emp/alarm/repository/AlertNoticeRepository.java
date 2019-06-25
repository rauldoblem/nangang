package com.taiji.emp.alarm.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.alarm.entity.AlertNotice;
import com.taiji.emp.alarm.entity.QAlertNotice;
import com.taiji.emp.alarm.searchVo.AlertNoticePageSearchVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class AlertNoticeRepository extends BaseJpaRepository<AlertNotice,String> {

    //新增通知(避免重复通知)
    @Transactional
    public AlertNotice create(AlertNotice entity){
        if(!StringUtils.isEmpty(entity.getId())){
            entity.setId(null);
        }
        String alertId = entity.getAlert().getId();
        String receiveOrgId = entity.getReceiveOrgId();
        AlertNotice oldResult = findByAlertIdAndReceiveOrgId(alertId,receiveOrgId);
        if(null==oldResult){
            return super.save(entity);
        }else{
            return null; //避免重复通知，返回null
        }
    }

    //避免重复通知,根据预警id和接收单位id查找通知对象
    private AlertNotice findByAlertIdAndReceiveOrgId(String alertId,String receiveOrgId){
        Assert.hasText(alertId,"alertId 不能为空");
        Assert.hasText(receiveOrgId,"receiveOrgId 不能为空");
        QAlertNotice alertNotice = QAlertNotice.alertNotice;
        JPQLQuery<AlertNotice> query = from(alertNotice);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(alertNotice.alert.id.eq(alertId));
        builder.and(alertNotice.receiveOrgId.eq(receiveOrgId));
        query.where(builder).orderBy(alertNotice.noticeTime.desc());
        List<AlertNotice> list = findAll(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }

    //更新通知
    @Transactional
    public AlertNotice update(AlertNotice entity){
        String id = entity.getId();
        Assert.hasText(id,"id 不能为空");
        AlertNotice temp = findOne(id);
        Assert.notNull(temp,"AlertNotice 不能为null");
        BeanUtils.copyNonNullProperties(entity,temp);
        return super.save(temp);
    }

    //分页查询
    public Page<AlertNotice> findPage(AlertNoticePageSearchVo alertNoticePageVo, Pageable pageable){

        QAlertNotice alertNotice = QAlertNotice.alertNotice;
        JPQLQuery<AlertNotice> query = from(alertNotice);
        BooleanBuilder builder = new BooleanBuilder();

        String headline = alertNoticePageVo.getHeadline();
        String severityId = alertNoticePageVo.getSeverityId();
        List<String> eventTypeIds = alertNoticePageVo.getEventTypeIds();
        String source = alertNoticePageVo.getSource();
        LocalDateTime sendStartTime = alertNoticePageVo.getSendStartTime();
        LocalDateTime sendEndTime = alertNoticePageVo.getSendEndTime();
        String revOrgId = alertNoticePageVo.getRevOrgId();
        String alertId = alertNoticePageVo.getAlertId();

        if(!StringUtils.isEmpty(headline)){
            builder.and(alertNotice.alert.headline.contains(headline));
        }

        if(!StringUtils.isEmpty(severityId)){
            builder.and(alertNotice.alert.severityId.eq(severityId));
        }

        if(!CollectionUtils.isEmpty(eventTypeIds)){
            builder.and(alertNotice.alert.eventTypeId.in(eventTypeIds));
        }

        if(!StringUtils.isEmpty(source)){
            builder.and(alertNotice.alert.source.eq(source));
        }

        if(null!=sendStartTime){
            builder.and(alertNotice.noticeTime.after(sendStartTime)); //通知下发时间
        }
        if(null!=sendEndTime){
            builder.and(alertNotice.noticeTime.before(sendEndTime)); //通知下发时间
        }

        if(!StringUtils.isEmpty(revOrgId)){
            builder.and(alertNotice.receiveOrgId.eq(revOrgId));
        }

        if(!StringUtils.isEmpty(alertId)){
            builder.and(alertNotice.alert.id.eq(alertId));
        }

        query.select(Projections.bean(AlertNotice.class
                ,alertNotice.id
                ,alertNotice.alert
                ,alertNotice.receiveOrgId
                ,alertNotice.receiveOrgName
                ,alertNotice.noticeTime
                ,alertNotice.feedbackContent
                ,alertNotice.feedbackLasttime
                ,alertNotice.feedbackStatus
        )).where(builder).orderBy(alertNotice.noticeTime.desc());

        return findAll(query,pageable);
    }

    //不分页查询
    public List<AlertNotice> findList(MultiValueMap<String, Object> params){
        QAlertNotice alertNotice = QAlertNotice.alertNotice;
        JPQLQuery<AlertNotice> query = from(alertNotice);
        String alertId = null;
        if(params.containsKey("alertId")){
            alertId = params.getFirst("alertId").toString();
            Assert.hasText(alertId,"alertId 不能为空");
        }
        query.where(alertNotice.alert.id.eq(alertId))
                .orderBy(alertNotice.feedbackStatus.asc()) //按反馈顺序排列
                .orderBy(alertNotice.noticeTime.desc()); //按下发时间倒序
        return findAll(query);
    }

}
