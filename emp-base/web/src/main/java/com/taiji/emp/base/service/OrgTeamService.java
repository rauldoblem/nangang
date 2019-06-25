package com.taiji.emp.base.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.feign.OrgTeamClient;
import com.taiji.emp.base.vo.OrgTeamMidSaveVo;
import com.taiji.emp.base.vo.OrgTeamMidVo;
import com.taiji.emp.base.vo.OrgTeamVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 9:51
 */
@Service
public class OrgTeamService extends BaseService{

    @Autowired
    private OrgTeamClient client;

    /**
     * 创建一条组织分组
     * @param orgTeamVo
     * @param principal
     */
    public void create(OrgTeamVo orgTeamVo, Principal principal) {

        //获取principalMap 为了拿到当前用户的ID
        LinkedHashMap<String, Object> principalMap = SecurityUtils.getPrincipalMap((OAuth2Authentication) principal);
        UserVo userVo = getCurrentUser(principal);
        System.out.println(userVo);
        Assert.notNull(userVo,"userVo不能为null");
        String userName = principal.getName();

        //设置创建人、修改人
        orgTeamVo.setCreateBy(userName);
        orgTeamVo.setUpdateBy(userName);
        if(null != principalMap){
            orgTeamVo.setCreateUserId(principalMap.get("id").toString());
        }

        ResponseEntity<OrgTeamVo> responseEntity = client.create(orgTeamVo);
        ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
    }

    /**
     * 修改分组信息
     * @param orgTeamVo
     * @param principal
     */
    public void update(OrgTeamVo orgTeamVo, Principal principal) {
        //设置修改信息
        UserVo userVo = getCurrentUser(principal);
        System.out.println(userVo);
        Assert.notNull(userVo,"userVo不能为null");
        String userName = principal.getName();
        //设置修改人 逻辑上修改人不用重新设置，因为没人只能修改自己的分组
        orgTeamVo.setUpdateBy(userName);

        ResponseEntity<OrgTeamVo> responseEntity = client.update(orgTeamVo);
        ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
    }

    /**
     * 根据id获取一条组织分组
     * @param id
     * @return
     */
    public OrgTeamVo findOne(String id) {
        ResponseEntity<OrgTeamVo> responseEntity = client.findOne(id);
        OrgTeamVo orgTeamVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
        return orgTeamVo;
    }

    /**
     * 根据id删除分组信息，并删除该记录的id关联的的中间表信息
     * @param id
     */
    public void deleteOne(String id) {
        ResponseEntity responseEntity = client.deleteOne(id);
        ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
    }

    /**
     * 获取当前用户建立的所有分组
     * @param principal
     * @return
     */
    public List<OrgTeamVo> searchAll(Principal principal) {
        LinkedHashMap<String, Object> principalMap = SecurityUtils.getPrincipalMap((OAuth2Authentication) principal);
        String createUserId = null;
        if(null != principalMap){
            createUserId = principalMap.get("id").toString();
        }

        ResponseEntity<List<OrgTeamVo>> responseEntity = client.findAll(createUserId);
        return ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
    }

    /**
     * 设置分组内组织机构，后台每次均将前一次数据删除，再增加新关联
     * @param orgTeamMidSaveVo
     */
    public void createOrgTeamMids(OrgTeamMidSaveVo orgTeamMidSaveVo) {
        String teamId = orgTeamMidSaveVo.getTeamId();
        List<String> orgIds = orgTeamMidSaveVo.getOrgIds();

        if(!StringUtils.isEmpty(teamId)){
            //先根据teamId删掉该分组内的mid条,传空组织机构id时，提供删除
            ResponseEntity responseEntity = client.deleteOrgTeamMidsByTeamId(teamId);
            ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
            if(!CollectionUtils.isEmpty(orgIds)){
                //根据teamId查询teamName
                ResponseEntity<OrgTeamVo> one = client.findOne(teamId);
                OrgTeamVo orgTeamVo = ResponseEntityUtils.achieveResponseEntityBody(one);
                if(null != orgTeamVo){
                    String teamName = orgTeamVo.getTeamName();
                    //把orgTeamMidSaveVo拆解成orgTeamMidVo
                    List<OrgTeamMidVo> orgTeamMidVos = new ArrayList(orgIds.size());
                    OrgTeamMidVo orgTeamMidVo;
                    for (String orgId : orgIds) {
                        orgTeamMidVo = new OrgTeamMidVo();
                        orgTeamMidVo.setTeamId(teamId);
                        //根据teamId查询TeamName放进去
                        orgTeamMidVo.setTeamName(teamName);
                        orgTeamMidVo.setOrgId(orgId);
                        //根据orgId查询name放进去
                        OrgVo orgVo = getOrgNameById(orgId);
                        if(null != orgVo){
                            orgTeamMidVo.setOrgName(orgVo.getOrgName());
                        }
                        orgTeamMidVos.add(orgTeamMidVo);
                    }
                    //把这个list拿过去保存
                    client.createOrgTeamMids(orgTeamMidVos);
                }
            }
        }
    }

    /**
     * 根据分组ID获取分组下的组织机构信息
     * @param id
     * @return
     */
    public List<OrgTeamMidVo> searchOrgsByTeamId(String id) {
        ResponseEntity<List<OrgTeamMidVo>> responseEntity =  client.searchOrgsByTeamId(id);
        List<OrgTeamMidVo> orgTeamMidVos = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
        return orgTeamMidVos;
    }
}
