package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.CmdSupport;
import com.taiji.emp.event.cmd.entity.QCmdSupport;
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
public class CmdSupportRepository extends BaseJpaRepository<CmdSupport,String> {

    @Override
    @Transactional
    public CmdSupport save(CmdSupport entity){
        Assert.notNull(entity,"CmdSupport 不能为null");
        String id = entity.getId();
        CmdSupport result;
        if(StringUtils.isEmpty(id)){
            CmdSupport oldResult = findBySchemeIdAndSupportId(entity.getSchemeId(),entity.getSupportId());//入库前先判重
            if(null!=oldResult){//记录已存在
                return null;
            }
            result = super.save(entity);
        }else{
            CmdSupport temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //根据方案Id和社会依托资源Id查询应急处置社会依托资源调度记录
    public CmdSupport findBySchemeIdAndSupportId(String schemeId,String supportId){
        Assert.hasText(schemeId,"schemeId不能为空字符串");
        Assert.hasText(supportId,"expertId不能为空字符串");
        QCmdSupport cmdSupport = QCmdSupport.cmdSupport;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cmdSupport.schemeId.eq(schemeId));
        builder.and(cmdSupport.supportId.eq(supportId));
        builder.and(cmdSupport.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<CmdSupport> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //根据条件查询处置方案已关联的社会依托资源信息
    //参数key:schemeId,name,address
    public List<CmdSupport> findList(MultiValueMap<String,Object> params){
        QCmdSupport cmdSupport = QCmdSupport.cmdSupport;
        JPQLQuery<CmdSupport> query = from(cmdSupport);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("schemeId")){
            builder.and(cmdSupport.schemeId.eq(params.getFirst("schemeId").toString()));
        }
        if(params.containsKey("name")){//社会依托资源名称
            builder.and(cmdSupport.supportName.contains(params.getFirst("name").toString()));
        }
        if(params.containsKey("address")){//社会依托资源地址
            builder.and(cmdSupport.address.contains(params.getFirst("address").toString()));
        }
        builder.and(cmdSupport.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(cmdSupport.updateTime.desc());
        return findAll(query);
    }

}
