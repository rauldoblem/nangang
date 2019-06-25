package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanOrgRespon;
import com.taiji.emp.res.entity.QPlanOrgRespon;
import com.taiji.emp.res.vo.PlanOrgResponVo;
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
public class PlanOrgResponRepository extends BaseJpaRepository<PlanOrgRespon,String>{

    //预案责任人、单位管理list -- 不分页
    public List<PlanOrgRespon> findList(PlanOrgResponVo planOrgResponVo){

        String planOrgId = planOrgResponVo.getPlanOrgId();
        List<String> planOrgIds = planOrgResponVo.getPlanOrgIds();

        QPlanOrgRespon planOrgRespon = QPlanOrgRespon.planOrgRespon;

        JPQLQuery<PlanOrgRespon> query = from(planOrgRespon);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(planOrgId)){
            builder.and(planOrgRespon.planOrgId.eq(planOrgId));
        }

        if(null!=planOrgIds&&planOrgIds.size()>0){
            builder.and(planOrgRespon.planOrgId.in(planOrgIds));
        }

        builder.and(planOrgRespon.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder)
                .orderBy(planOrgRespon.updateTime.desc());
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanOrgRespon save(PlanOrgRespon entity){
        Assert.notNull(entity,"planOrgRespon对象不能为 null");
        PlanOrgRespon result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            PlanOrgRespon temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

}
