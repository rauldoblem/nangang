package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.Contact;
import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.repository.ContactMidRepository;
import com.taiji.emp.base.repository.ContactRepository;
import com.taiji.emp.base.vo.ContactMidVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class ContactsMidService extends BaseService<ContactMid,String> {

    @Autowired
    private ContactMidRepository repository;
    @Autowired
    private ContactRepository contactRepository;

    public ContactMid findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        ContactMid result = repository.findOne(id);
        return result;
    }

//    public List<ContactMid> findList(MultiValueMap<String,Object> params){
//        List<ContactMid> result = repository.findList(params);
//        return result;
//    }


    public void addContactToTeam(ContactMidVo contactMidVo){
        Assert.notNull(contactMidVo,"contactMidVo 对象不能为 null");
       List<String> list = contactMidVo.getContactIdList();
        for (String str:list) {
            ContactMid contactMid = new ContactMid();
            Contact contact = new Contact();
            contact.setId(str);
            contactMid.setContact(contact);
            contactMid.setAddrName(contactRepository.findOne(str).getName());
            contactMid.setTeamId(contactMidVo.getTeamId());
            contactMid.setTeamName(contactMidVo.getTeamName());
            ContactMid result = repository.findEntityByAddrIdAndTeamId(contactMid);
            if (null == result) {
                repository.save(contactMid);
            }
        }
    }

    public void removeContactToTeam(ContactMidVo vo){

        Assert.notNull(vo,"list 对象不能为 null");

        List<ContactMid> list = repository.findList(vo);
        Assert.notNull(list,"list 对象不能为 null");
       List<String> list1 = new ArrayList<>();
        for (ContactMid contactMid:list) {
            list1.add(contactMid.getId());
        }
        repository.deleteList(list1);
    }

    /**
     * 获取当前通讯录人员在某些分组下的记录
     * @param addrId
     * @return
     */
    public List<ContactMid> findList(String addrId) {
        Assert.hasText(addrId,"addrId不能为null或空字符串!");
        ContactMidVo vo = new ContactMidVo();
        List<String> contactIdList = new ArrayList<>(1);
        contactIdList.add(addrId);
        vo.setContactIdList(contactIdList);
        List<ContactMid> list = repository.findList(vo);
        return list;
    }

    /**
     * 删除掉当前通讯录人员在某些分组下的记录
     * @param entity
     */
    public void deleteLogic(ContactMid entity, DelFlagEnum delFlagEnum) {
        Assert.notNull(entity,"entity不能为 null");
        repository.deleteLogic(entity,delFlagEnum);
    }


    public void removeTeamList(List<ContactMid> list) {
        List<String> idList = list.stream().map(temp -> temp.getId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(idList)) {
            repository.deleteList(idList);
        }
    }
}
