package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.CalenSetting;
import com.taiji.emp.duty.entity.QCalenSetting;
import com.taiji.emp.duty.mapper.CalenSettingMapper;
import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CalenSettingRepository extends BaseJpaRepository<CalenSetting,String> {

    @Autowired
    CalenSettingMapper mapper;

    /**
     * 新增日历设置信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public CalenSetting save(CalenSetting entity){
        Assert.notNull(entity,"entity对象不能为空");
        CalenSetting result;
        result = super.save(entity);
        return result;
    }

    //新增日历设置信息
    @Transactional
    public CalenSetting save(CalenSettingListVo vo){
        Assert.notNull(vo,"vo对象不能为空");
        CalenSetting result = null;
        //循环入库
        //String orgId = vo.getOrgId();
        List<CalenSettingVo> calenSettingVo = vo.getCalenSetting();
        List<CalenSetting> calenSetting = mapper.voListToEntityList(calenSettingVo);
        for (CalenSetting cs:calenSetting) {
            if (!StringUtils.isEmpty(cs.getId())){
                CalenSetting temp = findOne(cs.getId());
                BeanUtils.copyNonNullProperties(cs,temp);
                result = super.save(temp);
            }
        }
        return result;
    }

    /**
     * 根据条件查询日历设置列表
     * @param calenSettingVo
     * @return
     */
    public List<CalenSetting> findList(CalenSettingVo calenSettingVo) {

        QCalenSetting calenSetting = QCalenSetting.calenSetting;

        JPQLQuery<CalenSetting> query = from(calenSetting);
        BooleanBuilder builder = new BooleanBuilder();

        String orgId = calenSettingVo.getOrgId();
        String month = calenSettingVo.getMonth();
        String day = calenSettingVo.getDay();
        String time = calenSettingVo.getTime();
        if (StringUtils.hasText(orgId)){
            builder.and(calenSetting.orgId.eq(orgId));
        }
        if (StringUtils.hasText(month) && StringUtils.hasText(day)){
            day = month + "-"+day;
            //补齐日
            month = month + "-01";
            LocalDate start = DateUtil.strToLocalDate(month);
            LocalDate end = DateUtil.strToLocalDate(day);
            builder.and(calenSetting.settingDate.between(start,end));
        }

        if (StringUtils.hasText(time)){
            LocalDate times = DateUtil.strToLocalDate(time);
            builder.and(calenSetting.settingDate.eq(times));
        }
        query.select(Projections.bean(CalenSetting.class
                ,calenSetting.id
                ,calenSetting.orgId
                ,calenSetting.settingDate
                ,calenSetting.dateTypeCode
                ,calenSetting.holidayName
        )).where(builder).orderBy(calenSetting.settingDate.asc());
        return findAll(query);
    }

    /**
     * 获取值班模式
     * @param currentDate
     * @return
     */
    public CalenSetting findSettingDate(LocalDate currentDate,String orgId) {
        QCalenSetting calenSetting = QCalenSetting.calenSetting;
        JPQLQuery<CalenSetting> query = from(calenSetting);
        BooleanBuilder builder = new BooleanBuilder();

        if(currentDate != null){
            builder.and(calenSetting.settingDate.eq(currentDate));
        }
        if(!StringUtils.isEmpty(orgId)){
            builder.and(calenSetting.orgId.eq(orgId));
        }
        CalenSetting entity = query.select(Projections.bean(CalenSetting.class
                , calenSetting.id
                , calenSetting.dateTypeCode)).where(builder).fetchOne();
        return entity;
    }

    //日历设置批量插入
    @Transactional
    public List<CalenSetting>  saveBatch(CalenSettingListVo vo){
        Assert.notNull(vo,"vo对象不能为空");
        List<CalenSettingVo> calenSettingVo = vo.getCalenSetting();
        List<CalenSetting> entityList = mapper.voListToEntityList(calenSettingVo);
        List<CalenSetting> entitys = super.save(entityList);
        return entitys;
    }
}
