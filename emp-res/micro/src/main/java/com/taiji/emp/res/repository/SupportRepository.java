package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.res.entity.QSupport;
import com.taiji.emp.res.entity.Support;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.vo.SupportVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class SupportRepository extends BaseJpaRepository<Support,String>{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SqlFormat sqlFormat;

    //查询应急社会依托资源list -- 分页
    public Page<Support> findPage(SupportPageVo supportPageVo, Pageable pageable){

        String name = supportPageVo.getName();
        String address = supportPageVo.getAddress();
        List<String> typeIds = supportPageVo.getTypeIds();
        List<String> selectedSupportIds = supportPageVo.getSelectedSupportIds();
        List<String> supportIds = supportPageVo.getSupportIds();
        String createOrgId = supportPageVo.getCreateOrgId();

        QSupport support = QSupport.support;

        JPQLQuery<Support> query = from(support);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){
            builder.and(support.name.contains(name));
        }

        if(StringUtils.hasText(address)){
            builder.and(support.address.contains(address));
        }

        if(null!=typeIds&&typeIds.size()>0){
            builder.and(support.typeId.in(typeIds));
        }

        if(null!=selectedSupportIds&&selectedSupportIds.size()>0){
            builder.and(support.id.notIn(selectedSupportIds));
        }

        if(null!=supportIds&&supportIds.size()>0){
            builder.and(support.id.in(supportIds));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(support.createOrgId.eq(createOrgId));
        }

        builder.and(support.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Support.class
                ,support.id
                ,support.name
                ,support.typeId
                ,support.typeName
                ,support.address
                ,support.lonAndLat
                ,support.principal
                ,support.principalTel
                ,support.supportSize
                ,support.capacity
                ,support.describes
                ,support.notes
                ,support.createOrgId
        )).where(builder)
                .orderBy(support.updateTime.desc());


        return findAll(query,pageable);
    }

    //查询应急社会依托资源list -- 不分页
    public List<Support> findList(SupportListVo supportListVo){

        String name = supportListVo.getName();
        String address = supportListVo.getAddress();
        List<String> typeIds = supportListVo.getTypeIds();
        List<String> supportIds = supportListVo.getSupportIds();
        List<String> selectedSupportIds = supportListVo.getSelectedSupportIds();
        String createOrgId = supportListVo.getCreateOrgId();

        QSupport support = QSupport.support;

        JPQLQuery<Support> query = from(support);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){
            builder.and(support.name.contains(name));
        }
        if(StringUtils.hasText(address)){
            builder.and(support.address.contains(address));
        }

        if(null!=typeIds&&typeIds.size()>0){
            builder.and(support.typeId.in(typeIds));
        }
        if(null!=selectedSupportIds&&selectedSupportIds.size()>0){
            builder.and(support.id.notIn(selectedSupportIds));
        }

        if(null!=supportIds&&supportIds.size()>0){
            builder.and(support.id.in(supportIds));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(support.createOrgId.eq(createOrgId));
        }

        builder.and(support.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Support.class
                ,support.id
                ,support.name
                ,support.typeId
                ,support.typeName
                ,support.address
                ,support.lonAndLat
                ,support.principal
                ,support.principalTel
                ,support.supportSize
                ,support.capacity
                ,support.describes
                ,support.notes
                ,support.createOrgId
        )).where(builder)
                .orderBy(support.updateTime.desc());
        return findAll(query);
    }

    @Override
    @Transactional
    public Support save(Support entity){
        Assert.notNull(entity,"support对象不能为 null");
        Support result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            Support temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 通过schemeId应急社会依托资源信息
     * @param schemeId
     * @return
     */
    public List<SupportVo> findBySchemeId(String schemeId) {
        int builderLength = 350;
        StringBuilder builder = new StringBuilder(builderLength);
        builder.append("SELECT * FROM ER_SUPPORT WHERE ID IN ")
                .append("(SELECT SUPPORT_ID FROM EC_SUPPORT WHERE SCHEME_ID = '")
                .append(schemeId).append("' AND DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("')");
        String sql = sqlFormat.sqlFormat(builder.toString());
        return jdbcTemplate.query(sql , new BeanPropertyRowMapper(SupportVo.class));
    }
}
