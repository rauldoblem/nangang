package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.CmdOrg;
import com.taiji.emp.event.cmd.entity.QCmdOrg;
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
public class CmdOrgRepository extends BaseJpaRepository<CmdOrg,String> {

    //新增单个应急机构
    @Transactional
    public CmdOrg createOne(CmdOrg entity){
        if(!StringUtils.isEmpty(entity.getId())){
            entity.setId(null);
        }

        String planOrgId = entity.getPlanOrgId();
        if(!StringUtils.isEmpty(planOrgId)){ //预案数字化生成 应急机构，需判重
            CmdOrg oldResult = findBySchemeIdAndPlanOrgId(entity.getSchemeId(),planOrgId);
            if(null!=oldResult){ //记录已存在
                return oldResult; //这里返回是因为责任单位的保存需要
            }
        }

        return super.save(entity);
    }

    //根据方案id和预案关联组织ID查询应急机构关联记录
    public CmdOrg findBySchemeIdAndPlanOrgId(String schemeId,String planOrgId){
        Assert.hasText(schemeId,"schemeId 不能为空");
        Assert.hasText(planOrgId,"planOrgId 不能为空");
        QCmdOrg cmdOrg = QCmdOrg.cmdOrg;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cmdOrg.schemeId.eq(schemeId));
        builder.and(cmdOrg.planOrgId.eq(planOrgId));
        builder.and(cmdOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<CmdOrg> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //更新单个应急机构
    @Transactional
    public CmdOrg update(CmdOrg entity,String id){
        Assert.notNull(entity,"CmdOrg 不能为空");
        Assert.hasText(id,"id 不能为空字符串");
        CmdOrg temp = super.findOne(id);
        Assert.notNull(entity,"CmdOrg temp 不能为空");
        entity.setId(null); //这里使用路径中的id
        BeanUtils.copyNonNullProperties(entity,temp);
        return super.save(temp);
    }

    //查询应急组织机构树
    //参数key:schemeId,planId
    public List<CmdOrg> findList(MultiValueMap<String, Object> params){
        QCmdOrg cmdOrg = QCmdOrg.cmdOrg;
        JPQLQuery<CmdOrg> query = from(cmdOrg);
        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("schemeId")){
            String schemeId = params.getFirst("schemeId").toString();
            Assert.hasText(schemeId,"schemeId 不能为空字符串");
            builder.and(cmdOrg.schemeId.eq(schemeId));
        }

        if(params.containsKey("planId")){
            String planId = params.getFirst("planId").toString();
            Assert.hasText(planId,"planId 不能为空字符串");
            builder.and(cmdOrg.planId.eq(planId));
        }

        builder.and(cmdOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder);

        return findAll(query);
    }


}
