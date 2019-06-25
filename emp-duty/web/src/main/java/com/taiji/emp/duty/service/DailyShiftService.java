package com.taiji.emp.duty.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.feign.DailyLogShiftClient;
import com.taiji.emp.duty.feign.DailyShiftClient;
import com.taiji.emp.duty.feign.SchedulingClient;
import com.taiji.emp.duty.feign.UtilsFeignClient;
import com.taiji.emp.duty.searchVo.DailyShiftPageVo;
import com.taiji.emp.duty.vo.DailyShiftAndLogVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.SigninVo;
import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import com.taiji.emp.duty.vo.dailyShift.DailyShiftVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class DailyShiftService extends BaseService {

    @Autowired
    private DailyShiftClient dailyShiftClient;
    @Autowired
    DailyLogShiftClient dailyLogShiftClient;
    @Autowired
    SchedulingClient schedulingClient;
    @Autowired
    UtilsFeignClient utilsFeignClient;
    public String create(DailyShiftAndLogVo vo, Principal principal) {

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String orgId = userVoProfile.getOrgId();
        SigninVo signinVo = vo.getSigninVo();
        //交接班时间值班结束时间前后各45分钟
        //调用duty服务查询结束时间
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setDutyDate(DateUtil.getDateStr(signinVo.getDutyDate()));
        schedulingVo.setOrgId(orgId);
        schedulingVo.setPersonName(signinVo.getDutyPersonName());
        schedulingVo.setPersonId(signinVo.getDutyPersonId());
        schedulingVo.setShiftPatternName(signinVo.getDutyShiftPattern());
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findList(schedulingVo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);

        String shiftTime = null;
        if (!CollectionUtils.isEmpty(voList)){
            shiftTime = voList.get(0).getEndTime();
        }

        String startTime = null;

        String endTime = null;
        Integer rangeTime = Integer.valueOf(SchedulingGlobal.SHIFT_TIME);
        startTime = DateUtil.addMinutes(shiftTime, -rangeTime);
        endTime = DateUtil.addMinutes(shiftTime, rangeTime);

        boolean flag = belongCalendar(startTime, endTime);
        String status = null;
        if (flag){
            DailyShiftVo dailyShiftVo = vo.getDailyShift();
            dailyShiftVo.setCreateBy(principal.getName()); //创建人
            dailyShiftVo.setCreateOrgId(userVoProfile.getOrgId()); //所属部门ID
            dailyShiftVo.setCreateOrgName(userVoProfile.getOrgName()); //所属部门名称
            ResponseEntity<DailyShiftVo> result = dailyShiftClient.create(dailyShiftVo);
            DailyShiftVo dailyShiftVos = ResponseEntityUtils.achieveResponseEntityBody(result);
            String dailyShiftVoId = dailyShiftVos.getId();
            //存入值班日志中间表
            List<String> logIds = vo.getLogIds();
            if (!CollectionUtils.isEmpty(logIds)) {
                List<DailyLogShiftVo> dailyLogShiftVoList = new ArrayList<DailyLogShiftVo>();
                for (String logId : logIds) {
                    DailyLogShiftVo dailyLogShiftVo = new DailyLogShiftVo();
                    dailyLogShiftVo.setDailyLogId(logId);
                    dailyLogShiftVo.setDailyShiftId(dailyShiftVoId);
                    dailyLogShiftVoList.add(dailyLogShiftVo);
                }
                //调用保存方法
                dailyLogShiftClient.create(dailyLogShiftVoList);
            }
            status = "0";
            return status;
        }else{
            status = "1";
            return status;
        }
    }

    public DailyShiftVo findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        ResponseEntity<DailyShiftVo> result = dailyShiftClient.findOne(id);
        DailyShiftVo vo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vo;
    }


    //提供给controller使用的 分页list查询方法
    public RestPageImpl<DailyShiftVo> findPage(DailyShiftPageVo dailyShiftPageVo){
        Assert.notNull(dailyShiftPageVo,"params 不能为空");
        ResponseEntity<RestPageImpl<DailyShiftVo>> resultVo = dailyShiftClient.findPage(dailyShiftPageVo);
        RestPageImpl<DailyShiftVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
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
