package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.emp.event.cmd.entity.OrgResponDetail;
import com.taiji.emp.event.cmd.entity.QOrgResponDetail;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class OrgResDetailRepository extends BaseJpaRepository<OrgResponDetail,String> {

    //保存应急组织责任单位/个人detail从表
    @Transactional
    @Override
    public OrgResponDetail save(OrgResponDetail entity){
        Assert.notNull(entity,"OrgResponDetail 不能为null");
        String id = entity.getId();
        OrgResponDetail result;
        if(StringUtils.isEmpty(id)){
            String planResDetailId = entity.getPlanResDetailId();
            if(!StringUtils.isEmpty(planResDetailId)) { //预案数字化生成 应急责任单位/人从表，需判重
                OrgResponDetail oldResult = findByOrgResponIdAndPlanResDetailId(entity.getOrgResponId(),planResDetailId);
                if(null!=oldResult){ //记录已存在
                    return null;
                }
            }
            result=super.save(entity);
        }else{
            OrgResponDetail temp = findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result=super.save(temp);
        }
        return result;
    }

    //根据应急组织责任ID和预案关联责任单位/人ID查询应急处置--应急责任单位/人从表记录
    public OrgResponDetail findByOrgResponIdAndPlanResDetailId(String orgResponId,String planResDetailId){
        Assert.hasText(orgResponId,"orgResponId 不能为空字符串");
        Assert.hasText(planResDetailId,"planResDetailId 不能为空字符串");
        QOrgResponDetail orgResponDetail = QOrgResponDetail.orgResponDetail;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orgResponDetail.orgResponId.eq(orgResponId));
        builder.and(orgResponDetail.planResDetailId.eq(planResDetailId));
        builder.and(orgResponDetail.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<OrgResponDetail> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }


    //应急组织责任单位/个人删除时，级联删除detail从表
    @Transactional
    public void deleteDetailByOrgResponId(String orgResponId,DelFlagEnum delFlagEnum){
        Assert.hasText(orgResponId,"应急责任orgResponId 不能为空");
        QOrgResponDetail orgResponDetail = QOrgResponDetail.orgResponDetail;
        JPAUpdateClause updateClause = querydslUpdate(orgResponDetail);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orgResponDetail.orgResponId.eq(orgResponId)); // 该应急责任下的从表信息
        builder.and(orgResponDetail.delFlag.eq(DelFlagEnum.NORMAL.getCode())); //未删除的

        updateClause.set(orgResponDetail.delFlag,delFlagEnum.getCode()).where(builder).execute();
    }

    //应急组织责任单位/个人批量删除(应急机构节点删除)时，级联删除detail从表
    @Transactional
    public void deleteDetailByOrgResponList(List<String> orgResponIds, DelFlagEnum delFlagEnum){
        if(null!=orgResponIds&&orgResponIds.size()>0){
            QOrgResponDetail orgResponDetail = QOrgResponDetail.orgResponDetail;
            JPAUpdateClause updateClause = querydslUpdate(orgResponDetail);

            BooleanBuilder builder = new BooleanBuilder();
            builder.and(orgResponDetail.orgResponId.in(orgResponIds)); // 该应急责任id串下的从表信息
            builder.and(orgResponDetail.delFlag.eq(DelFlagEnum.NORMAL.getCode())); //未删除的

            updateClause.set(orgResponDetail.delFlag,delFlagEnum.getCode()).where(builder).execute();
        }
    }

}
