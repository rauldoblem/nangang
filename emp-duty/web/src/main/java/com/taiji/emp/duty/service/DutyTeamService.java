package com.taiji.emp.duty.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.emp.duty.feign.DutyTeamClient;
import com.taiji.emp.duty.vo.DutyTeamVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DutyTeamService extends BaseService {

    @Autowired
    DutyTeamClient dutyTeamClient;

    /**
     * 新增值班人员分组信息
     * @param vo
     * @param principal
     */
    public void create(DutyTeamVo vo, OAuth2Authentication principal) {

        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgName = userMap.get("orgName").toString(); //创建单位名称
        String account = principal.getName();
        vo.setCreateBy(account); //创建人
        vo.setUpdateBy(account); //更新人
        vo.setOrgName(orgName);
        dutyTeamClient.create(vo);
    }

    /**
     * 根据id删除某条值班人员分组信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = dutyTeamClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 修改值班人员分组信息
     * @param vo
     * @param principal
     */
    public void update(DutyTeamVo vo, Principal principal) {

        //获取用户姓名
        String account = principal.getName();
        //更新人
        vo.setUpdateBy(account);
        dutyTeamClient.update(vo,vo.getId());
    }

    /**
     * 根据id查询值班人员分组信息
     * @param id
     * @return
     */
    public DutyTeamVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<DutyTeamVo> resultVo = dutyTeamClient.findOne(id);
        DutyTeamVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询值班人员分组列表
     * @param id
     * @return
     */
    public List<DutyTeamVo> findList(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<List<DutyTeamVo>> list = dutyTeamClient.findList(id);
        List<DutyTeamVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

}
