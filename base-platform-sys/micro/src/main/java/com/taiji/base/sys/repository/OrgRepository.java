package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.sys.entity.Org;
import com.taiji.base.sys.entity.QOrg;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.hibernate.bytecode.internal.javassist.BulkAccessor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统组织机构Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
public class OrgRepository extends BaseJpaRepository<Org,String>{

    public List<Org> findAll(String parentId,String orgName)
    {
        QOrg qOrg = QOrg.org;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(parentId))
        {
            builder.and(qOrg.parentId.eq(parentId));
        }

        if(StringUtils.hasText(orgName))
        {
            builder.and(qOrg.orgName.eq(orgName));
        }

        builder.and(qOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder);
    }

    @Override
    @Transactional
    public Org save(Org entity)
    {
        Assert.notNull(entity, "role must not be null!");

        Org result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            Org tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

    public List<Org> findOrgInfo(List<String> orgIds) {
        QOrg org = QOrg.org;
        JPQLQuery<Org> query = from(org);
        BooleanBuilder builder = new BooleanBuilder();
        if (!CollectionUtils.isEmpty(orgIds)){
            builder.and(org.id.in(orgIds));
        }
        builder.and(org.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(Org.class
                ,org.id
                ,org.orgName
        )).where(builder);
        return findAll(query);
    }

    /**
     * 根据新加字段orgZnCode查询org的Id
     * @param znCode
     * @return
     */
    public Org findIdByOrgZnCode(String znCode) {
        QOrg org = QOrg.org;
        JPQLQuery<Org> query = from(org);
        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isEmpty(znCode)){
            builder.and(org.orgZnCode.eq(znCode));
        }
        Org result = query.where(builder).fetchOne();
        return result;
    }

    /**
     * 传入板块OrgCode的前两位，例如01,返回所有以01打头的orgCode对应的orgId的list
     * @param code
     * @return
     */
    public List<String> findOrgIdListByCode(String code) {
        boolean empty = StringUtils.isEmpty(code);
        Assert.isTrue(!empty && code.trim().length() == 2 ,"用于获取获取orgIdList的code参数不合法");

        QOrg org = QOrg.org;
        JPQLQuery<Org> query = from(org);
        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isEmpty(code)){
            builder.and(org.orgCode.like(code + "%"));
        }
        List<Org> orgs = findAll(query.where(builder));
        List<String> result = null;
        if(!CollectionUtils.isEmpty(orgs)){
            result = new ArrayList();
            for (Org org1 : orgs) {
                if(null != org1){
                    result.add(org1.getId());
                }
            }
        }
        return result;
    }
}
