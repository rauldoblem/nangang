package com.taiji.emp.alarm.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.alarm.common.constant.AlarmGlobal;
import com.taiji.emp.alarm.entity.Alert;
import com.taiji.emp.alarm.entity.QAlert;
import com.taiji.emp.alarm.searchVo.AlertPageSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class AlertRepository extends BaseJpaRepository<Alert,String> {

    @Override
    @Transactional
    public Alert save(Alert entity){
        Assert.notNull(entity,"Alert对象不能为 null");
        Alert result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){
            result = super.save(entity);
        }else{
            Alert temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //分页查询
    public Page<Alert> findPage(AlertPageSearchVo alertPageVo, Pageable pageable){

        QAlert alert = QAlert.alert;
        JPQLQuery<Alert> query = from(alert);
        BooleanBuilder builder = new BooleanBuilder();

        String headline = alertPageVo.getHeadline();
        String severityId = alertPageVo.getSeverityId();
        List<String> eventTypeIds = alertPageVo.getEventTypeIds();
        String source = alertPageVo.getSource();
        LocalDateTime sendStartTime = alertPageVo.getSendStartTime();
        LocalDateTime sendEndTime = alertPageVo.getSendEndTime();
        List<String> noticeFlags = alertPageVo.getNoticeFlags();
        String orgId = alertPageVo.getOrgId();

        if(!StringUtils.isEmpty(headline)){
            builder.and(alert.headline.contains(headline));
        }

        if(!StringUtils.isEmpty(severityId)){
            builder.and(alert.severityId.eq(severityId));
        }

        if(!CollectionUtils.isEmpty(eventTypeIds)){
            builder.and(alert.eventTypeId.in(eventTypeIds));
        }

        if(!StringUtils.isEmpty(source)){
            builder.and(alert.source.eq(source));
        }

        if(null!=sendStartTime){
            builder.and(alert.sendTime.after(sendStartTime)); //发布时间
        }
        if(null!=sendEndTime){
            builder.and(alert.sendTime.before(sendEndTime)); //发布时间
        }

        /**
         * 对这个判断做修改：
         *      noticeFlags={0} 时并且IS_NOTICE = 1
         *      noticeFlags={1,2,3} 时，or IS_NOTICE = 0
         */
        if(!CollectionUtils.isEmpty(noticeFlags)){
            if(noticeFlags.size() == 1 && AlarmGlobal.ALAERT_FB_NOT_START.equals(noticeFlags.get(0))){
                builder.and(alert.alertStatus.in(noticeFlags).
                            and(alert.isNotice.eq(AlarmGlobal.Alert_IS_NOTICE_NO)));
            }else{
                builder.and(alert.alertStatus.in(noticeFlags).
                            or(alert.isNotice.eq(AlarmGlobal.Alert_Notice_NOT_ACCEPT)));
            }

        }

        if(!StringUtils.isEmpty(orgId)){
            builder.and(alert.createOrgId.eq(orgId));
        }

        builder.and(alert.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Alert.class
                ,alert.id
                ,alert.source  //来源：（1：系统录入，2：天气预警，3：设备预警）
                ,alert.headline //标题
                ,alert.eventId  //eventId -- 预留：关联事件id
                ,alert.eventTypeId // 预警类型 -- 同事件类型（预留）
                ,alert.eventTypeName //预警类型名称
                ,alert.severityId // 预警级别Id（预留）
                ,alert.severityName //预警级别名称
                ,alert.sendTime //预警发布时间
                ,alert.alertStatus //预警处理状态：0：未处理；1：已忽略；2：已通知；3：已办结
                ,alert.isNotice //是否通知（0：已通知；1：未通知）
        )).where(builder).orderBy(alert.updateTime.desc());

        return findAll(query,pageable);
    }
}
