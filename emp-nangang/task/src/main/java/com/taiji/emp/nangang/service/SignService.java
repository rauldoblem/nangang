package com.taiji.emp.nangang.service;

import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.nangang.common.constant.SignGlobal;
import com.taiji.emp.nangang.feign.SchedulingTaskClient;
import com.taiji.emp.nangang.feign.SignTaskClient;
import com.taiji.emp.nangang.vo.SigninVo;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SignService extends BaseService  {

    @Autowired
    SignTaskClient signinClient;

    @Autowired
    SchedulingTaskClient schedulingTaskClient;

    /**
     * 上午7:30 白班 8:00-17:30
     * @param
     */
    public void signInDay() {
        String account = SignGlobal.USER_NAME;//用户
        String orgId = SignGlobal.ORG_ID;//应急指挥中心ID
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        //获取值班人员信息
        List<SchedulingVo> voList = commonMethodDay(schedulingVo);
        if (!CollectionUtils.isEmpty(voList)){
            SchedulingVo entityVo = voList.get(0);
            String shiftPatternName = entityVo.getShiftPatternName();
            if (SignGlobal.SHIFT_PATTERN_NAME_DAY.equals(shiftPatternName)){
                //入库
                insert(account,voList);
            }
        }
    }

    /**
     * 下午16:30  夜班 17:30-8:00
     */
    public void signInNight() {
        String account = SignGlobal.USER_NAME;//用户
        String orgId = SignGlobal.ORG_ID;//应急指挥中心ID
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        //获取值班人员信息
        List<SchedulingVo> voList = commonMethodNight(schedulingVo);
        if (!CollectionUtils.isEmpty(voList)){
            SchedulingVo entityVo = voList.get(0);
            String shiftPatternName = entityVo.getShiftPatternName();
            if (SignGlobal.SHIFT_PATTERN_NAME_NIGHT.equals(shiftPatternName)){
                //入库
                insert(account,voList);
            }
        }
    }

    private List<SchedulingVo> commonMethodDay(SchedulingVo schedulingVo){
        LocalDateTime currentTime = getDayTime();
        schedulingVo.setStartTime(DateUtil.getDateTimeStr(currentTime));
        schedulingVo.setPtypeCode(SignGlobal.P_TYPE_CODE_TIMES);//班次
        List<SchedulingVo> voList = getSchedulingVoList(schedulingVo);
        return voList;
    }

    private List<SchedulingVo> commonMethodNight(SchedulingVo schedulingVo){
        LocalDateTime currentTime = getNightTime();
        schedulingVo.setStartTime(DateUtil.getDateTimeStr(currentTime));
        schedulingVo.setPtypeCode(SignGlobal.P_TYPE_CODE_TIMES);//班次
        List<SchedulingVo> voList = getSchedulingVoList(schedulingVo);
        return voList;
    }

    private List<SchedulingVo> getSchedulingVoList(SchedulingVo schedulingVo){
        ResponseEntity<List<SchedulingVo>> resultVo = schedulingTaskClient.findNextTimesList(schedulingVo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return voList;
    }

    private void insert(String account,List<SchedulingVo> voList){
        for (SchedulingVo vo : voList){
            SigninVo signinVo = new SigninVo();
            signinVo.setDutyDate(DateUtil.strToLocalDate(vo.getDutyDate()));
            signinVo.setDutyShiftPattern(vo.getShiftPatternName());
            signinVo.setDutyPersonId(vo.getPersonId());
            signinVo.setDutyPersonName(vo.getPersonName());
            signinVo.setSignStatus(SignGlobal.SIGN_STATUS_ONE);
            signinVo.setCreateBy(account);
            signinVo.setUpdateBy(account);
            signinClient.create(signinVo);
        }
    }

    /**
     * 上午7:30  8:00
     * @return
     */
    private LocalDateTime getDayTime(){
        LocalDateTime currentTime = getCurrentTime();
        currentTime = currentTime.plusHours(1).minusMinutes(currentTime.getMinute()).minusSeconds(currentTime.getSecond());
        return currentTime;
    }

    /**
     * 下午16:30 17:30
     * @return
     */
    private LocalDateTime getNightTime(){
        LocalDateTime currentTime = getCurrentTime();
        currentTime = currentTime.plusHours(1).minusSeconds(currentTime.getSecond());
        return currentTime;
    }

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    private LocalDateTime getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime;
    }
}
