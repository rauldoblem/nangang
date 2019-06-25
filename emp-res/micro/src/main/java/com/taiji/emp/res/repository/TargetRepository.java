package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.QTarget;
import com.taiji.emp.res.entity.Target;
import com.taiji.emp.res.searchVo.target.TargetSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public class TargetRepository extends BaseJpaRepository<Target,String> {

    /**
     * 分页,查询防护目标列表
     * @param vo
     * @param pageable
     * @return
     */
    public Page<Target> findPage(TargetSearchVo vo, Pageable pageable){
        JPQLQuery<Target> query = buildQuery(vo);
        return findAll(query,pageable);
    }

    /**
     * 不分页,查询防护目标列表
     * @param
     * @return
     */
    public List<Target> findList(TargetSearchVo vo) {
        JPQLQuery<Target> query = buildQuery(vo);
        return findAll(query);
    }

    private JPQLQuery<Target> buildQuery(TargetSearchVo vo){
        QTarget rcTarget = QTarget.target;
        JPQLQuery<Target> query = from(rcTarget);
        BooleanBuilder builder = new BooleanBuilder();

        String name = vo.getName();
        List<String> typeIds = vo.getTargetTypeIds();
        String unit = vo.getUnit();
        //浙能需求新增条件
        String createOrgId = vo.getCreateOrgId();

        if (StringUtils.hasText(name)){    //防护目标名称
            builder.and(rcTarget.name.contains(name));
        }

        if(null != typeIds && typeIds.size() >0){
            builder.and(rcTarget.targetTypeId.in(typeIds));
        }

        if (StringUtils.hasText(unit)){
            builder.and(rcTarget.unit.contains(unit));
        }

        //浙能需求新增条件
        if (!StringUtils.isEmpty(createOrgId)){
            builder.and(rcTarget.createOrgId.eq(createOrgId));
        }

        builder.and(rcTarget.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Target.class
                    ,rcTarget.id
                    ,rcTarget.name
                    ,rcTarget.createBy
                    ,rcTarget.createTime
                    ,rcTarget.updateBy
                    ,rcTarget.updateTime
                    ,rcTarget.unit
                    ,rcTarget.targetTypeId
                    ,rcTarget.targetTypeName
                    ,rcTarget.address
                    ,rcTarget.lonAndLat
                    ,rcTarget.principal
                    ,rcTarget.principalTel
                    ,rcTarget.describes
                    ,rcTarget.disaster
                    ,rcTarget.measure
                    ,rcTarget.createOrgId
                    ,rcTarget.createOrgName
                )
        ).where(builder)
        .orderBy(rcTarget.updateTime.desc());

        return query;
    }

    /**
     * 新增或编辑
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public Target save(Target entity){
        Assert.notNull(entity,"rcTarget对象不能为null");
        Target result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            Target temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }
}
