package com.taiji.emp.duty.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.feign.ShiftPatternClient;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class ShiftPatternService extends BaseService {

    @Autowired
    ShiftPatternClient shiftPatternClient;

    /**
     * 新增班次信息
     * @param vo
     * @param principal
     */
    public void create(ShiftPatternVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String userName = userVoProfile.getName();

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        shiftPatternClient.create(vo);
    }

    /**
     * 根据id删除某条班次信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = shiftPatternClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 修改班次信息
     * @param vo
     * @param principal
     */
    public void update(ShiftPatternVo vo, Principal principal,String id) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //获取用户姓名
        String userName = userProfileVo.getName();
        //更新人
        vo.setUpdateBy(userName);
        shiftPatternClient.update(vo,id);
    }

    /**
     * 根据id查询班次信息
     * @param id
     * @return
     */
    public ShiftPatternVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<ShiftPatternVo> resultVo = shiftPatternClient.findOne(id);
        ShiftPatternVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询值班日志列表
     * @param id
     * @return
     */
    public List<ShiftPatternVo> findList(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<List<ShiftPatternVo>> list = shiftPatternClient.findAll(id);
        List<ShiftPatternVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }


}
