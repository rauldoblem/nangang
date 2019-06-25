package com.taiji.emp.nangang.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.feign.SchedulingClient;
import com.taiji.emp.nangang.feign.SigninClient;
import com.taiji.emp.nangang.feign.UtilsFeignClient;
import com.taiji.emp.nangang.searchVo.signin.SigninListVo;
import com.taiji.emp.nangang.searchVo.signin.SigninPageVo;
import com.taiji.emp.nangang.vo.SigninVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SigninService extends BaseService {
    @Autowired
    private SigninClient signinClient;
    @Autowired
    SchedulingClient schedulingClient;
    @Autowired
    UtilsFeignClient utilsFeignClient;
    //新增
    public String create(SigninVo signinVo, Principal principal){

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = userProfileVo.getName();
        String orgId = userProfileVo.getOrgId();
        //签到、签出出时间
        String signStatus = signinVo.getSignStatus();
        String status = null;
        //签到时间值班开始时间前后各45分钟
        //调用duty服务查询开始时间
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setDutyDate(DateUtil.getDateStr(signinVo.getDutyDate()));
        schedulingVo.setOrgId(orgId);
        schedulingVo.setPersonName(signinVo.getDutyPersonName());
        schedulingVo.setPersonId(signinVo.getDutyPersonId());
        schedulingVo.setShiftPatternName(signinVo.getDutyShiftPattern());
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findList(schedulingVo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        String shiftStartTime = null;
        String shiftEndTime = null;
        if (!CollectionUtils.isEmpty(voList)){
            shiftStartTime = voList.get(0).getStartTime();
        }
        if (!CollectionUtils.isEmpty(voList)){
            shiftEndTime = voList.get(0).getEndTime();
        }
        if ("1".equals(signStatus)){
            //签到时间
            String startTime = null;
            String endTime = null;
            Integer rangeTime = Integer.valueOf(NangangGlobal.SIGN_TIME);
            startTime = DateUtil.addMinutes(shiftStartTime, -rangeTime);
            endTime = DateUtil.addMinutes(shiftStartTime, rangeTime);

            boolean flag = belongCalendar(startTime, endTime);
            if (flag){
                //在签到时间内
                signinVo.setCreateBy(account);
                signinVo.setUpdateBy(account);

                ResponseEntity<SigninVo> resultVo = signinClient.create(signinVo);
                ResponseEntityUtils.achieveResponseEntityBody(resultVo);
                status = "0";
                return status;
            }else {
                status = "1";
                return status;
            }
        }else{
            String startTime = null;
            String endTime = null;
            Integer rangeTime = Integer.valueOf(NangangGlobal.SIGN_TIME);
            startTime = DateUtil.addMinutes(shiftEndTime, -rangeTime);
            endTime = DateUtil.addMinutes(shiftEndTime, rangeTime);

            boolean flag = belongCalendar(startTime, endTime);
            if (flag){
                //在签出时间内
                signinVo.setCreateBy(account);
                signinVo.setUpdateBy(account);

                ResponseEntity<SigninVo> resultVo = signinClient.create(signinVo);
                ResponseEntityUtils.achieveResponseEntityBody(resultVo);
                status = "0";
                return status;
            }else {
                status = "1";
                return status;
            }

        }
    }
    //分页
    public RestPageImpl<SigninVo> findPage(SigninPageVo signinPageVo){
        Assert.notNull(signinPageVo,"signinPageVo 不能为空");
        ResponseEntity<RestPageImpl<SigninVo>> resultVo = signinClient.findPage(signinPageVo);
        RestPageImpl<SigninVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    //不分页
    public List<SigninVo> findList(SigninListVo signinListVo){
        Assert.notNull(signinListVo,"signinListVo 不能为空");
        ResponseEntity<List<SigninVo>>resultVo = signinClient.findList(signinListVo);
        List<SigninVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取当前班次人员的签入状态
     */
    public String getSigninStatus(Principal principal){
        //获取当前单位
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String orgId = userProfileVo.getOrgId();
        String username = userProfileVo.getName();
        SchedulingVo vo = new SchedulingVo();
        vo.setOrgId(orgId);
        vo.setPtypeCode("0");
        String dutyDate = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
        vo.setDutyDate(dutyDate);
        //获取当前值班信息
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findList(vo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        String p1 =null;
        String p2 =null;
        String p3 =null;
        String p4 =null;
        List<String> personIds= new ArrayList<String>();
        String status = null;
        if (!CollectionUtils.isEmpty(voList)){
            p1 = voList.get(0).getPersonId();
            p2 = voList.get(1).getPersonId();
            personIds.add(p1);
            personIds.add(p2);
            if(voList.size()==4){
                p3 = voList.get(2).getPersonId();
                p4 = voList.get(3).getPersonId();
                personIds.add(p3);
                personIds.add(p4);
            }
        }
        //获取当前班次人员的签入状态
        SigninListVo signinListVo = new SigninListVo();
        signinListVo.setDutyDate(DateUtil.strToLocalDate(dutyDate));
        signinListVo.setDutyPersonIds(personIds);
        signinListVo.setFlag("0");
        signinListVo.setUserName(username);
        ResponseEntity<List<SigninVo>>resultVo = signinClient.findList(signinListVo);
        List<SigninVo> signinVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if(signinVo.size()==2 || signinVo.size()==4){
            //已全部签到
            status = "0";
        }else{
            //未全部签到
            status = "1";
        }
        return status;
    }

    //判断当前时间是否在某个区间内
    public  boolean belongCalendar(String beginDate, String endDate) {

        Date beginTime = DateUtil.stringToDate(beginDate, "yyyy-MM-dd HH:mm:ss");
        Date endTime = DateUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
        //设置当前时间
        Date nowTime = getCurrentTime();
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //设置开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        //处于开始时间之后，和结束时间之前的判断
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public  Date getCurrentTime(){
        LocalDateTime currentDateTime = utilsFeignClient.now().getBody();
        String dateTimeStr = DateUtil.getDateTimeStr(currentDateTime);
        Date date = DateUtil.stringToDate(dateTimeStr, "yyyy-MM-dd HH:mm:ss");
        return date;
    }
}
