package com.taiji.emp.duty.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.feign.PatternSettingClient;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PatternSettingService extends BaseService {

    @Autowired
    private PatternSettingClient patternSettingClient;


    /**
     * 根据条件查询值班模式设置列表
     * @param id
     * @return
     */
    public List<PatternSettingVo> findList(String id, OAuth2Authentication principal) {
        Assert.hasText(id,"id不能为空字符串");
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgName = userMap.get("orgName").toString(); //创建单位名称
        ResponseEntity<List<PatternSettingVo>> list = patternSettingClient.findAll(id);
        List<PatternSettingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        if (CollectionUtils.isEmpty(voList)){
            List<PatternSettingVo> voLists = new ArrayList<PatternSettingVo>();
            voLists = packList(id,orgName);
            //调用批量保存方法
            ResponseEntity<List<PatternSettingVo>> lists = patternSettingClient.createBatch(voLists);
            voList = ResponseEntityUtils.achieveResponseEntityBody(lists);
        }
        //组装前台数据
        return packDataList(voList);
    }

    //组装数据入库
    public List<PatternSettingVo> packList(String orgId,String orgName){
       //返回五个对象信息
        List<PatternSettingVo> patternSettingVos = new ArrayList<PatternSettingVo>();
        for (int i = 1; i <= 5; i++) {
            PatternSettingVo patternSettingVo = new PatternSettingVo();
            patternSettingVo.setOrgId(orgId);
            patternSettingVo.setOrgName(orgName);
            patternSettingVo.setDtypeCode(i);
            if(i== SchedulingGlobal.DATE_TYPE_CODE_WEEKDAY){
                patternSettingVo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_WEEKDAY);
            }else if(i== SchedulingGlobal.DATE_TYPE_CODE_WEEKEND){
                patternSettingVo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_WEEKEND);
            }else if(i== SchedulingGlobal.DATE_TYPE_CODE_LEGAL_HOLIDAY){
                patternSettingVo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_LEGAL_HOLIDAY);
            }else if(i== SchedulingGlobal.DATE_TYPE_CODE_SPECIPAL_HOLIDAY){
                patternSettingVo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_SPECIPAL_HOLIDAY);
            }else if(i== SchedulingGlobal.DATE_TYPE_CODE_OTHER){
                patternSettingVo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_OTHER);
            }
            patternSettingVos.add(patternSettingVo);
        }
        return patternSettingVos;
    }

    //组装前台需要数据
    public List<PatternSettingVo> packDataList(List<PatternSettingVo> list){
        if (!CollectionUtils.isEmpty(list)){
            for (PatternSettingVo vo:list) {
                Integer code = vo.getDtypeCode();
                if(code == SchedulingGlobal.DATE_TYPE_CODE_WEEKDAY){
                    vo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_WEEKDAY);
                }else if(code == SchedulingGlobal.DATE_TYPE_CODE_WEEKEND){
                    vo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_WEEKEND);
                }else if(code == SchedulingGlobal.DATE_TYPE_CODE_LEGAL_HOLIDAY){
                    vo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_LEGAL_HOLIDAY);
                }else if(code == SchedulingGlobal.DATE_TYPE_CODE_SPECIPAL_HOLIDAY){
                    vo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_SPECIPAL_HOLIDAY);
                }else if(code == SchedulingGlobal.DATE_TYPE_CODE_OTHER){
                    vo.setDtypeName(SchedulingGlobal.DATE_TYPE_NAME_OTHER);
                }
            }
        }
        return list;
    }

    /**
     *  根据当前时间获取班次名称，供交接班使用
     * @return
     */
    public String findCurrentShiftName(String shiftPatternName,String dutyDate,Principal principal) {
//        UserVo userVo = getCurrentUser(principal);
//        Assert.notNull(userVo, "userVo不能为null");
//        UserProfileVo profileVo = userVo.getProfile();
//        String orgId = profileVo.getOrgId();
//
//        ResponseEntity<SchedulingVo>  entity = patternSettingClient.findCurrentShiftName(orgId);
//        SchedulingVo vo = ResponseEntityUtils.achieveResponseEntityBody(entity);


        if (null != dutyDate){
            return dutyDate + shiftPatternName +"交接班";
        }else{
            String dutyDate1 = DateUtil.dateToString(new Date(),"yyyy-MM-dd");
            return dutyDate1 + SchedulingGlobal.SHIFT_PATTERN_NAME +"交接班";
        }
    }


}
