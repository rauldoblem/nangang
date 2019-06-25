package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.Contact;
import com.taiji.emp.base.entity.QContact;
import com.taiji.emp.base.searchVo.contacts.ContactPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class ContactRepository extends BaseJpaRepository<Contact,String> {

    //带分页信息，查询通讯录列表
    public Page<Contact> findPage(ContactPageVo contactPageVo, Pageable pageable){
        JPQLQuery<Contact> query = buildQuery(contactPageVo);
        return findAll(query,pageable);
    }

    //不带分页信息，查询通讯录列表
    public List<Contact> findList(ContactPageVo contactPageVo){
        JPQLQuery<Contact> query = buildQuery(contactPageVo);
        return findAll(query);
    }

    private JPQLQuery<Contact> buildQuery(ContactPageVo contactPageVo){
        QContact contact = QContact.contact;
        JPQLQuery<Contact> query = from(contact);

        BooleanBuilder builder = new BooleanBuilder();
        String name = contactPageVo.getName();
        String orgId = contactPageVo.getOrgId();
        String telephone = contactPageVo.getTelephone();
        String mobileFlag = contactPageVo.getMobileFlag();
        List<String> orgIds = contactPageVo.getOrgIds();
        if(!CollectionUtils.isEmpty(orgIds)){
            builder.and(contact.orgId.in(orgIds));
        }else {
            if(StringUtils.hasText(orgId)) {
                builder.and(contact.orgId.eq(orgId));
            }
        }
//        if(params.containsKey("dutyTypeId")){
//            builder.and(contact.dutyTypeId.eq(params.getFirst("dutyTypeId").toString()));
//        }
        if(StringUtils.hasText(name)){
            builder.and(contact.name.contains(name.trim()));
        }
        if(StringUtils.hasText(telephone)){
            builder.and(contact.mobile.contains(telephone.trim()));
        }
        if(StringUtils.hasText(mobileFlag)){
            if (mobileFlag.equals("1")){
                builder.and(contact.mobile.isNotEmpty());
            }
        }
        builder.and(contact.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Contact.class
                        ,contact.id
                        ,contact.orgId
                        ,contact.orgName
                        ,contact.dutyTypeId
                        ,contact.dutyTypeName
                        ,contact.name
                        ,contact.sex
                        //,contact.birthdate
                        ,contact.address
                        ,contact.postCode
                        ,contact.addrSeq
                        ,contact.telephone
                        ,contact.homeTel
                        ,contact.mobile
                        ,contact.email
                        ,contact.otherWay
                        ,contact.addType
                        ,contact.fax
                        ,contact.notes
                )).where(builder)
                .orderBy(contact.name.asc());
        return query;
    }

    @Override
    @Transactional
    public Contact save(Contact entity){
        Assert.notNull(entity,"Contact 对象不能为 null");

        Contact result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            Contact temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }
}
