package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Contact;
import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.vo.ContactMidVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录管理 mapper ContactMapper
 * @author sun yi
 * @date 2018年10月22日
 */
@Mapper(componentModel = "spring")
public interface ContactMidMapper extends BaseMapper<ContactMid,ContactMidVo>{

    default RestPageImpl<ContactMidVo> entityPageToVoPage(Page<ContactMid> entityPage, Pageable page){
        if ( entityPage == null || page == null) {
            return null;
        }

        List<ContactMid> content = entityPage.getContent();
        List<ContactMidVo> list = new ArrayList<>(content.size());
        for (ContactMid entity :content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list,page,entityPage.getTotalElements());
        return voPage;
    }
    default ContactMidVo entityToVoForList(ContactMid entity){
        if (entity == null){
            return null;
        }
        ContactMidVo contactMidVo = new ContactMidVo();
        contactMidVo.setId(entity.getId());
        contactMidVo.setTeamId(entity.getTeamId());
        contactMidVo.setTeamName(entity.getTeamName());

        if(entity.getContact()!=null) {
            Contact contact = entity.getContact();
            ContactVo contactVo = new ContactVo();
            contactVo.setId(contact.getId());
            contactVo.setOrgId(contact.getOrgId());
            contactVo.setOrgName(contact.getOrgName());
            if(contact.getDutyTypeId()!=null) {
                contactVo.setDutyTypeId(contact.getDutyTypeId());
                contactVo.setDutyTypeName(contact.getDutyTypeName());
            }
            contactMidVo.setAddrName(contact.getName());
            contactVo.setSex(contact.getSex());
            contactVo.setAddress(contact.getAddress());
            contactVo.setPostCode(contact.getPostCode());
            contactVo.setAddrSeq(contact.getAddrSeq());
            contactVo.setTelephone(contact.getTelephone());
            contactVo.setHomeTel(contact.getHomeTel());
            contactVo.setMobile(contact.getMobile());
            contactVo.setEmail(contact.getEmail());
            contactVo.setOtherWay(contact.getOtherWay());
            contactVo.setAddType(contact.getAddType());
            contactVo.setFax(contact.getFax());
            contactVo.setNotes(contact.getNotes());
            contactVo.setBirthDate(contact.getBirthDate());
            contactMidVo.setContact(contactVo);
        }
        return contactMidVo;
    }

}
