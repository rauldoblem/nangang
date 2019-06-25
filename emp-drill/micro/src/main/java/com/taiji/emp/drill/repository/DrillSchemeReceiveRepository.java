package com.taiji.emp.drill.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.drill.entity.DrillSchemeReceive;
import com.taiji.emp.drill.entity.QDrillSchemeReceive;
import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class DrillSchemeReceiveRepository extends BaseJpaRepository<DrillSchemeReceive,String> {

    /**
     * 根据条件查询演练方案接收列表——分页
     * @param searchVo
     * @param page
     * @return
     */
    public Page<DrillSchemeReceive> findPage(DrillSchemeReceiveSearchVo searchVo, Pageable page) {
        QDrillSchemeReceive drillSchemeReceive = QDrillSchemeReceive.drillSchemeReceive;
        JPQLQuery<DrillSchemeReceive> query = from(drillSchemeReceive);
        BooleanBuilder builder = new BooleanBuilder();

        String drillName = searchVo.getDrillName();
        String receiveOrgId = searchVo.getReceiveOrgId();
        String orgIds = searchVo.getOrgIds();
        String createStartTime = searchVo.getDrillStartTime();
        String createEndTime = searchVo.getDrillEndTime();

        String[] orgIdArray = null;
        if (StringUtils.hasText(orgIds)){
            orgIdArray = orgIds.split(",");
        }

        if (StringUtils.hasText(drillName)){
            builder.and(drillSchemeReceive.drillScheme.drillName.contains(drillName));
        }

        if (StringUtils.hasText(receiveOrgId)){
            builder.and(drillSchemeReceive.orgId.eq(receiveOrgId));
        }

        if (null != orgIdArray && orgIdArray.length > 0){
            builder.and(drillSchemeReceive.drillScheme.orgId.in(orgIdArray));
        }

        if (StringUtils.hasText(createStartTime) && StringUtils.hasText(createEndTime)){
            builder.and(drillSchemeReceive.drillScheme.createTime
                    .between(DateUtil.strToLocalDateTime(createStartTime),DateUtil.strToLocalDateTime(createEndTime)));
        }
        if (StringUtils.hasText(createStartTime) && !StringUtils.hasText(createEndTime)){
            LocalDateTime startDrillTime = DateUtil.strToLocalDateTime(createStartTime);
            builder.and(drillSchemeReceive.drillScheme.createTime.lt(startDrillTime));
        }
        if (StringUtils.hasText(createEndTime) && !StringUtils.hasText(createStartTime)){
            LocalDateTime endDrillTime = DateUtil.strToLocalDateTime(createEndTime);
            builder.and(drillSchemeReceive.drillScheme.createTime.gt(endDrillTime));
        }
        query.select(Projections.bean(DrillSchemeReceive.class
                ,drillSchemeReceive.drillScheme
                ,drillSchemeReceive.sendTime
                ,drillSchemeReceive.reciever
                ,drillSchemeReceive.recieveTime
                ,drillSchemeReceive.recieveStatus)).where(builder);
        return findAll(query,page);
    }

    /**
     * 根据演练方案ID获取 演练方案接收信息
     * @param drillSchemeId
     * @return
     */
    public DrillSchemeReceive findByDrillSchemeId(String drillSchemeId) {
        QDrillSchemeReceive drillSchemeReceive = QDrillSchemeReceive.drillSchemeReceive;
        JPQLQuery<DrillSchemeReceive> query = from(drillSchemeReceive);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(drillSchemeId)){
            builder.and(drillSchemeReceive.drillScheme.id.eq(drillSchemeId));
        }
        DrillSchemeReceive result = query.where(builder).fetchOne();
        return result;
    }

    /**
     * 根据条件查询 方案接受部门的 接受状态信息列表
     * @param searchVo
     * @return
     */
    public List<DrillSchemeReceive> findList(DrillSchemeReceiveSearchVo searchVo) {
        QDrillSchemeReceive drillSchemeReceive = QDrillSchemeReceive.drillSchemeReceive;
        JPQLQuery<DrillSchemeReceive> query = from(drillSchemeReceive);
        BooleanBuilder builder = new BooleanBuilder();

        String drillSchemeId = searchVo.getDrillSchemeId();
        String sendType = searchVo.getSendType();
        if (StringUtils.hasText(drillSchemeId)){
            builder.and(drillSchemeReceive.drillScheme.id.eq(drillSchemeId));
        }
        if (StringUtils.hasText(sendType)){
            builder.and(drillSchemeReceive.sendType.eq(sendType));
        }
        query.select(Projections.bean(DrillSchemeReceive.class
            ,drillSchemeReceive.id
                ,drillSchemeReceive.drillScheme
                ,drillSchemeReceive.sendType
                ,drillSchemeReceive.sendTime
                ,drillSchemeReceive.orgId
                ,drillSchemeReceive.orgName
                ,drillSchemeReceive.reciever
                ,drillSchemeReceive.recieveTime
                ,drillSchemeReceive.recieveStatus
        )).where(builder);
        return findAll(query);
    }

    /**
     * 判断要上报、下发是否已存在
     * @param searchVo
     * @return
     */
    public List<DrillSchemeReceive> findIsExist(DrillSchemeReceiveSearchVo searchVo) {
        QDrillSchemeReceive drillSchemeReceive = QDrillSchemeReceive.drillSchemeReceive;
        JPQLQuery<DrillSchemeReceive> query = from(drillSchemeReceive);
        BooleanBuilder builder = new BooleanBuilder();
        String drillSchemeId = searchVo.getDrillSchemeId();
        String sendType = searchVo.getSendType();
        List<DrillSchemeReceiveVo> orgIdANames = searchVo.getOrgIdANames();
        List<String> orgIds = orgIdANames.stream().map(temp -> temp.getOrgId()).collect(Collectors.toList());

        if (StringUtils.hasText(drillSchemeId)){
            builder.and(drillSchemeReceive.drillScheme.id.eq(drillSchemeId));
        }
        if (StringUtils.hasText(sendType)){
            builder.and(drillSchemeReceive.sendType.eq(sendType));
        }
        if (!CollectionUtils.isEmpty(orgIds)){
            builder.and(drillSchemeReceive.orgId.in(orgIds));
        }
        query.select(Projections.bean(DrillSchemeReceive.class
                ,drillSchemeReceive.id
                ,drillSchemeReceive.drillScheme
                ,drillSchemeReceive.sendType
                ,drillSchemeReceive.sendTime
                ,drillSchemeReceive.orgId
                ,drillSchemeReceive.orgName
                ,drillSchemeReceive.reciever
                ,drillSchemeReceive.recieveTime
                ,drillSchemeReceive.recieveStatus
        )).where(builder);
        return findAll(query);
    }
}
