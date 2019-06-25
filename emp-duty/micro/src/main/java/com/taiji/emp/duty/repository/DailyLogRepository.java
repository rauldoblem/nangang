package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.dailylog.DailyLog;
import com.taiji.emp.duty.entity.dailylog.QDailyLog;
import com.taiji.emp.duty.searchVo.DailyLogSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DailyLogRepository extends BaseJpaRepository<DailyLog,String> {

    /**
     * 新增或修改值班日志信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public DailyLog save(DailyLog entity){
        Assert.notNull(entity,"entity对象不能为空");
        DailyLog result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else{
            DailyLog temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据条件查询值班日志列表——分页
     * @param searchVo
     * @param pageable
     * @return
     */
    public Page<DailyLog> findPage(DailyLogSearchVo searchVo, Pageable pageable) {
        JPQLQuery<DailyLog> query = buildQuery(searchVo);
        return findAll(query,pageable);
    }

    /**
     * 根据条件查询值班日志列表
     * @param searchVo
     * @return
     */
    public List<DailyLog> findList(DailyLogSearchVo searchVo) {
        String isLastlyFlag = searchVo.getIsLastlyFlag();
        JPQLQuery<DailyLog> query = null;
        if (!StringUtils.isEmpty(isLastlyFlag)){
            if(isLastlyFlag.equals("0")){
                query = buildQuery(searchVo);
            }else if (isLastlyFlag.equals("1")){
                LocalDateTime nowTime = searchVo.getNowTime();
                query = buildQueryByLastlyFlag(searchVo,nowTime);
            }
        }
        return findAll(query);
    }

    private JPQLQuery<DailyLog> buildQuery(DailyLogSearchVo searchVo){
        QDailyLog dailyLog = QDailyLog.dailyLog;

        JPQLQuery<DailyLog> query = from(dailyLog);
        BooleanBuilder builder = new BooleanBuilder();

        List<String> affirtTypeIds = searchVo.getAffirtTypeIds();
        List<String> treatStatuses = searchVo.getTreatStatuses();
        String orgId = searchVo.getOrgId();
        String inputerName = searchVo.getInputerName();

        if (null != affirtTypeIds && affirtTypeIds.size() > 0){
            builder.and(dailyLog.affirtTypeId.in(affirtTypeIds));
        }

        if (null != treatStatuses && treatStatuses.size() > 0){
            builder.and(dailyLog.treatStatus.in(treatStatuses));
        }
        if (StringUtils.hasText(orgId)){
            builder.and(dailyLog.orgId.eq(orgId));
        }
        if (StringUtils.hasText(inputerName)){
            builder.and(dailyLog.inputerName.contains(inputerName));
        }
        builder.and(dailyLog.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(dailyLog.updateTime.desc());
        return query;
    }

    private JPQLQuery<DailyLog> buildQueryByLastlyFlag(DailyLogSearchVo searchVo,LocalDateTime nowTime){
        QDailyLog dailyLog = QDailyLog.dailyLog;
        JPQLQuery<DailyLog> query = from(dailyLog);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = searchVo.getOrgId();
        LocalDateTime time = nowTime.minusDays(1);
        //昨天
        LocalDateTime yesterday = time.minusHours(time.getHour()).minusMinutes(time.getMinute()).minusSeconds(time.getSecond());
        if (StringUtils.hasText(orgId)){
            builder.and(dailyLog.orgId.eq(orgId));
        }
        builder.and(dailyLog.createTime.between(yesterday,nowTime));
        builder.and(dailyLog.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(dailyLog.updateTime.desc());
        return query;
    }

}
