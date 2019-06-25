package com.taiji.emp.base.service;

import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.feign.ContactsTeamClient;
import com.taiji.emp.base.searchVo.contacts.ContactTeamListVo;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.emp.base.vo.ContactTeamVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactsTeamService extends BaseService {

    private ContactsTeamClient contactsTeamClient;

    /**
     * 新增通讯录组
     */
    public void create(ContactTeamVo vo){
       contactsTeamClient.create(vo);
    }

    /**
     * 更新单条通讯录组
     */
    public void update(ContactTeamVo vo,String id){
        Assert.hasText(id,"id不能为空字符串");
        contactsTeamClient.update(vo,id);
    }



    /**
     * 根据id获取单条通讯录组记录
     */
    public ContactTeamVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<ContactTeamVo> resultVo = contactsTeamClient.findOne(id);
        ContactTeamVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id删除单条通讯录组记录
     * 存在子节点一并删除
     */
    public void deleteIdAll(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = contactsTeamClient.deletes(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取通讯录组list(带分页)
     */
    public List<ContactTeamVo> findList(Principal principal){
        UserVo userVo = getCurrentUser(principal);
        String userVoId = userVo.getId();
        ContactTeamListVo contactTeamListVo = new ContactTeamListVo();
        contactTeamListVo.setUserId(userVoId);
        ResponseEntity<List<ContactTeamVo>> resultVo = contactsTeamClient.findList(contactTeamListVo);
        List<ContactTeamVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        ContactTeamVo contactTeamVo = new ContactTeamVo();
        contactTeamVo.setId("1");
        contactTeamVo.setParentId("1");
        contactTeamVo.setTeamName("我的分组");
        contactTeamVo.setLeaf("false");
        contactTeamVo.setOrders(1);
        vo.add(contactTeamVo);
        if(vo.size()!=1){
            List<ContactTeamVo> root = AssemblyTreeUtils.assemblyTree(vo);
            return root;
        }else {
            return vo;
        }
    }

    /**
     * 获取通讯录list(根据组ID 查询对于任意信息，带分页)
     */
    public RestPageImpl<ContactVo> findContactsList(ContactTeamsPageVo contactTeamsPageVo){
        Assert.notNull(contactTeamsPageVo,"params 不能为空");
        ResponseEntity<RestPageImpl<ContactVo>> resultVo = contactsTeamClient.findContactsList(contactTeamsPageVo);
        RestPageImpl<ContactVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }
}
