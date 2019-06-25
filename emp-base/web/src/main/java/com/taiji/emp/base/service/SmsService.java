package com.taiji.emp.base.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.feign.SmsClient;
import com.taiji.emp.base.feign.SmsRecieveClient;
import com.taiji.emp.base.searchVo.sms.SmsListVo;
import com.taiji.emp.base.searchVo.sms.SmsPageVo;
import com.taiji.emp.base.vo.SmsRecieveVo;
import com.taiji.emp.base.vo.SmsVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;
@Service
public class SmsService extends BaseService{
    @Autowired
    private SmsClient smsClient;
    @Autowired
    SmsRecieveClient smsRecieveClient;

    //新增
    public void create(SmsVo smsVo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = principal.getName();

        smsVo.setCreateBy(account);
        smsVo.setUpdateBy(account);
        smsVo.setBuildOrgId(userProfileVo.getOrgId());
        smsVo.setBuildOrgName(userProfileVo.getOrgName());
        smsVo.setSendStatus("0");
        ResponseEntity<SmsVo> resultVo = smsClient.create(smsVo);
        SmsVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        //存入短信接收表信息
        SmsRecieveVo SmsRecieveVo = new SmsRecieveVo();
        SmsRecieveVo.setSmsId(vo.getId());
        SmsRecieveVo.setReceivers(smsVo.getReceivers());
        smsRecieveClient.create(SmsRecieveVo);
    }

    //修改
    public void update(SmsVo smsVo,String id, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account  =  userProfileVo.getName();
        Assert.hasText(id,"id 不能为空");

        smsVo.setBuildOrgId(userProfileVo.getOrgId());
        smsVo.setBuildOrgName(userProfileVo.getOrgName());
        smsVo.setUpdateBy(account);
        smsClient.update(smsVo,id);
        //修改短信接收表信息
        SmsRecieveVo SmsRecieveVo = new SmsRecieveVo();
        SmsRecieveVo.setReceivers(smsVo.getReceivers());
        smsRecieveClient.update(SmsRecieveVo,id);

    }

    //获取单条
    public SmsVo findOne(String id){
        Assert.notNull(id,"id不能为空字符串或null");
        ResponseEntity<SmsVo> resultVo = smsClient.findOne(id);
        SmsVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //获取短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(id);
        ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo);
        List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);
        vo.setReceivers(voList);
        return vo;
    }

    //删除
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串或null");
        ResponseEntity<Void> resultVo = smsClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //删除短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(id);
        smsRecieveClient.deleteLogic(id);
    }

    //分页
    public RestPageImpl<SmsVo> findPage(SmsPageVo smsPageVo){
        Assert.notNull(smsPageVo,"smsPageVo 不能为空");
        ResponseEntity<RestPageImpl<SmsVo>> resultVo = smsClient.findPage(smsPageVo);
        RestPageImpl<SmsVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        for (SmsVo sv:vo) {
            String id = sv.getId();
            //查询短信接收表信息
            SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
            smsRecieveVo.setSmsId(id);
            ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo);
            List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);
            sv.setReceivers(voList);
        }
        return vo;
    }

    //不分页
    public List<SmsVo> findList(SmsListVo smsListVo){
        Assert.notNull(smsListVo,"smsListVo 不能为空");
        ResponseEntity<List<SmsVo>>resultVo = smsClient.findList(smsListVo);
        List<SmsVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }


    //查看短信发送状态
    public List<SmsRecieveVo> findSmsRecieveBySmsId(String id){
        Assert.notNull(id,"id不能为空字符串或null");
        //获取短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(id);
        ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo);
        List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);
        return voList;
    }

}
