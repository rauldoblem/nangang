package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.Contact;
import com.taiji.emp.duty.entity.DutyTeam;
import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 值班人员表 PersonMapper
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends BaseMapper<Person, PersonVo> {

    @Override
    default Person voToEntity(PersonVo vo){
        if ( vo == null) {
            return null;
        }
        Person entity = new Person();
        entity.setId(vo.getId());
        DutyTeam dutyTeam = new DutyTeam();
        dutyTeam.setId(vo.getDutyTeamId());
        Contact contact = new Contact();
        contact.setId(vo.getAddrId());
        entity.setContact(contact);
        entity.setAddrName(vo.getAddrName());
        entity.setDutyTeam(dutyTeam);
        entity.setDutyIName(vo.getDutyteamName());
        entity.setOrderInTeam(vo.getOrderInTeam());
        entity.setCreateBy(vo.getCreateBy());
        entity.setCreateTime(vo.getCreateTime());
        return entity;
    }

    @Override
    default RestPageImpl<PersonVo> entityPageToVoPage(Page<Person> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<Person> people = entityPage.getContent();
        List<PersonVo> list = new ArrayList<>(people.size());
        for (Person person : people){
            list.add(entityToVoForList(person));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    @Override
    default List<PersonVo> entityListToVoList(List<Person> entityList)
    {
        if ( entityList == null) {
            return null;
        }


        List<PersonVo> list = new ArrayList<>(entityList.size());

        for ( Person entity : entityList ) {
            list.add( entityToVoForList(entity) );
        }

        return list;
    }

    default PersonVo entityToVoForList(Person entity){
        if ( entity == null ) {
            return null;
        }
        PersonVo personVo = new PersonVo();
        personVo.setId(entity.getId());
        personVo.setAddrName(entity.getAddrName());
        DutyTeam dutyTeam = entity.getDutyTeam();
        if (null != dutyTeam) {
            personVo.setDutyTeamId(dutyTeam.getId());
        }
        Contact contact = entity.getContact();
        if (null != contact) {
            personVo.setAddrId(contact.getId());
            personVo.setMobile(contact.getMobile());
            personVo.setOrgName(contact.getOrgName());
            personVo.setTelephone(contact.getTelephone());
        }
        personVo.setDutyteamName(entity.getDutyIName());
        personVo.setOrderInTeam(entity.getOrderInTeam());
        return personVo;
    }
}
