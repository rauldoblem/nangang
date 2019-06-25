package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.CmdExpert;
import com.taiji.emp.event.cmd.entity.QCmdExpert;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CmdExpertRepository extends BaseJpaRepository<CmdExpert,String> {

    @Override
    @Transactional
    public CmdExpert save(CmdExpert entity){
        Assert.notNull(entity,"CmdExpert 不能为null");
        String id = entity.getId();
        CmdExpert result;
        if(StringUtils.isEmpty(id)){
            CmdExpert oldResult = findBySchemeIdAndExpertId(entity.getSchemeId(),entity.getExpertId());//入库前先判重
            if(null!=oldResult){//记录已存在
                return null;
            }
            result = super.save(entity);
        }else{
            CmdExpert temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //根据方案Id和专家Id查询应急专家调度记录
    public CmdExpert findBySchemeIdAndExpertId(String schemeId,String expertId){
        Assert.hasText(schemeId,"schemeId不能为空字符串");
        Assert.hasText(expertId,"expertId不能为空字符串");
        QCmdExpert cmdExpert = QCmdExpert.cmdExpert;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cmdExpert.schemeId.eq(schemeId));
        builder.and(cmdExpert.expertId.eq(expertId));
        builder.and(cmdExpert.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<CmdExpert> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //根据条件查询处置方案已关联的应急专家信息
    //参数key:schemeId,name,specialty
    public List<CmdExpert> findList(MultiValueMap<String,Object> params){
        QCmdExpert cmdExpert = QCmdExpert.cmdExpert;
        JPQLQuery<CmdExpert> query = from(cmdExpert);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("schemeId")){
            builder.and(cmdExpert.schemeId.eq(params.getFirst("schemeId").toString()));
        }
        if(params.containsKey("name")){//专家姓名
            builder.and(cmdExpert.expertName.contains(params.getFirst("name").toString()));
        }
        if(params.containsKey("specialty")){//专业特长
            builder.and(cmdExpert.specialty.contains(params.getFirst("specialty").toString()));
        }
        builder.and(cmdExpert.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(cmdExpert.updateTime.desc());
        return findAll(query);
    }

}
