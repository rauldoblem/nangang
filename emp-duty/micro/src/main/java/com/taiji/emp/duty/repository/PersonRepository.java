package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.entity.QPerson;
import com.taiji.emp.duty.searchVo.PersonListVo;
import com.taiji.emp.duty.searchVo.PersonPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PersonRepository extends BaseJpaRepository<Person,String> {

    /**
     * 新增或修改值班人员信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public Person save(Person entity){
        Assert.notNull(entity,"entity对象不能为空");
        Person result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else{
            Person temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }


    /**
     * 根据条件查询值班人员列表
     * @param vo
     * @return
     */
    public List<Person> findList(PersonListVo vo) {
        QPerson person = QPerson.person;

        JPQLQuery<Person> query = from(person);
        BooleanBuilder builder = new BooleanBuilder();

        List<String> orgIds = vo.getOrgIds();
        String mobile = vo.getMobile();
        String name = vo.getName();
        List<String> teamIds = vo.getTeamIds();

        List<String> addrIds = vo.getAddrIds();
        String dutyTeamId = vo.getDutyTeamId();
        //换班人员
        List<String> personIds = vo.getPersonIds();

        if (null != orgIds && orgIds.size() > 0){
            builder.and(person.dutyTeam.orgId.in(orgIds));
        }

        if (StringUtils.hasText(mobile)){
            builder.and(person.contact.mobile.contains(mobile));
        }

        //换班人员
        if (null != personIds && personIds.size() > 0){
            builder.and(person.contact.id.notIn(personIds));
        }

        if (StringUtils.hasText(name)){
            builder.and(person.addrName.eq(name));
        }
        if (null != teamIds && teamIds.size() > 0){
            builder.and(person.dutyTeam.id.in(teamIds));
        }

        if (null != addrIds && addrIds.size() > 0){
            builder.and(person.contact.id.in(addrIds));
        }
        if (StringUtils.hasText(dutyTeamId)){
            builder.and(person.dutyTeam.id.eq(dutyTeamId));
        }

        builder.and(person.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Person.class
                ,person.id
                ,person.contact
                ,person.addrName
                ,person.dutyTeam
                ,person.dutyIName
                ,person.orderInTeam
        )).where(builder).orderBy(person.createTime.desc());
        return findAll(query);
    }

    /**
     * 根据条件查询值班人员列表--分页
     * @param vo
     * @return
     */
    public Page<Person> findPage(PersonPageVo vo, Pageable pageable) {
        QPerson person = QPerson.person;

        JPQLQuery<Person> query = from(person);
        BooleanBuilder builder = new BooleanBuilder();

        List<String> orgIds = vo.getOrgIds();
        String mobile = vo.getMobile();
        String name = vo.getName();
        List<String> teamIds = vo.getTeamIds();

        if (null != orgIds && orgIds.size() > 0){
            builder.and(person.contact.orgId.in(orgIds));
        }

        if (StringUtils.hasText(name)){
            builder.and(person.addrName.contains(name));
        }

        if (StringUtils.hasText(mobile)){
            builder.and(person.contact.mobile.contains(mobile));
        }

        if (null != teamIds && teamIds.size() > 0){
            builder.and(person.dutyTeam.id.in(teamIds));
        }
        builder.and(person.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Person.class
                ,person.id
                ,person.contact
                ,person.addrName
                ,person.dutyTeam
                ,person.dutyIName
                ,person.orderInTeam
        )).where(builder).orderBy(person.orderInTeam.asc());
        return findAll(query,pageable);
    }

    /**
     * 获取分组下的人员信息
     * @param teamId
     * @return
     */
    public List<Person> findListByTeamId(String teamId) {
        QPerson person = QPerson.person;
        JPQLQuery<Person> query = from(person);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(teamId)){
            builder.and(person.dutyTeam.id.eq(teamId));
        }
        builder.and(person.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(Person.class
                ,person.id
                ,person.contact
                ,person.addrName
                ,person.dutyTeam
                ,person.dutyIName
                ,person.orderInTeam
        )).where(builder).orderBy(person.orderInTeam.asc());
        return findAll(query);
    }
}
