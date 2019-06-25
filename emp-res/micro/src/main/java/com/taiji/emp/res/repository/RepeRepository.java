package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.QRepertory;
import com.taiji.emp.res.entity.Repertory;
import com.taiji.emp.res.searchVo.repertory.RepertoryPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class RepeRepository extends BaseJpaRepository<Repertory,String> {

    //带分页信息，查询应急储备库列表
    public Page<Repertory> findPage(RepertoryPageVo vo, Pageable pageable){

        String name = vo.getName();
        String address = vo.getAddress();
        String unit = vo.getUnit();
        String createOrgId = vo.getCreateOrgId();

        QRepertory repertory = QRepertory.repertory;
        JPQLQuery<Repertory> query = from(repertory);

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){
            builder.and(repertory.name.contains(name));
        }

        if(StringUtils.hasText(address)){
            builder.and(repertory.repertoryAddress.contains(address));
        }

        if(StringUtils.hasText(unit)){
            builder.and(repertory.unit.contains(unit));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(repertory.createOrgId.eq(createOrgId));
        }

        builder.and(repertory.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Repertory.class
                ,repertory.id
                ,repertory.unit
                ,repertory.code
                ,repertory.name
                ,repertory.repertoryAddress
                ,repertory.lonAndLat
                ,repertory.principalName
                ,repertory.principalTel
                ,repertory.createOrgId
        )).where(builder)
                .orderBy(repertory.updateTime.desc());
        return findAll(query,pageable);
    }

    @Override
    @Transactional
    public Repertory save(Repertory entity){
        Assert.notNull(entity,"repertory 对象不能为 null");

        Repertory result;
        if(null == entity.getId()){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            Repertory temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }

    /**
     * 根据条件查询物资库列表-不分页
     * @param vo
     * @return
     */
    public List<Repertory> findRepertoryList(RepertoryPageVo vo) {
        QRepertory repertory = QRepertory.repertory;
        JPQLQuery<Repertory> query = from(repertory);
        BooleanBuilder builder = new BooleanBuilder();

        String name = vo.getName();
        String address = vo.getAddress();
        String unit = vo.getUnit();
        String createOrgId = vo.getCreateOrgId();

        if(StringUtils.hasText(name)){
            builder.and(repertory.name.contains(name));
        }

        if(StringUtils.hasText(address)){
            builder.and(repertory.repertoryAddress.contains(address));
        }

        if(StringUtils.hasText(unit)){
            builder.and(repertory.unit.contains(unit));
        }
        if(StringUtils.hasText(createOrgId)){
            builder.and(repertory.createOrgId.eq(createOrgId));
        }
        builder.and(repertory.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(Repertory.class
                ,repertory.id
                ,repertory.unit
                ,repertory.code
                ,repertory.name
                ,repertory.repertoryAddress
                ,repertory.lonAndLat
                ,repertory.principalName
                ,repertory.principalTel
                ,repertory.createOrgId
        )).where(builder)
                .orderBy(repertory.updateTime.desc());
        return findAll(query);
    }
}
