package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.CmdPlan;
import com.taiji.emp.event.cmd.entity.QCmdPlan;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CmdPlanRepository extends BaseJpaRepository<CmdPlan,String> {

    //保存方法
    @Override
    @Transactional
    public CmdPlan save(CmdPlan entity){
        Assert.notNull(entity,"CmdPlan 不能为null");
        String id = entity.getId();
        CmdPlan result;
        if(StringUtils.isEmpty(id)){
            CmdPlan oldPan = findCmdPlanBySchemeIdAndPlanId(entity.getSchemeId(),entity.getPlanId());
            if(null!=oldPan){
                return null; //已经存在该预案启动记录，避免重复入库
            }
            result = super.save(entity);
        }else{
            CmdPlan temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //通过方案id和预案id查询启动的应急预案记录
    public CmdPlan findCmdPlanBySchemeIdAndPlanId(String schemeId,String planId){
        Assert.hasText(schemeId,"schemeId 不能为空字符串");
        Assert.hasText(planId,"planId 不能为空字符串");

        QCmdPlan cmdPlan = QCmdPlan.cmdPlan;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cmdPlan.schemeId.eq(schemeId));
        builder.and(cmdPlan.planId.eq(planId));
        builder.and(cmdPlan.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findOne(builder);
    }

    //根据条件查询处置方案已关联的应急预案信息
    public List<CmdPlan> findList(MultiValueMap<String, Object> params){

        QCmdPlan cmdPlan = QCmdPlan.cmdPlan;
        JPQLQuery<CmdPlan> query = from(cmdPlan);
        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("schemeId")){
            String schemeId = params.getFirst("schemeId").toString();
            Assert.hasText(schemeId,"schemeId 不能为空字符串");
            builder.and(cmdPlan.schemeId.eq(schemeId));
        }

        builder.and(cmdPlan.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder).orderBy(cmdPlan.updateTime.desc());
        return findAll(query);
    }

}
