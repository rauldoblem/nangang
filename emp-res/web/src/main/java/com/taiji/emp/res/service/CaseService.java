package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.res.feign.CaseClient;
import com.taiji.emp.res.feign.DocAttClient;
import com.taiji.emp.res.searchVo.caseVo.CasePageVo;
import com.taiji.emp.res.vo.CaseEntityVo;
import com.taiji.emp.res.vo.CaseSaveVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class CaseService extends BaseService {

    private CaseClient caseClient;
    private DocAttClient docAttClient;

    /**
     * 新增案例信息记录
     */
    public void create(CaseSaveVo saveVo, Principal principal){
        CaseEntityVo vo = saveVo.getCaseEntityVo();
        vo.setCreateBy(principal.getName());
        //事件类型
        Assert.notNull(vo.getEventTypeId(),"EventId不能为null");
        vo.setEventTypeName(getItemNameById(vo.getEventTypeId()));
        //事件等级
        Assert.notNull(vo.getEventGradeId(),"EventGradeId不能为null");
        vo.setEventGradeName(getItemNameById(vo.getEventGradeId()));
        //设置部门
        UserVo userVo = getCurrentUser(principal);
        UserProfileVo userProfileVo = userVo.getProfile();
        String orgId = userProfileVo.getOrgId();
        String orgName = userProfileVo.getOrgName();
        vo.setCreateOrgId(orgId);
        vo.setCreateOrgName(orgName);
        //案例来源
//        vo.setSourceFlag("1");
//        vo.setCaseSource("1");
        ResponseEntity<CaseEntityVo> resultVo = caseClient.create(vo);
        fileSave(saveVo,resultVo.getBody().getId());
    }

    /**
     * 更新单条案例信息记录
     */
    public void update(CaseSaveVo saveVo, Principal principal,String id){
        CaseEntityVo vo = saveVo.getCaseEntityVo();
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名
        vo.setUpdateBy(userName);
        //事件类型
        Assert.notNull(vo.getEventTypeId(),"EventTypeId不能为null");
        vo.setEventTypeName(getItemById(vo.getEventTypeId()).getItemName());
        //事件等级
        Assert.notNull(vo.getEventGradeId(),"EventGradeId不能为null");
        vo.setEventGradeName(getItemById(vo.getEventGradeId()).getItemName());
        ResponseEntity<CaseEntityVo> resultVo =  caseClient.update(vo,id);
        fileSave(saveVo,id);
    }

    private void fileSave(CaseSaveVo saveVo,String id) throws RuntimeException{
        List<String> docAttIds = saveVo.getFileIds();
        List<String> docAttDelIds = saveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(id,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);

    }


    /**
     * 根据id获取单条案例信息记录
     */
    public CaseEntityVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<CaseEntityVo> resultVo = caseClient.findOne(id);
        CaseEntityVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条案例信息记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = caseClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取案例信息分页list
     */
    public RestPageImpl<CaseEntityVo> findPage(CasePageVo casePageVo){
        Assert.notNull(casePageVo,"casePageVo 不能为空");
        ResponseEntity<RestPageImpl<CaseEntityVo>> resultVo = caseClient.findPage(casePageVo);
        RestPageImpl<CaseEntityVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取案例信息list(不带分页)
     */
    public List<CaseEntityVo> findList(CasePageVo casePageVo){
        Assert.notNull(casePageVo,"casePageVo 不能为空");
        ResponseEntity<List<CaseEntityVo>> resultVo = caseClient.findList(casePageVo);
        List<CaseEntityVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }


}
