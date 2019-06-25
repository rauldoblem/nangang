package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.Position;
import com.taiji.emp.res.entity.QPosition;
import com.taiji.emp.res.vo.PositionVo;
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
public class PositionRepository extends BaseJpaRepository<Position,String>{

   
    //查询仓位list -- 不分页
    public List<Position> findList(PositionVo positionVo){

        String repertoryId = positionVo.getRepertoryId();

        QPosition position = QPosition.position;
        JPQLQuery<Position> query = from(position);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(repertoryId)){
            builder.and(position.repertoryId.eq(repertoryId));
        }
        builder.and(position.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Position.class
                ,position.id
                ,position.name
                ,position.code
                ,position.sortNumber
                ,position.repertoryId
                ,position.repertoryName
        )).where(builder)
                .orderBy(position.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public Position save(Position entity){
        Assert.notNull(entity,"position对象不能为 null");
        Position result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            Position temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

}
