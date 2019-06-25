package com.taiji.emp.base.service;

import com.taiji.base.sys.vo.OrgVo;
import com.taiji.emp.base.common.constant.ContactsGlobal;
import com.taiji.emp.base.feign.ContactsClient;
import com.taiji.emp.base.feign.ContactsTeamClient;
import com.taiji.emp.base.feign.OrgClient;
import com.taiji.emp.base.searchVo.contacts.ContactPageVo;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.emp.base.vo.ContactMidSaveVo;
import com.taiji.emp.base.vo.ContactMidVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactsService extends BaseService {

    private ContactsClient contactsClient;
    private ContactsTeamClient contactsTeamClient;
    private OrgClient orgClient;

    /**
     * 新增通讯录
     */
    public void create(ContactVo vo, Principal principal){
        String userName  = principal.getName(); //获取用户姓名
        vo.setCreateBy(userName); //创建人

        //部门信息
        String orgId = vo.getOrgId();
        Assert.notNull(orgId,"orgId不能为null");
        OrgVo orgVo = orgClient.find(orgId).getBody();
        Assert.notNull(orgVo,"orgVo不能为null");
        String orgName = orgVo.getOrgName();
        vo.setOrgName(orgName);
        //职务待定
        contactsClient.create(vo);
    }

    /**
     * 更新单条通讯录
     */
    public void update(ContactVo vo, Principal principal){
        String userName  = principal.getName(); //获取用户姓名
        vo.setUpdateBy(userName); //更新人

        //部门信息
        String orgId = vo.getOrgId();
        //Assert.notNull(orgId,"orgId不能为null");
        OrgVo orgVo = orgClient.find(orgId).getBody();
        String orgName = orgVo.getOrgName();
        Assert.notNull(orgName,"orgName不能为null");
        vo.setOrgName(orgName);

        contactsClient.update(vo,vo.getId());
    }



    /**
     * 根据id获取单条通讯录记录
     */
    public ContactVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<ContactVo> resultVo = contactsClient.findOne(id);
        ContactVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条通讯录记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = contactsClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取通讯录分页list
     */
    public RestPageImpl<ContactVo> findPage(ContactTeamsPageVo contactTeamsPageVo){
        Assert.notNull(contactTeamsPageVo,"contactPageVo 不能为空");
        String teamId = contactTeamsPageVo.getTeamId();
        ResponseEntity<RestPageImpl<ContactVo>> resultVo;
        if(StringUtils.isEmpty(teamId)){
            String orgId = contactTeamsPageVo.getOrgId();
            Map<String,Object> params = new HashMap<>();
            params.put("parentId",orgId);
            List<OrgVo> emps = orgClient.findList(super.convertMap2MultiValueMap(params)).getBody();
            List<String> collect1 = emps.stream().map(emp ->emp.getId() ).collect(Collectors.toList());
            ContactPageVo contactPageVo = new ContactPageVo();
            contactPageVo.setMobileFlag(contactTeamsPageVo.getTelephone());
            contactPageVo.setPage(contactTeamsPageVo.getPage());
            contactPageVo.setSize(contactTeamsPageVo.getSize());
            contactPageVo.setMobileFlag(contactTeamsPageVo.getMobileFlag());
            contactPageVo.setName(contactTeamsPageVo.getName());
            contactPageVo.setTelephone(contactTeamsPageVo.getTelephone());
            contactPageVo.setOrgId(contactTeamsPageVo.getOrgId());
            contactPageVo.setOrgIds(collect1);
            resultVo = contactsClient.findPage(contactPageVo);
        }else {
            if(ContactsGlobal.CONTACT_YES.equals(teamId)){
                contactTeamsPageVo.setTeamId("");
            }
            resultVo = contactsTeamClient.findContactsList(contactTeamsPageVo);
        }
        RestPageImpl<ContactVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取通讯录list(不带分页)
     */
    public List<ContactVo> findList(ContactPageVo contactPageVo){
        Assert.notNull(contactPageVo,"contactPageVo 不能为空");
        ResponseEntity<List<ContactVo>> resultVo = contactsClient.findList(contactPageVo);
        List<ContactVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }
    /**
     * 将通讯录信息添加到分组中
     */
    public void addContactToTeam(ContactMidSaveVo contactMidSaveVo){
        Assert.notNull(contactMidSaveVo,"contactMidSaveVo 不能为空");
        //组名
        Assert.notNull(contactMidSaveVo.getTeamId(),"TeamId 不能为空");
        String teamId = contactMidSaveVo.getTeamId();
        String contactTeamName = contactsTeamClient.findOne(teamId).getBody().getTeamName();
        //通讯录id 组
        List<String> addrIds = contactMidSaveVo.getContactIds();
        Assert.notNull(teamId,"teamId 不能为空");
        Assert.notNull(contactTeamName,"contactTeamName 不能为空");
        Assert.notNull(addrIds,"addrIds 不能为空");
        ContactMidVo contactMidVo = new ContactMidVo();
        contactMidVo.setTeamId(teamId);
        contactMidVo.setTeamName(contactTeamName);
        contactMidVo.setContactIdList(addrIds);
        contactsClient.addContactToTeam(contactMidVo);
    }
    /**
     * 将通讯录信息移除分组中
     */
    public void removeContactToTeam(ContactMidSaveVo contactMidSaveVo){
        Assert.notNull(contactMidSaveVo,"contactMidSaveVo 不能为空");
        Assert.notNull(contactMidSaveVo.getTeamId(),"TeamId 不能为空");
        String teamId = contactMidSaveVo.getTeamId();
        List<String> strings = contactMidSaveVo.getContactIds();
        ContactMidVo contactMidVo = new ContactMidVo();
        contactMidVo.setTeamId(teamId);
        contactMidVo.setContactIdList(strings);
        contactsClient.removeContactToTeam(contactMidVo);
    }
}
