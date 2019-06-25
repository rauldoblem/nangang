package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.entity.QContactMid;
import com.taiji.emp.base.vo.ContactMidVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;



@Repository
@Transactional(
        readOnly = true
)
public class ContactMidRepository extends BaseJpaRepository<ContactMid,String> {
    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public ContactMid save(ContactMid entity){
        Assert.notNull(entity,"ContactMid 对象不能为 null");
        return super.save(entity);
    }

    //不带分页信息，查询通讯录列表
    public List<ContactMid> findList(ContactMidVo vo){
        JPQLQuery<ContactMid> query = buildQuery(vo);
        return findAll(query);
    }
    private JPQLQuery<ContactMid> buildQuery(ContactMidVo vo){
        QContactMid contactMid = QContactMid.contactMid;
        JPQLQuery<ContactMid> query = from(contactMid);
        List<String> contactIdList = vo.getContactIdList();
        String teamId = vo.getTeamId();
        BooleanBuilder builder = new BooleanBuilder();
        if(!CollectionUtils.isEmpty(contactIdList)){
            builder.and(contactMid.contact.id.in(contactIdList));
        }
        if(StringUtils.hasText(teamId)){
            builder.and(contactMid.teamId.eq(teamId));
        }
        query.select(
                Projections.bean(ContactMid.class
                        ,contactMid.id
                        ,contactMid.contact
                        ,contactMid.teamId
                )).where(builder);
        return query;
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteList(List<String> list)
    {   //根据多个ID进行删除 通讯录中间表
        Query query = this.em.createNativeQuery("delete from ED_CONTACT_MID where id IN (:listStr)");
        query.setParameter("listStr",list);
        query.executeUpdate();
    }

    /**
     * 判断当前人员是否在本分组下
     * @param contactMid
     * @return
     */
    public ContactMid findEntityByAddrIdAndTeamId(ContactMid contactMid) {
        QContactMid entity = QContactMid.contactMid;
        JPQLQuery<ContactMid> query = from(entity);
        BooleanBuilder builder = new BooleanBuilder();
        String teamId = contactMid.getTeamId();
        String addrId = contactMid.getContact().getId();
        if(StringUtils.hasText(teamId)){
            builder.and(entity.teamId.eq(teamId));
        }
        if(StringUtils.hasText(addrId)){
            builder.and(entity.contact.id.eq(addrId));
        }
        ContactMid one = query.select(Projections.bean(ContactMid.class, entity.id)).where(builder).fetchOne();
        return one;
    }
}
