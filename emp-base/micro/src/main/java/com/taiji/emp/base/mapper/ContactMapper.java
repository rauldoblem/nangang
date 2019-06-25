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
public interface ContactMapper extends BaseMapper<Contact,ContactVo>{

    //将分页的ContactMid，转为分页的 ContactVo
    default RestPageImpl<ContactVo> entityPageMidToVoPage(Page<ContactMid> entityPage, Pageable page){
        if ( entityPage == null || page == null) {
            return null;
        }

        List<ContactMid> content = entityPage.getContent();
        List<ContactVo> list = new ArrayList<>();
        for (ContactMid entity :content){
            list.add(entityToVoForList(entity.getContact()));
        }
        RestPageImpl voPage = new RestPageImpl(list,page,entityPage.getTotalElements());
        return voPage;
    }
    default ContactVo entityToVoForList(Contact entity){
        if (entity == null){
            return null;
        }
        ContactVo contactVo = new ContactVo();
        contactVo.setId(entity.getId());
        contactVo.setOrgId(entity.getOrgId());
        contactVo.setOrgName(entity.getOrgName());
        contactVo.setDutyTypeId(entity.getDutyTypeId());
        contactVo.setDutyTypeName(entity.getDutyTypeName());
        contactVo.setName(entity.getName());
        contactVo.setSex(entity.getSex());
        contactVo.setAddress(entity.getAddress());
        contactVo.setPostCode(entity.getPostCode());
        contactVo.setAddrSeq(entity.getAddrSeq());
        contactVo.setTelephone(entity.getTelephone());
        contactVo.setHomeTel(entity.getHomeTel());
        contactVo.setMobile(entity.getMobile());
        contactVo.setEmail(entity.getEmail());
        contactVo.setOtherWay(entity.getOtherWay());
        contactVo.setAddType(entity.getAddType());
        contactVo.setFax(entity.getFax());
        contactVo.setNotes(entity.getNotes());
        contactVo.setBirthDate(entity.getBirthDate());
        return contactVo;
    }

}
