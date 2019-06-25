package com.taiji.emp.base.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.feign.DocAttClient;
import com.taiji.emp.base.feign.FaxClient;
import com.taiji.emp.base.searchVo.fax.FaxListVo;
import com.taiji.emp.base.searchVo.fax.FaxPageVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.base.vo.FaxSaveVo;
import com.taiji.emp.base.vo.FaxVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class FaxService extends BaseService{

    @Autowired
    private FaxClient faxClient;
    @Autowired
    private DocAttClient docAttClient;

    //新增
    public void create(FaxSaveVo faxSaveVo, Principal principal){
        FaxVo faxVo = faxSaveVo.getFax();
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account  = userProfileVo.getName(); //获取用户姓名

        faxVo.setCreateBy(account); //创建人
        faxVo.setUpdateBy(account); //更新人
        ResponseEntity<FaxVo> resultVo = faxClient.create(faxVo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = faxSaveVo.getFileIds();
        List<String> docAttDelIds = faxSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);


    }

    //修改
    public void update(FaxSaveVo faxSaveVo,String id, Principal principal){
        FaxVo faxVo = faxSaveVo.getFax();
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account  = userProfileVo.getName(); //获取用户姓名
        Assert.hasText(id,"id 不能为空");

        faxVo.setUpdateBy(account); //更新人

        ResponseEntity<FaxVo> resultVo = faxClient.update(faxVo,faxVo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        List<String> docAttIds = faxSaveVo.getFileIds();
        List<String> docAttDelIds = faxSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(faxVo.getId(),docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    /**
     * 根据id获取单条记录
     */
    public FaxVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<FaxVo> resultVo = faxClient.findOne(id);
        FaxVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = faxClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 分页list
     */
    public RestPageImpl<FaxVo> findPage(FaxPageVo faxPageVo){
        Assert.notNull(faxPageVo,"FaxPageVo 不能为空");
        ResponseEntity<RestPageImpl<FaxVo>> resultVo = faxClient.findPage(faxPageVo);
        RestPageImpl<FaxVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取list(不带分页)
     */
    public List<FaxVo> findList(FaxListVo faxListVo){
        Assert.notNull(faxListVo,"FaxListVo 不能为空");
        ResponseEntity<List<FaxVo>> resultVo = faxClient.findList(faxListVo);
        List<FaxVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
