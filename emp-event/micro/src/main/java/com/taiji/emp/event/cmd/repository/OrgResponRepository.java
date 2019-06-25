package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.emp.event.cmd.entity.OrgRespon;
import com.taiji.emp.event.cmd.entity.QOrgRespon;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(
        readOnly = true
)
public class OrgResponRepository extends BaseJpaRepository<OrgRespon,String> {

    @Autowired
    private OrgResDetailRepository detailRepository;

    //新增单个应急责任单位/人
    @Transactional
    public OrgRespon createOne(OrgRespon entity){
        if(!StringUtils.isEmpty(entity.getId())){
            entity.setId(null);
        }

        String planResponId = entity.getPlanResponId();
        if(!StringUtils.isEmpty(planResponId)) { //预案数字化生成 应急责任单位，需判重
            OrgRespon oldResult = findByEmgOrgIdAndPlanResponId(entity.getEmgOrgId(),planResponId);
            if(null!=oldResult){ //记录已存在
                return null;
            }
        }

        return super.save(entity);
    }

    //根据应急组织ID和预案关联组织责任ID 查询应急责任单位/人记录
    public OrgRespon findByEmgOrgIdAndPlanResponId(String emgOrgId,String planResponId){
        Assert.hasText(emgOrgId,"emgOrgId 不能为空字符串");
        Assert.hasText(planResponId,"planResponId 不能为空字符串");
        QOrgRespon orgRespon = QOrgRespon.orgRespon;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orgRespon.emgOrgId.eq(emgOrgId));
        builder.and(orgRespon.planResponId.eq(planResponId));
        builder.and(orgRespon.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<OrgRespon> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //更新单个应急机构
    @Transactional
    public OrgRespon update(OrgRespon entity,String id){
        Assert.notNull(entity,"OrgRespon 不能为空");
        Assert.hasText(id,"id 不能为空字符串");
        OrgRespon temp = super.findOne(id);
        Assert.notNull(entity,"CmdOrg temp 不能为空");
        entity.setId(null); //这里使用路径中的id
        entity.setDetails(null); //避免下面赋值出现问题
        BeanUtils.copyNonNullProperties(entity,temp);
        return super.save(temp);
    }


    //删除应急组织机构时，级联删除责任单位/人
    @Transactional
    public void deleteOrgResponByOrg(String emgOrgId, DelFlagEnum delFlagEnum){

        Assert.hasText(emgOrgId,"emgOrgId 不能为空");

        QOrgRespon orgRespon = QOrgRespon.orgRespon;
        JPAUpdateClause updateClause = querydslUpdate(orgRespon);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orgRespon.emgOrgId.eq(emgOrgId)); // 该应急机构节点下的责任单位/人
        builder.and(orgRespon.delFlag.eq(DelFlagEnum.NORMAL.getCode())); //未删除的

        //级联删除责任单位/人从表信息
        JPQLQuery<OrgRespon> query = from(orgRespon);
        query.select(Projections.bean(OrgRespon.class,orgRespon.id)).where(builder);
        List<OrgRespon> list = findAll(query);
        List<String> OrgResponIds = list.stream().map(orgRespon1 -> orgRespon1.getId()).collect(Collectors.toList());

        updateClause.set(orgRespon.delFlag,delFlagEnum.getCode()).where(builder).execute(); //删除主表信息

        detailRepository.deleteDetailByOrgResponList(OrgResponIds,delFlagEnum); //删除从表信息

    }



    //根据条件查询责任单位/人信息
    //参数：emgOrgId
    public List<OrgRespon> findList(MultiValueMap<String,Object> params){

        QOrgRespon orgRespon = QOrgRespon.orgRespon;
        JPQLQuery<OrgRespon> query = from(orgRespon);

        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("emgOrgId")){
            builder.and(orgRespon.emgOrgId.eq(params.getFirst("emgOrgId").toString())); // 该应急机构节点下的责任单位/人
        }
        builder.and(orgRespon.delFlag.eq(DelFlagEnum.NORMAL.getCode())); //未删除的

        query.where(builder).orderBy(orgRespon.updateTime.desc());

        return findAll(query);
    }

}
