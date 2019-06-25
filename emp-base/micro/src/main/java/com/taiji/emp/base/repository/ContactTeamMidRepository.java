package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.entity.QContactMid;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@Repository
@Transactional(
        readOnly = true
)
public class ContactTeamMidRepository extends BaseJpaRepository<ContactMid,String> {


    //不带分页信息，查询通讯录组列表
    public Page<ContactMid> findList(ContactTeamsPageVo contactTeamsPageVo, Pageable pageable){
        JPQLQuery<ContactMid> query = buildQuery(contactTeamsPageVo);
        return findAll(query,pageable);
    }

    private JPQLQuery<ContactMid> buildQuery(ContactTeamsPageVo contactTeamsPageVo){
        QContactMid contactMid = QContactMid.contactMid;
        JPQLQuery<ContactMid> query = from(contactMid);

        BooleanBuilder builder = new BooleanBuilder();
        String name = contactTeamsPageVo.getName();
        String teamId = contactTeamsPageVo.getTeamId();
        String telephone = contactTeamsPageVo.getTelephone();

        if(StringUtils.hasText(teamId)){
            builder.and(contactMid.teamId.eq(teamId));
        }

        if(StringUtils.hasText(name)){
            builder.and(contactMid.addrName.contains(name));
        }
        if(StringUtils.hasText(telephone)){
            builder.and(contactMid.contact.mobile.contains(telephone));
        }
        query.select(
                Projections.bean(ContactMid.class
                        ,contactMid.id
                        ,contactMid.addrName
                        ,contactMid.teamId
                        ,contactMid.teamName
                        ,contactMid.contact
                )).where(builder);
        return query;
    }





}
