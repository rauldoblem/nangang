package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanOrgResponDetail;
import com.taiji.emp.res.entity.QPlanOrgResponDetail;
import com.taiji.emp.res.mapper.PlanOrgResponDetailMapper;
import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class PlanOrgResponDetailRepository extends BaseJpaRepository<PlanOrgResponDetail,String>{

    @Autowired
    PlanOrgResponDetailMapper mapper;

    //预案责任人、单位管理详情list -- 不分页
    public List<PlanOrgResponDetail> findList(PlanOrgResponDetailVo planOrgResponDetailVo){

        String orgresponId = planOrgResponDetailVo.getOrgResponId();
        QPlanOrgResponDetail planOrgResponDetail = QPlanOrgResponDetail.planOrgResponDetail;

        JPQLQuery<PlanOrgResponDetail> query = from(planOrgResponDetail);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(orgresponId)){
            builder.and(planOrgResponDetail.orgResponId.eq(orgresponId));
        }

        builder.and(planOrgResponDetail.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(PlanOrgResponDetail.class
                ,planOrgResponDetail.id
                ,planOrgResponDetail.orgResponId
                ,planOrgResponDetail.rspOrgId
                ,planOrgResponDetail.repOrgName
                ,planOrgResponDetail.principal
                ,planOrgResponDetail.principalTel
        )).where(builder)
                .orderBy(planOrgResponDetail.updateTime.desc());
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanOrgResponDetail save(PlanOrgResponDetail entity){
        Assert.notNull(entity,"planOrgResponDetail对象不能为 null");
        PlanOrgResponDetail result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            PlanOrgResponDetail temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    @Transactional
    public PlanOrgResponDetail save(PlanOrgResponDetailListVo vo,String orgResponId) {
        Assert.notNull(vo, "PlanOrgResponDetailListVo对象不能为 null");
        PlanOrgResponDetail result = null;
        //循环入库
        List<PlanOrgResponDetailVo> planOrgResponDetailVo = vo.getPlanOrgResponDetailVo();
        List<PlanOrgResponDetail> planOrgResponDetail = mapper.voListToEntityList(planOrgResponDetailVo);
        for (PlanOrgResponDetail detail : planOrgResponDetail) {
            detail.setDelFlag(DelFlagEnum.NORMAL.getCode());
            detail.setCreateBy(vo.getCreateBy());
            detail.setUpdateBy(vo.getUpdateBy());
            String id = detail.getId();
            if (StringUtils.isEmpty(id)) { //新增保存
                result = super.save(detail);
            } else {//编辑保存
//                //根据传入orgResponId获取要修改详情的id，循环更新
//                PlanOrgResponDetailVo detailVo = new PlanOrgResponDetailVo();
//                detailVo.setOrgresponId(orgResponId);
//                List<PlanOrgResponDetail> details = this.findList(detailVo);
//                for (PlanOrgResponDetail de : details) {
//                    PlanOrgResponDetail temp = super.findOne(de.getId());
//                    temp.setUpdateBy(vo.getUpdateBy());
//                    Assert.notNull(temp, "temp对象不能为 null");
//                    BeanUtils.copyNonNullProperties(detail, temp);
//                    result = super.save(temp);
//                }

            }

        }
        return result;
    }

    //预案责任人、单位管理详情list -- 不分页
    public List<PlanOrgResponDetail> findList(PlanOrgResponDetailListVo planOrgResponDetailListVo){

        List<String> ids = planOrgResponDetailListVo.getIds();
        QPlanOrgResponDetail planOrgResponDetail = QPlanOrgResponDetail.planOrgResponDetail;

        JPQLQuery<PlanOrgResponDetail> query = from(planOrgResponDetail);
        BooleanBuilder builder = new BooleanBuilder();

        if(null!=ids&&ids.size()>0){
            builder.and(planOrgResponDetail.orgResponId.in(ids));
        }

        builder.and(planOrgResponDetail.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(PlanOrgResponDetail.class
                ,planOrgResponDetail.id
                ,planOrgResponDetail.orgResponId
                ,planOrgResponDetail.rspOrgId
                ,planOrgResponDetail.repOrgName
                ,planOrgResponDetail.principal
                ,planOrgResponDetail.principalTel
        )).where(builder)
                .orderBy(planOrgResponDetail.updateTime.desc());
        return findAll(query);
    }

//    @Transactional
    /*public PlanOrgResponDetail updateDetail(PlanOrgResponDetailListVo vo,String orgResponId) {
        Assert.notNull(vo, "PlanOrgResponDetailListVo对象不能为 null");
        PlanOrgResponDetail result = null;
        //循环入库
        List<PlanOrgResponDetailVo> planOrgResponDetailVo = vo.getPlanOrgResponDetailVo();
        List<PlanOrgResponDetail> planOrgResponDetail = mapper.voListToEntityList(planOrgResponDetailVo);
        //根据传入orgResponId获取要修改详情的id，循环更新
        PlanOrgResponDetailVo detailVo = new PlanOrgResponDetailVo();
        detailVo.setOrgResponId(orgResponId);
        List<PlanOrgResponDetail> details = this.findList(detailVo);

        List<String> detailIds = details.stream().map(temp -> temp.getId()).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(planOrgResponDetail)) {
            for (PlanOrgResponDetail detail : planOrgResponDetail) {
                String detailId = detail.getId();
                if (!StringUtils.isEmpty(detailId)){
                    if(detailIds.contains(detailId)){
                        //编辑
                        PlanOrgResponDetail temp = super.findOne(detailId);
                        BeanUtils.copyNonNullProperties(detail, temp);
                        result = super.save(temp);
                        //删除掉编辑后的那条数据
                        detailIds.remove(detailId);
                    }
                }else {
                    //新增
                    result = super.save(detail);
                }
                if (detailIds.size() > 0){
                    //删除
                    for (String delId:detailIds) {
                        PlanOrgResponDetail tempDetail = super.findOne(delId);
                        tempDetail.setDelFlag(DelFlagEnum.NORMAL.getCode());
                        result = super.save(tempDetail);
                    }
                }
            }
        }
        return result;
    }*/

}
