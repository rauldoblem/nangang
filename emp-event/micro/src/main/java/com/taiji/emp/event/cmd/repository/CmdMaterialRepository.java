package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.CmdMaterial;
import com.taiji.emp.event.cmd.entity.QCmdMaterial;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CmdMaterialRepository extends BaseJpaRepository<CmdMaterial,String> {

    @Override
    @Transactional
    public CmdMaterial save(CmdMaterial entity){
        Assert.notNull(entity,"CmdMaterial 不能为null");
        String id = entity.getId();
        CmdMaterial result;
        if(StringUtils.isEmpty(id)){
            CmdMaterial oldResult = findBySchemeIdAndMaterialId(entity.getSchemeId(),entity.getMaterialId());//入库前先判重
            if(null!=oldResult){//记录已存在
                return null;
            }
            result = super.save(entity);
        }else{
            CmdMaterial temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //根据方案Id和物资Id查询应急物资调度记录
    public CmdMaterial findBySchemeIdAndMaterialId(String schemeId,String materialId){
        Assert.hasText(schemeId,"schemeId不能为空字符串");
        Assert.hasText(materialId,"expertId不能为空字符串");
        QCmdMaterial cmdMaterial = QCmdMaterial.cmdMaterial;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cmdMaterial.schemeId.eq(schemeId));
        builder.and(cmdMaterial.materialId.eq(materialId));
        builder.and(cmdMaterial.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<CmdMaterial> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //根据条件查询处置方案已关联的应急物资信息
    //参数key:schemeId,name,resTypeName
    public List<CmdMaterial> findList(MultiValueMap<String,Object> params){
        QCmdMaterial cmdMaterial = QCmdMaterial.cmdMaterial;
        JPQLQuery<CmdMaterial> query = from(cmdMaterial);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("schemeId")){
            builder.and(cmdMaterial.schemeId.eq(params.getFirst("schemeId").toString()));
        }
        if(params.containsKey("name")){//物资名称
            builder.and(cmdMaterial.materialName.contains(params.getFirst("name").toString()));
        }
        if(params.containsKey("resTypeName")){//物资类型名称
            builder.and(cmdMaterial.resTypeName.contains(params.getFirst("resTypeName").toString()));
        }

        builder.and(cmdMaterial.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(cmdMaterial.updateTime.desc());
        return findAll(query);
    }

}
