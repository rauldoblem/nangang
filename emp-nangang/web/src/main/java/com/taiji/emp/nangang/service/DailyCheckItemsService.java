package com.taiji.emp.nangang.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.nangang.feign.DailyCheckItemsClient;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class DailyCheckItemsService extends BaseService{

    @Autowired
    DailyCheckItemsClient client;
    /**
     * 更新值班检查项，根据dailyCheckItemsVo更新成功返回
     */
    public void update(DailyCheckItemsVo vo, Principal principal){

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = userProfileVo.getName();

        vo.setCreateBy(account);
        vo.setUpdateBy(account);

        boolean notBlank = StringUtils.isNotBlank(vo.getId());
        Assert.isTrue(notBlank ,"检查表ID 不能为空格或空字符串" );

        ResponseEntity<DailyCheckItemsVo> resultVo = client.update(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }
    /**
     * 根据主键id查询DailyCheckItemsVo
     */
    public List<DailyCheckItemsVo> findCheckId(String id) {
        Assert.hasText(id,"id 不能为空");
        ResponseEntity<List<DailyCheckItemsVo>> result = client.findCheckId(id);
        List<DailyCheckItemsVo> vo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vo;
    }


}
