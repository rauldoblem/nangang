package com.taiji.emp.duty.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.feign.CalenSettingClient;
import com.taiji.emp.duty.feign.SchedulingClient;
import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.util.SchedulingUtil;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Service
public class CalenSettingService extends BaseService {

    @Autowired
    private CalenSettingClient calenSettingClient;
    @Autowired
    private SchedulingClient schedulingClient;

    /**
     * 添加日历设置信息
     *
     * @param vo
     */
    public void create(CalenSettingListVo vo,OAuth2Authentication principal) {
        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgName = userMap.get("orgName").toString(); //创建单位名称
        vo.setOrgName(orgName);
        calenSettingClient.create(vo);
    }

    /**
     * 根据条件查询日历设置列表
     *
     * @param calenSettingVo
     * @return
     */
    public CalenSettingListVo findList(CalenSettingVo calenSettingVo, OAuth2Authentication principal) {

        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgid = userMap.get("orgId").toString(); //创建单位id
        String orgName = userMap.get("orgName").toString(); //创建单位名称
        String orgID = calenSettingVo.getOrgId();
        if (StringUtils.isBlank(orgID)){
            calenSettingVo.setOrgId(orgid);
        }
        CalenSettingListVo calenSettingListVo = new CalenSettingListVo();
        int day = SchedulingUtil.getMonthNumber(calenSettingVo.getMonth());
        calenSettingVo.setDay(String.valueOf(day));
        //先判断是否有日历设置
        ResponseEntity<List<CalenSettingVo>> list = calenSettingClient.findAll(calenSettingVo);
        List<CalenSettingVo> voList  = ResponseEntityUtils.achieveResponseEntityBody(list);
        SchedulingVo schedulingVo = new SchedulingVo();
        if (!CollectionUtils.isEmpty(voList)){
            //查询是否已排班
            String orgId = calenSettingVo.getOrgId();
            String month = calenSettingVo.getMonth();
           // month = month + "-01";
            schedulingVo.setOrgId(orgId);
            schedulingVo.setMonth(month);
            ResponseEntity<List<SchedulingVo>> vo = schedulingClient.findPersonsList(schedulingVo);
            List<SchedulingVo> vos = ResponseEntityUtils.achieveResponseEntityBody(vo);
            if (!CollectionUtils.isEmpty(vos)){
                calenSettingListVo.setSchedulingFlag(SchedulingGlobal.SCHEDULING_FLAG_YES);//已排班
            }else{
                calenSettingListVo.setSchedulingFlag(SchedulingGlobal.SCHEDULING_FLAG_NO);//未排班
                //未排班（未排人员）还可以设置日历需要把scheduling表中数据删除
                schedulingClient.deteleSchedulingsList(schedulingVo);
            }
            List<CalenSettingVo> settingVoList = packList(calenSettingVo.getMonth(),calenSettingVo.getOrgId(),voList);
            calenSettingListVo.setCalenSetting(settingVoList);
        }else{
            //获取整月数据入库
            List<CalenSettingVo> vos = new ArrayList<CalenSettingVo>();
            List<CalenSettingVo> data = packMonthList(day,calenSettingVo.getMonth(),calenSettingVo.getOrgId(),orgName);
            CalenSettingListVo csList = new CalenSettingListVo();
            csList.setCalenSetting(data);
            ResponseEntity<List<CalenSettingVo>> lists = calenSettingClient.createBatch(csList);
            List<CalenSettingVo> calenSettingVos = ResponseEntityUtils.achieveResponseEntityBody(lists);
            //组装默认日历格式补齐数据
            vos = packList(calenSettingVo.getMonth(),calenSettingVo.getOrgId(),calenSettingVos);
            calenSettingListVo.setCalenSetting(vos);
            calenSettingListVo.setSchedulingFlag(SchedulingGlobal.SCHEDULING_FLAG_NO);//未排班
        }
        return calenSettingListVo;
    }

