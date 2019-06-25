package com.taiji.emp.base.service;

import com.taiji.emp.base.common.constant.ContactsGlobal;
import com.taiji.emp.base.entity.ContactMid;
import com.taiji.emp.base.entity.ContactTeam;
import com.taiji.emp.base.repository.ContactTeamMidRepository;
import com.taiji.emp.base.repository.ContactTeamRepository;
import com.taiji.emp.base.searchVo.contacts.ContactTeamListVo;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class ContactsTeamService extends BaseService<ContactTeam,String> {

    @Autowired
    private ContactTeamRepository repository;

    @Autowired
    private ContactTeamMidRepository contactTeamMidRepository;
    public ContactTeam create(ContactTeam entity){
        Assert.notNull(entity,"ContactTeam 对象不能为 null");
        //将父id的 leaf 字段改为1，当前节点下有节点（1不是叶子节点，0是叶子节点）
        Assert.notNull(entity.getParentId(),"ParentId 字段不能为 null");
        //父通讯录组对象
        ContactTeam contactTeam = repository.findOne(entity.getParentId());
        if(null != contactTeam) {
            entity.setOrders(contactTeam.getOrders() + 1);
        }else {
            entity.setOrders(1);
        }
        //将新增的 orders 字段 （父+1）
        /*if(entity.getOrders()==null){
            List<ContactTeam> contactTeams =repository.findListParendId(entity.getParentId());
            if(contactTeams.size()!=0){
                entity.setOrders(contactTeams.get(0).getOrders()+1);
            }else {
                entity.setOrders(1);
            }
        }*/
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public ContactTeam update(ContactTeam entity){
        Assert.notNull(entity,"ContactTeam 对象不能为 null");
        return repository.save(entity);
    }

    public ContactTeam findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        ContactTeam result = repository.findOne(id);
        return result;
    }

    public void deleteIdAll(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        ContactTeam entity = repository.findOne(id);
        if(entity != null){
            repository.deleteLogic(entity, delFlagEnum);
        }
    }

    public List<ContactTeam> findList(ContactTeamListVo contactTeamListVo){
        List<ContactTeam> result = repository.findList(contactTeamListVo);
        return result;
    }

    public List<ContactTeam> findListByParentId(List<ContactTeam> list,String parentId){
        ContactTeamListVo contactTeamListVo = new ContactTeamListVo();
        contactTeamListVo.setParentIdStr(parentId);
        List<ContactTeam> result = repository.findList(contactTeamListVo);
        for (ContactTeam contact:result) {
            String leaf = contact.getLeaf();
            if(!ContactsGlobal.CONTACT_LEFT_NO.equals(leaf)){
                list.add(contact);
                findListByParentId(list,contact.getId());
            }else {
                list.add(contact);
            }
        }
        return list;
    }



    public Page<ContactMid> findContactsList(ContactTeamsPageVo contactTeamsPageVo, Pageable pageable){
        Page<ContactMid> result = contactTeamMidRepository.findList(contactTeamsPageVo,pageable);
        return result;
    }

}
