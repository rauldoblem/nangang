package com.taiji.emp.duty.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.feign.PersonTypePatternClient;
import com.taiji.emp.duty.vo.dailylog.PersonTypePatternVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class PersonTypePatternService extends BaseService {

    @Autowired
    PersonTypePatternClient personTypePatternClient;

    /**
     * 新增值班人员设置信息
     * @param vo
     * @param principal
     */
    public void create(PersonTypePatternVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String userName = userVoProfile.getName();

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        personTypePatternClient.create(vo);
    }

    /**
     * 根据id删除某条值班人员设置信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = personTypePatternClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 修改值班人员设置信息
     * @param vo
     * @param principal
     */
    public void update(PersonTypePatternVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //获取用户姓名
        String userName = userProfileVo.getName();
        //更新人
        vo.setUpdateBy(userName);
        personTypePatternClient.update(vo,vo.getId());
    }

    /**
     * 根据id查询值班人员设置信息
     * @param id
     * @return
     */
    public PersonTypePatternVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PersonTypePatternVo> resultVo = personTypePatternClient.findOne(id);
        PersonTypePatternVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询值班人员设置列表
     * @param id
     * @return
     */
    public List<PersonTypePatternVo> findList(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<List<PersonTypePatternVo>> list = personTypePatternClient.findAll(id);
        List<PersonTypePatternVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

   
}
