package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.PlantLayout;
import com.taiji.emp.base.entity.QPlantLayout;
import com.taiji.emp.base.searchVo.PlantLayoutSearchVo;
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
@Transactional(readOnly = true)
public class PlantLayoutRepository extends BaseJpaRepository<PlantLayout,String> {

    /**
     * 新增或编辑-----厂区平面图
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public PlantLayout save(PlantLayout entity){
        Assert.notNull(entity,"entity对象不能为null");
        PlantLayout result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            PlantLayout temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 查询厂区平面图信息列表------分页
     * @param plantLayoutVo
     * @param page
     * @return
     */
    public Page<PlantLayout> findPage(PlantLayoutSearchVo plantLayoutVo, Pageable page) {
        JPQLQuery<PlantLayout> query = buildQuery(plantLayoutVo);
        return findAll(query,page);
    }

    /**
     * 查询厂区平面图信息列表------不分页
     * @param plantLayoutVo
     * @return
     */
    public List<PlantLayout> findList(PlantLayoutSearchVo plantLayoutVo) {
        JPQLQuery<PlantLayout> query = buildQuery(plantLayoutVo);
        return findAll(query);
    }

    private JPQLQuery<PlantLayout> buildQuery(PlantLayoutSearchVo plantLayoutVo) {
        QPlantLayout plantLayout = QPlantLayout.plantLayout;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<PlantLayout> query = from(plantLayout);
        String gisOrgId = plantLayoutVo.getGisOrgId();
        if (StringUtils.hasText(gisOrgId)){    //外部地图组织机构编码
            builder.and(plantLayout.gisOrgId.eq(gisOrgId));
        }
        builder.and(plantLayout.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(plantLayout.updateTime.desc());
        return query;
    }
}
