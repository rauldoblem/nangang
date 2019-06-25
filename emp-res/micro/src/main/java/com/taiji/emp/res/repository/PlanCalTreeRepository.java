package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanCalTree;
import com.taiji.emp.res.entity.QPlanCalTree;
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
public class PlanCalTreeRepository extends BaseJpaRepository<PlanCalTree,String>{

    //预案目录list -- 不分页
    public List<PlanCalTree> findList(){

        QPlanCalTree planCalTree = QPlanCalTree.planCalTree;

        JPQLQuery<PlanCalTree> query = from(planCalTree);
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(planCalTree.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder);
    }

    @Override
    @Transactional
    public PlanCalTree save(PlanCalTree entity){
        Assert.notNull(entity,"PlanCalTree对象不能为 null");
        PlanCalTree result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            PlanCalTree temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

}
