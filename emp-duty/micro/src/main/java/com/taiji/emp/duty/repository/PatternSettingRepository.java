package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.CalenSetting;
import com.taiji.emp.duty.entity.PatternSetting;
import com.taiji.emp.duty.entity.QPatternSetting;
import com.taiji.emp.duty.mapper.PatternSettingMapper;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PatternSettingRepository extends BaseJpaRepository<PatternSetting,String> {

    @Autowired
    PatternSettingMapper mapper;
    
    /**
     * 根据条件查询值班模式设置列表
     * @param orgId
     * @return
     */
    public List<PatternSetting> findList(String orgId) {

        QPatternSetting patternSetting = QPatternSetting.patternSetting;

        JPQLQuery<PatternSetting> query = from(patternSetting);
        BooleanBuilder builder = new BooleanBuilder();


        if (StringUtils.hasText(orgId)){
            builder.and(patternSetting.orgId.eq(orgId));
        }

        query.select(Projections.bean(PatternSetting.class
                ,patternSetting.id
                ,patternSetting.orgId
                ,patternSetting.orgName
                ,patternSetting.dtypeCode
        )).where(builder);
        return findAll(query);
    }

    /**
     * 模式设置批量插入
     * @param  vo
     * @return ResponseEntity<List<PatternSettingVo>>
     */
    @Transactional
    public List<PatternSetting> saveBatch(List<PatternSettingVo> vo){
        Assert.notNull(vo,"vo对象不能为空");
        List<PatternSetting> entityList = mapper.voListToEntityList(vo);
        List<PatternSetting> entitys = super.save(entityList);
        return entitys;
    }

    /**
     * 根据所属单位ID,日期类型编码，获取模式设置
     * @param patternSettingVo
     * @return
     */
    public PatternSetting findPatternSetting(PatternSettingVo patternSettingVo) {

        QPatternSetting patternSetting = QPatternSetting.patternSetting;

        JPQLQuery<PatternSetting> query = from(patternSetting);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = patternSettingVo.getOrgId();
        Integer code = patternSettingVo.getDtypeCode();
        String typeCode = String.valueOf(code);
        if (StringUtils.hasText(orgId)){
            builder.and(patternSetting.orgId.eq(orgId));
        }

        if (StringUtils.hasText(typeCode)){
            builder.and(patternSetting.dtypeCode.eq(code));
        }

        PatternSetting entity = query.where(builder).fetchOne();
        return entity;
    }
}