    //组装前端需要数据
    public List<CalenSettingVo> packList(String date,String orgId,List<CalenSettingVo> list) {

        //获取数据
        Calendar calen = SchedulingUtil.dayForCalen(date);
        //当前月份
        int month = calen.get(Calendar.MONTH)+1;
        //当前天数
        int day = SchedulingUtil.getMonthNumber(date);
        //当月第一天是周几
        int week = calen.get(Calendar.DAY_OF_WEEK)-1;

        CalenSettingVo vo = new CalenSettingVo();

        List<CalenSettingVo> calenSettingVo = new ArrayList<CalenSettingVo>();

          if (week == 1) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
                calenSettingVo.add(vo);
                if (!CollectionUtils.isEmpty(list)){
                    for (CalenSettingVo calenSettingVos:list) {
                        LocalDate time = calenSettingVos.getSettingDate();
                        String str = DateUtil.getDateStr(time);
                        String days = str.substring(str.length()-2,str.length());
                        calenSettingVos.setDay(days);
                        calenSettingVo.add(calenSettingVos);
                    }
                }
                //补齐后面的空缺
                CalenSettingVo calVo = list.get(list.size()-1);
                LocalDate localDate = calVo.getSettingDate();
                String str = DateUtil.getDateStr(localDate);
                Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
                String weekDay = DateUtil.getWeek(time);
                if (!weekDay.equals("星期六")){
                    day = day-(7-week);
                    int add = day % 7;
                    int addDay = 7-add;
                    for (int i = 1; i <= addDay; i++) {
                        calenSettingVo.add(vo);
                    }
                }

            } else if (week == 2) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
              if (!CollectionUtils.isEmpty(list)){
                  for (CalenSettingVo calenSettingVos:list) {
                      LocalDate time = calenSettingVos.getSettingDate();
                      String str = DateUtil.getDateStr(time);
                      String days = str.substring(str.length()-2,str.length());
                      calenSettingVos.setDay(days);
                      calenSettingVo.add(calenSettingVos);
                  }
              }
              //补齐后面的空缺
              CalenSettingVo calVo = list.get(list.size()-1);
              LocalDate localDate = calVo.getSettingDate();
              String str = DateUtil.getDateStr(localDate);
              Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
              String weekDay = DateUtil.getWeek(time);
              if (!weekDay.equals("星期六")){
                  day = day-(7-week);
                  int add = day % 7;
                  int addDay = 7-add;
                  for (int i = 1; i <= addDay; i++) {
                      calenSettingVo.add(vo);
                  }
              }
            } else if (week == 3) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
              if (!CollectionUtils.isEmpty(list)){
                  for (CalenSettingVo calenSettingVos:list) {
                      LocalDate time = calenSettingVos.getSettingDate();
                      String str = DateUtil.getDateStr(time);
                      String days = str.substring(str.length()-2,str.length());
                      calenSettingVos.setDay(days);
                      calenSettingVo.add(calenSettingVos);
                  }
              }
              //补齐后面的空缺
              CalenSettingVo calVo = list.get(list.size()-1);
              LocalDate localDate = calVo.getSettingDate();
              String str = DateUtil.getDateStr(localDate);
              Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
              String weekDay = DateUtil.getWeek(time);
              if (!weekDay.equals("星期六")){
                  day = day-(7-week);
                  int add = day % 7;
                  int addDay = 7-add;
                  for (int i = 1; i <= addDay; i++) {
                      calenSettingVo.add(vo);
                  }
              }
            } else if (week == 4) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
              if (!CollectionUtils.isEmpty(list)){
                  for (CalenSettingVo calenSettingVos:list) {
                      LocalDate time = calenSettingVos.getSettingDate();
                      String str = DateUtil.getDateStr(time);
                      String days = str.substring(str.length()-2,str.length());
                      calenSettingVos.setDay(days);
                      calenSettingVo.add(calenSettingVos);
                  }
              }
              //补齐后面的空缺
              CalenSettingVo calVo = list.get(list.size()-1);
              LocalDate localDate = calVo.getSettingDate();
              String str = DateUtil.getDateStr(localDate);
              Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
              String weekDay = DateUtil.getWeek(time);
              if (!weekDay.equals("星期六")){
                  day = day-(7-week);
                  int add = day % 7;
                  int addDay = 7-add;
                  for (int i = 1; i <= addDay; i++) {
                      calenSettingVo.add(vo);
                  }
              }
            } else if (week == 5) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
              if (!CollectionUtils.isEmpty(list)){
                  for (CalenSettingVo calenSettingVos:list) {
                      LocalDate time = calenSettingVos.getSettingDate();
                      String str = DateUtil.getDateStr(time);
                      String days = str.substring(str.length()-2,str.length());
                      calenSettingVos.setDay(days);
                      calenSettingVo.add(calenSettingVos);
                  }
              }
              //补齐后面的空缺
              CalenSettingVo calVo = list.get(list.size()-1);
              LocalDate localDate = calVo.getSettingDate();
              String str = DateUtil.getDateStr(localDate);
              Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
              String weekDay = DateUtil.getWeek(time);
              if (!weekDay.equals("星期六")){
                  day = day-(7-week);
                  int add = day % 7;
                  int addDay = 7-add;
                  for (int i = 1; i <= addDay; i++) {
                      calenSettingVo.add(vo);
                  }
              }
            } else if (week == 6) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
                calenSettingVo.add(vo);
              if (!CollectionUtils.isEmpty(list)){
                  for (CalenSettingVo calenSettingVos:list) {
                      LocalDate time = calenSettingVos.getSettingDate();
                      String str = DateUtil.getDateStr(time);
                      String days = str.substring(str.length()-2,str.length());
                      calenSettingVos.setDay(days);
                      calenSettingVo.add(calenSettingVos);
                  }
              }
              //补齐后面的空缺
              CalenSettingVo calVo = list.get(list.size()-1);
              LocalDate localDate = calVo.getSettingDate();
              String str = DateUtil.getDateStr(localDate);
              Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
              String weekDay = DateUtil.getWeek(time);
              if (!weekDay.equals("星期六")){
                  day = day-(7-week);
                  int add = day % 7;
                  int addDay = 7-add;
                  for (int i = 1; i <= addDay; i++) {
                      calenSettingVo.add(vo);
                  }
              }
            } else if (week == 0) {
                //日，，一，，二，，三，，四，，五，，六。。
                //补齐前面的空缺
              if (!CollectionUtils.isEmpty(list)){
                  for (CalenSettingVo calenSettingVos:list) {
                      LocalDate time = calenSettingVos.getSettingDate();
                      String str = DateUtil.getDateStr(time);
                      String days = str.substring(str.length()-2,str.length());
                      calenSettingVos.setDay(days);
                      calenSettingVo.add(calenSettingVos);
                  }
              }
              //补齐后面的空缺
              CalenSettingVo calVo = list.get(list.size()-1);
              LocalDate localDate = calVo.getSettingDate();
              String str = DateUtil.getDateStr(localDate);
              Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
              String weekDay = DateUtil.getWeek(time);
              if (!weekDay.equals("星期六")){
                  day = day-(7-week);
                  int add = day % 7;
                  int addDay = 7-add;
                  for (int i = 1; i <= addDay; i++) {
                      calenSettingVo.add(vo);
                  }
              }
            }
        return calenSettingVo;
    }

    //组装要入库的整月数据
    public List<CalenSettingVo> packMonthList(int day,String date,String orgId,String orgName) {

        List<CalenSettingVo> calenSettingVo = new ArrayList<CalenSettingVo>();
        for (int i = 1; i <= day; i++) {
            CalenSettingVo calenSetting = new CalenSettingVo();
            Boolean flag = SchedulingUtil.weekDayOrWeekEnd(date,i);
            if (flag){
                calenSetting.setDateTypeCode(SchedulingGlobal.DATE_TYPE_CODE_WEEKDAY);
            }else{
                calenSetting.setDateTypeCode(SchedulingGlobal.DATE_TYPE_CODE_WEEKEND);
            }
            LocalDate localDate = SchedulingUtil.stringToLocalDate(date,i);
            calenSetting.setSettingDate(localDate);
            calenSetting.setDay(String.valueOf(i));
            calenSetting.setOrgId(orgId);
            calenSetting.setOrgName(orgName);
            calenSettingVo.add(calenSetting);
        }
        return calenSettingVo;
    }
}
