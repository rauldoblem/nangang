package com.taiji.emp.drill.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.drill.entity.DrillPlanReceive;
import com.taiji.emp.drill.entity.QDrillPlanReceive;
import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class DrillPlanReceiveRepository extends BaseJpaRepository<DrillPlanReceive,String> {

    /**
     * 根据条件查询演练计划接收列表——分页
     * @param searchVo
     * @param pageable
     * @return
     */
    public Page<DrillPlanReceive> findPage(DrillPlanReceiveSearchVo searchVo, Pageable pageable) {
        QDrillPlanReceive drillPlanReceive = QDrillPlanReceive.drillPlanReceive;
        JPQLQuery<DrillPlanReceive> query = from(drillPlanReceive);
        BooleanBuilder builder = new BooleanBuilder();

        String drillName = searchVo.getDrillName();
        String drillStartTime = searchVo.getDrillStartTime();
        String drillEndTime = searchVo.getDrillEndTime();
        String drillWayId = searchVo.getDrillWayId();
        String orgIds = searchVo.getOrgIds();
        String[] orgIdArray = null;
        if (StringUtils.hasText(orgIds)){
            orgIdArray = orgIds.split(",");
        }
        String receiveOrgId = searchVo.getReceiveOrgId();
        if (StringUtils.hasText(drillName)){
            builder.and(drillPlanReceive.drillPlan.drillName.contains(drillName));
        }
        if (StringUtils.hasText(drillStartTime) && StringUtils.hasText(drillEndTime)){
            LocalDate startDrillTime = DateUtil.strToLocalDate(drillStartTime);
            LocalDate endDrillTime = DateUtil.strToLocalDate(drillEndTime);
            builder.and(drillPlanReceive.drillPlan.drillTime.between(startDrillTime,endDrillTime));
        }
        if (StringUtils.hasText(drillStartTime) && !StringUtils.hasText(drillEndTime)){
            LocalDate startDrillTime = DateUtil.strToLocalDate(drillStartTime);
            builder.and(drillPlanReceive.drillPlan.drillTime.lt(startDrillTime));
        }
        if (StringUtils.hasText(drillEndTime) && !StringUtils.hasText(drillStartTime)){
            LocalDate endDrillTime = DateUtil.strToLocalDate(drillEndTime);
            builder.and(drillPlanReceive.drillPlan.drillTime.gt(endDrillTime));
        }
        if (StringUtils.hasText(drillWayId)){
            builder.and(drillPlanReceive.drillPlan.drillWayId.eq(drillWayId));
        }

        if (null != orgIdArray && orgIdArray.length > 0){
            builder.and(drillPlanReceive.drillPlan.orgId.in(orgIdArray));
        }

        if (StringUtils.hasText(receiveOrgId)){
            builder.and(drillPlanReceive.orgId.eq(receiveOrgId));
        }

        query.select(Projections.bean(
                DrillPlanReceive.class
                ,drillPlanReceive.drillPlan
                ,drillPlanReceive.sendTime
                ,drillPlanReceive.reciever
                ,drillPlanReceive.recieveTime
                ,drillPlanReceive.recieveStatus
        )).where(builder);
        return findAll(query,pageable);
    }

    /**
     * 根据条件查询 计划接收部门的 接收状态信息列表
     * @param searchVo
     * @return
     */
    public List<DrillPlanReceive> findList(DrillPlanReceiveSearchVo searchVo) {
        QDrillPlanReceive drillPlanReceive = QDrillPlanReceive.drillPlanReceive;
        JPQLQuery<DrillPlanReceive> query = from(drillPlanReceive);
        BooleanBuilder builder = new BooleanBuilder();

        String drillPlanId = searchVo.getDrillPlanId();
        String sendType = searchVo.getSendType();

        if (StringUtils.hasText(drillPlanId)){
            builder.and(drillPlanReceive.drillPlan.id.eq(drillPlanId));
        }

        if (StringUtils.hasText(sendType)){
            builder.and(drillPlanReceive.sendType.eq(sendType));
        }

        query.select(Projections.bean(
                DrillPlanReceive.class
                ,drillPlanReceive.id
                ,drillPlanReceive.drillPlan
                ,drillPlanReceive.sendType
                ,drillPlanReceive.sendTime
                ,drillPlanReceive.orgId
                ,drillPlanReceive.orgName
                ,drillPlanReceive.reciever
                ,drillPlanReceive.recieveTime
                ,drillPlanReceive.recieveStatus
        )).where(builder);
        return findAll(query);
    }

    /**
     * 根据演练计划ID获取 演练计划接收信息
     * @param receiveVo
     * @return
     */
    public DrillPlanReceive findByDrillPlanId(DrillPlanReceiveVo receiveVo) {
        QDrillPlanReceive drillPlanReceive = QDrillPlanReceive.drillPlanReceive;
        JPQLQuery<DrillPlanReceive> query = from(drillPlanReceive);
        BooleanBuilder builder = new BooleanBuilder();
        String drillPlanId = receiveVo.getDrillPlanId();
        String orgId = receiveVo.getOrgId();
        if (StringUtils.hasText(drillPlanId)){
            builder.and(drillPlanReceive.drillPlan.id.eq(drillPlanId));
        }
        if (StringUtils.hasText(orgId)){
            builder.and(drillPlanReceive.orgId.eq(orgId));
        }
        DrillPlanReceive entity = query.where(builder).fetchOne();
        return entity;
    }

    /**
     * 判断要上报、下发是否已存在
     * @param vo
     * @return
     */
    public List<DrillPlanReceive> findIsExist(DrillPlanReceiveSearchVo vo) {
        QDrillPlanReceive drillPlanReceive = QDrillPlanReceive.drillPlanReceive;
        JPQLQuery<DrillPlanReceive> query = from(drillPlanReceive);
        BooleanBuilder builder = new BooleanBuilder();
        String drillPlanId = vo.getDrillPlanId();
        String sendType = vo.getSendType();
        List<DrillPlanVo> orgIdANames = vo.getOrgIdANames();
        List<String> orgIds = orgIdANames.stream().map(temp -> temp.getOrgId()).collect(Collectors.toList());
        if (StringUtils.hasText(drillPlanId)){
            builder.and(drillPlanReceive.drillPlan.id.eq(drillPlanId));
        }
        if (StringUtils.hasText(sendType)){
            builder.and(drillPlanReceive.sendType.eq(sendType));
        }
        if (!CollectionUtils.isEmpty(orgIds)){
            builder.and(drillPlanReceive.orgId.in(orgIds));
        }
        query.select(Projections.bean(
                DrillPlanReceive.class
                ,drillPlanReceive.id
                ,drillPlanReceive.drillPlan
                ,drillPlanReceive.sendType
                ,drillPlanReceive.sendTime
                ,drillPlanReceive.orgId
                ,drillPlanReceive.orgName
                ,drillPlanReceive.reciever
                ,drillPlanReceive.recieveTime
                ,drillPlanReceive.recieveStatus
        )).where(builder);
        return findAll(query);
    }


}
