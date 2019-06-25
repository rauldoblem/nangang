package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.QPersonTypePattern;
import com.taiji.emp.duty.entity.PersonTypePattern;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PersonTypePatternRepository extends BaseJpaRepository<PersonTypePattern,String> {

    /**
     * 新增或修改值班人员设置信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public PersonTypePattern save(PersonTypePattern entity){
        Assert.notNull(entity,"entity对象不能为空");
        PersonTypePattern result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else{
            PersonTypePattern temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }


    /**
     * 根据条件查询值班人员设置列表
     * @param patternId
     * @return
     */
    public List<PersonTypePattern> findList(String patternId) {
        QPersonTypePattern personTypePattern = QPersonTypePattern.personTypePattern;

        JPQLQuery<PersonTypePattern> query = from(personTypePattern);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(patternId)){
            builder.and(personTypePattern.patternId.eq(patternId));
        }
        builder.and(personTypePattern.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(PersonTypePattern.class
                ,personTypePattern.id
                ,personTypePattern.patternId
                ,personTypePattern.teamId
                ,personTypePattern.teamName
                ,personTypePattern.dutyTypeCode
        )).where(builder).orderBy(personTypePattern.updateTime.desc());
        return findAll(query);
    }


}
