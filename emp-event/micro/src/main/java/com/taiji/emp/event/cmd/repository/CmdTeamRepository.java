package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.CmdTeam;
import com.taiji.emp.event.cmd.entity.QCmdTeam;
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
public class CmdTeamRepository extends BaseJpaRepository<CmdTeam,String> {

    @Override
    @Transactional
    public CmdTeam save(CmdTeam entity){
        Assert.notNull(entity,"CmdTeam 不能为null");
        String id = entity.getId();
        CmdTeam result;
        if(StringUtils.isEmpty(id)){
            CmdTeam oldResult = findBySchemeIdAndTeamId(entity.getSchemeId(),entity.getTeamId());//入库前先判重
            if(null!=oldResult){//记录已存在
                return null;
            }
            result = super.save(entity);
        }else{
            CmdTeam temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //根据方案Id和队伍Id查询应急处置应急队伍调度记录
    public CmdTeam findBySchemeIdAndTeamId(String schemeId,String teamId){
        Assert.hasText(schemeId,"schemeId不能为空字符串");
        Assert.hasText(teamId,"expertId不能为空字符串");
        QCmdTeam cmdTeam = QCmdTeam.cmdTeam;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cmdTeam.schemeId.eq(schemeId));
        builder.and(cmdTeam.teamId.eq(teamId));
        builder.and(cmdTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<CmdTeam> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //根据条件查询处置方案已关联的应急队伍信息
    //参数key:schemeId,name,teamTypeName
    public List<CmdTeam> findList(MultiValueMap<String,Object> params){
        QCmdTeam cmdTeam = QCmdTeam.cmdTeam;
        JPQLQuery<CmdTeam> query = from(cmdTeam);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("schemeId")){
            builder.and(cmdTeam.schemeId.eq(params.getFirst("schemeId").toString()));
        }
        if(params.containsKey("name")){//队伍名称
            builder.and(cmdTeam.teamName.contains(params.getFirst("name").toString()));
        }
        if(params.containsKey("teamTypeName")){//队伍类型名称
            builder.and(cmdTeam.teamTypeName.contains(params.getFirst("teamTypeName").toString()));
        }
        builder.and(cmdTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(cmdTeam.updateTime.desc());
        return findAll(query);
    }

}
