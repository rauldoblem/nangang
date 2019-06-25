package com.taiji.emp.event.infoDispatch.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.entity.AcceptDeal;
import com.taiji.emp.event.infoDispatch.entity.QAcceptDeal;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class AcceptDealRepository extends BaseJpaRepository<AcceptDeal,String> {

    @Autowired
    private AcceptRepository acceptRepository;

    //查询分页AcceptDeal对象
    public Page<AcceptDeal> findPage(InfoPageVo infoPageVo, Pageable pageable){

        String buttonType = infoPageVo.getButtonType();
        Assert.hasText(buttonType,"选择标签类型buttonType不能为空");

        String eventName = infoPageVo.getEventName();
        List<String> eventTypeIds = infoPageVo.getEventTypeIds();
        String eventGradeId = infoPageVo.getEventGradeId();
        LocalDateTime startDate = infoPageVo.getStartDate();
        LocalDateTime endDate = infoPageVo.getEndDate();

        String createOrgId = infoPageVo.getCreateOrgId();
        Assert.hasText(createOrgId,"createOrgId不能为空");

        QAcceptDeal acceptDeal = QAcceptDeal.acceptDeal;
        JPQLQuery<AcceptDeal> query = from(acceptDeal);

        BooleanBuilder builder = new BooleanBuilder();
        if(!StringUtils.isEmpty(buttonType)){ //查询选择标签类型：00信息录入，01接收待办；10信息已办，11接收已办
            switch (buttonType){
                case EventGlobal.INFO_DISPATCH_INFO_CREATE:
                    builder.and(acceptDeal.createOrgId.eq(createOrgId));  //userOrg == createOrgId
                    builder.and(acceptDeal.dealOrgId.eq(createOrgId)); //userOrg == deal_org
                    builder.and(acceptDeal.dealFlag.eq(EventGlobal.INFO_DISPATCH_UNSEND)); //处理状态位：0
                    break;
                case EventGlobal.INFO_DISPATCH_ACCEPT_UNDO:
                    builder.and(acceptDeal.createOrgId.ne(createOrgId)); //userOrg != createOrgId
                    builder.and(acceptDeal.dealOrgId.eq(createOrgId)); //userOrg == deal_org
                    builder.and(acceptDeal.dealFlag.eq(EventGlobal.INFO_DISPATCH_UNSEND)); //处理状态位：0
                    break;
                case EventGlobal.INFO_DISPATCH_INFO_DONE:
                    builder.and(acceptDeal.createOrgId.eq(createOrgId));//userOrg == createOrgId
                    builder.and(acceptDeal.dealOrgId.eq(createOrgId));//userOrg == deal_org
                    builder.and(acceptDeal.dealFlag.ne(EventGlobal.INFO_DISPATCH_UNSEND)); //处理状态位：非0
                    break;
                case EventGlobal.INFO_DISPATCH_ACCEPT_DONE:
                    builder.and(acceptDeal.createOrgId.ne(createOrgId)); //userOrg != createOrgId
                    builder.and(acceptDeal.dealOrgId.eq(createOrgId)); //userOrg == deal_org
                    builder.and(acceptDeal.dealFlag.ne(EventGlobal.INFO_DISPATCH_UNSEND)); //处理状态位：非0
                    break;
                default:break;
            }
        }

        if(!StringUtils.isEmpty(eventName)){//信息名称
            builder.and(acceptDeal.imAccept.eventName.contains(eventName));
        }

        if(null!=eventTypeIds&&eventTypeIds.size()>0){//事件类型选择Ids
            builder.and(acceptDeal.imAccept.eventTypeId.in(eventTypeIds));
        }

        if(!StringUtils.isEmpty(eventGradeId)){//事件等级
            builder.and(acceptDeal.imAccept.eventGradeId.eq(eventGradeId));
        }

        if(null!=startDate){//事发时间——查询开始时间
            builder.and(acceptDeal.imAccept.occurTime.after(startDate));
        }

        if(null!=endDate){//事发时间——查询结束时间
            builder.and(acceptDeal.imAccept.occurTime.before(endDate));
        }

        builder.and(acceptDeal.imAccept.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder).orderBy(acceptDeal.dealTime.desc());

        return findAll(query,pageable);
    }

    //初报/续报 新增AcceptDeal对象
    @Transactional
    public AcceptDeal createInfo(Accept accept){
        AcceptDeal acceptDeal = new AcceptDeal();
        acceptDeal.setImAccept(accept);
        acceptDeal.setAcceptId(accept.getId());
        acceptDeal.setCreateOrgId(accept.getCreateOrgId());
        acceptDeal.setCreateOrgName(accept.getCreateOrgName());
        acceptDeal.setDealFlag(EventGlobal.INFO_DISPATCH_UNSEND); //未发送
        acceptDeal.setDealOrgId(accept.getCreateOrgId());
        acceptDeal.setDealOrgName(accept.getCreateOrgName());
        acceptDeal.setDealPersonId(accept.getCreateUserId()); //处理用户id
        acceptDeal.setDealPersonName(accept.getCreateBy());
        return super.save(acceptDeal);
    }

    //报送信息 发送
    @Transactional
    public AcceptDeal sendInfo(AcceptDeal entity){

        String createOrgId = entity.getCreateOrgId();
        Assert.hasText(createOrgId,"createOrgId 不能为空");
        String dealOrgId = entity.getDealOrgId();
        Assert.hasText(dealOrgId,"dealOrgId 不能为空");

        AcceptDeal result;

//        if(createOrgId.equals(dealOrgId)){ //针对自建录入
//            entity.setDealFlag(EventGlobal.INFO_DISPATCH_SEND); //已发送
//            String acceptDealId = entity.getId();
//            Assert.hasText(acceptDealId,"acceptDealId 不能为空");
//            AcceptDeal temp = super.findOne(acceptDealId);
//            BeanUtils.copyNonNullProperties(entity,temp);
//            result = super.save(temp);
//        }else{ //针对报送其他录入
            //更新新增记录的处理状态：已发送
            String acceptDealId = entity.getId();
            Assert.hasText(acceptDealId,"acceptDealId 不能为空");
            AcceptDeal temp = super.findOne(acceptDealId);
            temp.setDealFlag(EventGlobal.INFO_DISPATCH_SEND);
            temp.setDealPersonId(entity.getDealPersonId()); //更新处理用户
            temp.setDealPersonName(entity.getDealPersonName());
            temp.setAcceptId(entity.getAcceptId());
            super.save(temp);
            //新增发送信息处理记录
            entity.setId(null);
            entity.setDealFlag(EventGlobal.INFO_DISPATCH_UNSEND); //未发送
            result = super.save(entity);
//        }

        return result;
    }

    //操作：退回、办结、生成事件/更新事件
    @Transactional
    public AcceptDeal operateInfo(AcceptDeal entity,String buttonFlag){
        String acceptId = entity.getAcceptId();
        Assert.hasText(acceptId,"acceptId 不能为空");
        QAcceptDeal acceptDeal = QAcceptDeal.acceptDeal;
        List<AcceptDeal> list=findAll(acceptDeal.imAccept.id.eq(acceptId)
                ,new Sort(Sort.Direction.ASC,"dealTime"));
        Assert.notNull(list,"List<AcceptDeal> 不能为 null");
        if(null!=list&&list.size()>0){
            for(AcceptDeal temp:list){
                temp.setAcceptId(entity.getAcceptId());
                if(EventGlobal.INFO_DISPATCH_BUTTON_FINISH.equals(buttonFlag)){ //办结操作
                    temp.setDealFlag(EventGlobal.INFO_DISPATCH_FINISH);
                }else if(EventGlobal.INFO_DISPATCH_BUTTON_RETURN.equals(buttonFlag)){ //退回操作
                    temp.setDealFlag(EventGlobal.INFO_DISPATCH_RETURN);
                    temp.setReturnReason(entity.getReturnReason());
                }else if(EventGlobal.INFO_DISPATCH_BUTTON_GENERATE_EVENT.equals(buttonFlag)){ //生成事件
                    temp.setDealFlag(EventGlobal.INFO_DISPATCH_GENERATE_EVENT);
                    temp.setEventId(entity.getEventId());
                }else if(EventGlobal.INFO_DISPATCH_BUTTON_UPDATE_EVENT.equals(buttonFlag)){//更新事件
                    String eventId = getEventIdByFirstReportId(entity.getImAccept().getFirstReportId());
                    Assert.hasText(eventId,"eventId 不能为空");
                    temp.setEventId(eventId);
                    temp.setDealFlag(EventGlobal.INFO_DISPATCH_GENERATE_EVENT);
                }
                super.save(temp);
            }
        }
        return findOne(entity.getId());
    }

    //根据初报Id 获取已生成事件的 eventId
    public String getEventIdByFirstReportId(String firstReportId){
        Assert.hasText(firstReportId,"初报Id 不能为空");
        QAcceptDeal acceptDeal = QAcceptDeal.acceptDeal;
        JPQLQuery<AcceptDeal> query = from(acceptDeal);

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(acceptDeal.imAccept.id.eq(firstReportId)); //匹配acceptId
        builder.and(acceptDeal.imAccept.delFlag.eq(DelFlagEnum.NORMAL.getCode())); //信息删除标志位：1未删除

        String eventId =query.where(builder).fetchAll()
                        .select(acceptDeal.eventId)
                        .groupBy(acceptDeal.eventId)
                        .fetchFirst();
        return eventId;
    }

    //通过eventId查询所有初报续报对象
    public List<Accept> findAcceptListByEventId(String eventId){

        QAcceptDeal acceptDeal = QAcceptDeal.acceptDeal;
        JPQLQuery<AcceptDeal> query = from(acceptDeal);

        List<String> acceptIds = query.select(acceptDeal.imAccept.id)
                .where(acceptDeal.eventId.eq(eventId))
                .groupBy(acceptDeal.imAccept.id)
                .fetch();

        List<Accept> list = acceptRepository.findAllByIds(acceptIds);

        return list;
    }

    //通过eventId 将所有该事件下的报送信息置位为已办结
    @Transactional
    public void finishInfosByEventId(String eventId){
        QAcceptDeal acceptDeal = QAcceptDeal.acceptDeal;
        JPAUpdateClause updateClause = querydslUpdate(acceptDeal);
        updateClause.set(acceptDeal.dealFlag,EventGlobal.INFO_DISPATCH_FINISH)
                .where(acceptDeal.eventId.eq(eventId))
                .execute();
    }

    public Page<AcceptDeal> findAllPage(InfoPageVo infoPageVo, Pageable page) {
        List<String> buttonTypeList = infoPageVo.getButtonTypeList();

        String eventName = infoPageVo.getEventName();
        List<String> eventTypeIds = infoPageVo.getEventTypeIds();
        String eventGradeId = infoPageVo.getEventGradeId();
        LocalDateTime startDate = infoPageVo.getStartDate();
        LocalDateTime endDate = infoPageVo.getEndDate();

        String createOrgId = infoPageVo.getCreateOrgId();
        Assert.hasText(createOrgId,"createOrgId不能为空");

        QAcceptDeal acceptDeal = QAcceptDeal.acceptDeal;
        JPQLQuery<AcceptDeal> query = from(acceptDeal);

        BooleanBuilder builder = new BooleanBuilder();
        if(!CollectionUtils.isEmpty(buttonTypeList)){ //查询选择标签类型：00信息录入，10信息已办
            builder.and(acceptDeal.createOrgId.eq(createOrgId));  //userOrg == createOrgId
            builder.and(acceptDeal.dealOrgId.eq(createOrgId));//userOrg == deal_org
//            builder.and(acceptDeal.dealFlag.eq(EventGlobal.INFO_DISPATCH_UNSEND)); //处理状态位：0
        }

        if(!StringUtils.isEmpty(eventName)){//信息名称
            builder.and(acceptDeal.imAccept.eventName.contains(eventName));
        }

        if(null!=eventTypeIds&&eventTypeIds.size()>0){//事件类型选择Ids
            builder.and(acceptDeal.imAccept.eventTypeId.in(eventTypeIds));
        }

        if(!StringUtils.isEmpty(eventGradeId)){//事件等级
            builder.and(acceptDeal.imAccept.eventGradeId.eq(eventGradeId));
        }

        if(null!=startDate){//事发时间——查询开始时间
            builder.and(acceptDeal.imAccept.occurTime.after(startDate));
        }

        if(null!=endDate){//事发时间——查询结束时间
            builder.and(acceptDeal.imAccept.occurTime.before(endDate));
        }

        builder.and(acceptDeal.imAccept.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder).orderBy(acceptDeal.dealTime.desc());

        return findAll(query,page);
    }
}
