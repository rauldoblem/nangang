package com.taiji.emp.nangang.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.searchVo.NoticeReceiveVo;
import com.taiji.emp.base.vo.DocAttVo;
import com.taiji.emp.base.vo.NoticeReceiveOrgResultVo;
import com.taiji.emp.base.vo.NoticeReceiveOrgVo;
import com.taiji.emp.base.vo.NoticeVo;
import com.taiji.emp.duty.searchVo.SchedulingSearchVo;
import com.taiji.emp.duty.searchVo.SchedulingsListVo;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.emp.duty.vo.SchedulingTimeVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.emp.nangang.common.constant.SchedulingGlobal;
import com.taiji.emp.nangang.feign.*;
import com.taiji.emp.nangang.util.SchedulingUtil;
import com.taiji.micro.common.entity.utils.RestPageImpl;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HomePageService extends BaseService{

    @Autowired
    ShedulingClient schedulingClient;
    @Autowired
    ShiftPatternClient shiftPatternClient;
    @Autowired
    NoticeReceiveOrgClient noticeReceiveOrgClient;
    @Autowired
    NoticeClient noticeClient;
    @Autowired
    DocAttClient docAttClient;
    @Autowired
    EventClient eventClient;

    /**
     * 根据时间获取对应的值班人员列表
     * @param searchDate
     * @return
     */
    public SchedulingSearchVo findDutysByDate(String searchDate, OAuth2Authentication principal) {
        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        schedulingVo.setDutyDate(searchDate);
        //查询当天的值班人员列表
        ResponseEntity<List<SchedulingVo>> schedulingVos = schedulingClient.findList(schedulingVo);
        List<SchedulingVo> schedulings = ResponseEntityUtils.achieveResponseEntityBody(schedulingVos);
        //拼装前台数据
        SchedulingSearchVo ssVo = new SchedulingSearchVo();
        if (!CollectionUtils.isEmpty(schedulings)){
            //按需拼接前台数据
            SchedulingVo sd = schedulings.get(0);
            ssVo.setDutyDate(sd.getDutyDate());
            ssVo.setDateTypeCode(sd.getDateTypeCode());
            String name = SchedulingUtil.getName(sd.getDateTypeCode());
            ssVo.setDateTypeName(name);
            ssVo.setHolidayName(sd.getHolidayName());
            String days = sd.getDutyDate();
            days = days.substring(days.length() - 2, days.length());
            ssVo.setDay(days);
            Date d = DateUtil.stringToDate(searchDate, "yyyy-MM-dd");
            String weekDay = DateUtil.getWeek(d);
            if (org.apache.commons.lang.StringUtils.isBlank(weekDay)){
                weekDay = ("星期日");
            }
            ssVo.setWeekDay(weekDay);

            List<SchedulingVo> schedulingsForDay = new ArrayList<SchedulingVo>();
            List<SchedulingTimeVo> schedulingTimeVo = new ArrayList<SchedulingTimeVo>();

            //按天值班list
            List<SchedulingVo> forDay = new ArrayList<SchedulingVo>();
            //按班次值班list
            List<SchedulingVo> forTime = new ArrayList<SchedulingVo>();

            //循环遍历每一天的值班信息
            if (!CollectionUtils.isEmpty(schedulings)){
                for (SchedulingVo sdVo:schedulings) {
                    //判断是按天还是按班次值班1--天，0--班次
                    String pTypeCode = sdVo.getPtypeCode();
                    if(org.apache.commons.lang.StringUtils.isNotEmpty(pTypeCode)){
                        if(pTypeCode.equals(SchedulingGlobal.P_TYPE_CODE_DAY)){
                            forDay.add(sdVo);
                        }else{
                            forTime.add(sdVo);
                        }
                    }
                }
            }

            //按天值班
            //去重按天值班信息分组个数
            List<String> number = new ArrayList<String>();
            //未排序有问题
            if(!CollectionUtils.isEmpty(forDay)){
                for(int k=0;k<forDay.size();k++){
                    if(k==0){
                        number.add(forDay.get(k).getDutyTeamId());
                    }else{
                        if(!forDay.get(k).getDutyTeamId().equals(forDay.get(k-1).getDutyTeamId())){
                            number.add(forDay.get(k).getDutyTeamId());
                        }else{
                            continue;
                        }
                    }
                }
            }

            //根据去重后的分组个数创建分组list
            Map<String,List<SchedulingVo>> map = new HashMap<String,List<SchedulingVo>>();
            for (String num:number) {
                List<SchedulingVo> list = new ArrayList<SchedulingVo>();
                map.put(num,list);
            }

            //添加相应数据到分组list
            for (SchedulingVo vo:forDay) {
                //获取key值有问题
                Set<String> key = map.keySet();
                if (key.contains(vo.getDutyTeamId())){
                    List<SchedulingVo> team = map.get(vo.getDutyTeamId());
                    team.add(vo);
                }
            }

            //遍历map拼装数据
            for (Map.Entry<String,List<SchedulingVo>> entry :map.entrySet()) {
                List<SchedulingVo> schedulingDay = entry.getValue();
                //判断同一个组下的多个人员
                if (!CollectionUtils.isEmpty(schedulingDay)){
                    if (schedulingDay.size()==1){
                        String personId = schedulingDay.get(0).getPersonId();
                        if (!StringUtils.isBlank(personId)){
                            List<PersonVo> personInfo = new ArrayList<PersonVo>();
                            PersonVo personVo = new PersonVo();
                            personVo.setId(schedulingDay.get(0).getPersonId());
                            personVo.setAddrName(schedulingDay.get(0).getPersonName());
                            personInfo.add(personVo);
                            schedulingDay.get(0).setPersonInfo(personInfo);
                            schedulingsForDay.add(schedulingDay.get(0));
                        }else{
                            List<PersonVo> personInfo = new ArrayList<PersonVo>();
                            schedulingDay.get(0).setPersonInfo(personInfo);
                            schedulingsForDay.add(schedulingDay.get(0));
                        }
                    }else{
                        SchedulingVo sdVos = schedulingDay.get(0);
                        List<PersonVo> personInfo = new ArrayList<PersonVo>();
                        for (SchedulingVo vos:schedulingDay) {
                            String personId = vos.getPersonId();
                            if (!StringUtils.isBlank(personId)){
                                //处理人员信息
                                PersonVo personVo = new PersonVo();
                                personVo.setId(vos.getPersonId());
                                personVo.setAddrName(vos.getPersonName());
                                personInfo.add(personVo);
                            }
                        }
                        sdVos.setPersonInfo(personInfo);
                        schedulingsForDay.add(sdVos);
                    }
                }
            }


            //按班次值班
            //按班次去重
            List<String> countShifts = new ArrayList<String>();
            if (!CollectionUtils.isEmpty(forTime)){
                for (SchedulingVo vos:forTime) {
                    countShifts = forTime.stream().map(temp -> temp.getShiftPatternId()).collect(Collectors.toList());
                }
            }
            Set h = new HashSet(countShifts);
            countShifts.clear();
            countShifts.addAll(h);


            //根据去重后的班次个数创建分组list
            Map<String,List<SchedulingVo>> mapShift = new HashMap<String,List<SchedulingVo>>();
            for (String numberShift:countShifts) {
                List<SchedulingVo> list = new ArrayList<SchedulingVo>();
                mapShift.put(numberShift,list);
            }

            //添加相应数据到分组list
            for (SchedulingVo timeVo:forTime) {
                //获取key值有问题
                Set<String> key = mapShift.keySet();
                if (key.contains(timeVo.getShiftPatternId())){
                    List<SchedulingVo> team = mapShift.get(timeVo.getShiftPatternId());
                    team.add(timeVo);
                }
            }

            //去重后的班次，需要去重组
            //遍历去重后的班次map
            for (Map.Entry<String,List<SchedulingVo>> entry :mapShift.entrySet()) {
                //班次
                List<SchedulingVo> schedulingShift = entry.getValue();
                SchedulingVo sv = new SchedulingVo();
                //拼接前台数据
                if (!CollectionUtils.isEmpty(schedulingShift)){
                    sv = schedulingShift.get(0);
                }
                SchedulingTimeVo schedulingTime = new SchedulingTimeVo();
                schedulingTime.setShiftPatternId(sv.getShiftPatternId());
                schedulingTime.setShiftPatternName(sv.getShiftPatternName());
                //获取班次顺序
                ResponseEntity<ShiftPatternVo> shiftPatternVos = shiftPatternClient.findOne(sv.getShiftPatternId());
                ShiftPatternVo shiftPatternVo = ResponseEntityUtils.achieveResponseEntityBody(shiftPatternVos);
                schedulingTime.setShiftSeq(shiftPatternVo.getShiftSeq());
                //获取开始时间及结束时间
                schedulingTime.setStartTime(sv.getStartTime());
                schedulingTime.setEndTime(sv.getEndTime());
                List<SchedulingVo> schedulingDetail = new ArrayList<SchedulingVo>();
                //同一班次下去重分组
                List<String> countShiftsDuty = new ArrayList<String>();
                if (!CollectionUtils.isEmpty(schedulingShift)) {
                    for (SchedulingVo vos : schedulingShift) {
                        countShiftsDuty = schedulingShift.stream().map(temp -> temp.getDutyTeamId()).collect(Collectors.toList());
                    }
                }
                Set set = new HashSet(countShiftsDuty);
                countShiftsDuty.clear();
                countShiftsDuty.addAll(set);

                //创建同一班次下不同的分组list
                Map<String, List<SchedulingVo>> sdMap = new HashMap<String, List<SchedulingVo>>();
                for (String num : countShiftsDuty) {
                    List<SchedulingVo> list = new ArrayList<SchedulingVo>();
                    sdMap.put(num, list);
                }
                //添加相应数据到分组list
                for (SchedulingVo timeVo : schedulingShift) {
                    //获取key值有问题
                    Set<String> key = sdMap.keySet();
                    if (key.contains(timeVo.getDutyTeamId())) {
                        List<SchedulingVo> team = sdMap.get(timeVo.getDutyTeamId());
                        team.add(timeVo);
                    }
                }

                //遍历map拼装数据
                for (Map.Entry<String, List<SchedulingVo>> sdEntry : sdMap.entrySet()) {
                    List<SchedulingVo> schedulingTimes = sdEntry.getValue();
                    //判断同一个组下的多个人员
                    if (!CollectionUtils.isEmpty(schedulingTimes)) {
                        if (schedulingTimes.size() == 1) {
                            String personId = schedulingTimes.get(0).getPersonId();
                            if (!StringUtils.isBlank(personId)){
                                List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                //处理人员信息
                                PersonVo personVo = new PersonVo();
                                personVo.setId(schedulingTimes.get(0).getPersonId());
                                personVo.setAddrName(schedulingTimes.get(0).getPersonName());
                                personInfo.add(personVo);
                                schedulingTimes.get(0).setPersonInfo(personInfo);
                                schedulingDetail.add(schedulingTimes.get(0));
                            }else{
                                List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                schedulingTimes.get(0).setPersonInfo(personInfo);
                                schedulingDetail.add(schedulingTimes.get(0));
                            }

                        } else {
                            SchedulingVo sdVos = schedulingTimes.get(0);
                            List<PersonVo> personInfo = new ArrayList<PersonVo>();
                            for (SchedulingVo vos : schedulingTimes) {
                                String personId = vos.getPersonId();
                                if (!StringUtils.isBlank(personId)){
                                    //处理人员信息
                                    PersonVo personVo = new PersonVo();
                                    personVo.setId(vos.getPersonId());
                                    personVo.setAddrName(vos.getPersonName());
                                    personInfo.add(personVo);
                                }
                            }
                            sdVos.setPersonInfo(personInfo);
                            schedulingDetail.add(sdVos);
                        }
                    }
                }
                //添加同一班次下不同分组信息
                schedulingTime.setSchedulingDetail(schedulingDetail);
                schedulingTimeVo.add(schedulingTime);
            }

            //排序schedulingTimeVo
            schedulingTimeVo.sort(new Comparator<SchedulingTimeVo>() {
                @Override
                public int compare(SchedulingTimeVo o1, SchedulingTimeVo o2) {
                    try {
                        return Integer.valueOf(o1.getShiftSeq()) - Integer.valueOf(o2.getShiftSeq());
                    } catch (Exception var3) {
                        return 0;
                    }
                }
            });

            ssVo.setSchedulingsForTime(schedulingTimeVo);
            ssVo.setSchedulingsForDay(schedulingsForDay);
        }
        return ssVo;
    }

    /**
     * 按月份获取当前用户的值班信息
     * @param searchMonth
     * @return
     */
    public List<SchedulingsListVo> findMonthSchedulings(String searchMonth, OAuth2Authentication principal) {
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id

        //添加人员查询条件
        return getList(searchMonth, orgId);
    }

    //值班列表数据
    public List<SchedulingsListVo> getList(String date,String orgId) {

        //当前天数
        int day = SchedulingUtil.getMonthNumber(date);
        //当月第一天是周几
        List<SchedulingsListVo> schedulingsListVo = new ArrayList<SchedulingsListVo>();

        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        schedulingVo.setDutyDate(date);
        schedulingVo.setDaysNumber(day);
        //获取整月数据
        ResponseEntity<List<List<SchedulingVo>>> lists = schedulingClient.findCalList(schedulingVo);
        List<List<SchedulingVo>> listAll = ResponseEntityUtils.achieveResponseEntityBody(lists);
        for (int i = 0; i < listAll.size(); i++) {
            SchedulingsListVo ssVo = new SchedulingsListVo();
            List<SchedulingVo> sd = listAll.get(i);
            //添加人员条件判断空值
            if (!CollectionUtils.isEmpty(sd)){
                //按需拼接前台数据
                SchedulingVo scheduling = new SchedulingVo();
                if (!CollectionUtils.isEmpty(sd)){
                    scheduling = sd.get(0);
                }
                ssVo.setDutyDate(scheduling.getDutyDate());
                ssVo.setDateTypeCode(scheduling.getDateTypeCode());
                ssVo.setDateTypeName(scheduling.getDateTypeName());
                ssVo.setHolidayName(scheduling.getHolidayName());
                String days = scheduling.getDutyDate();
                days = days.substring(days.length() - 2, days.length());
                ssVo.setDay(days);
                Date d = DateUtil.stringToDate(scheduling.getDutyDate(), "yyyy-MM-dd");
                String weekDay = DateUtil.getWeek(d);
                if(StringUtils.isBlank(weekDay)){
                    ssVo.setWeekDay("星期日");
                }else{
                    ssVo.setWeekDay(weekDay);
                }
                List<SchedulingVo> Schedulings = new ArrayList<SchedulingVo>();

                //按天值班list
                List<SchedulingVo> forDay = new ArrayList<SchedulingVo>();
                //按班次值班list
                List<SchedulingVo> forTime = new ArrayList<SchedulingVo>();

                //循环遍历每一天的值班信息
                if (!CollectionUtils.isEmpty(sd)){
                    for (SchedulingVo sdVo:sd) {
                        //判断是按天还是按班次值班1--天，0--班次
                        String pTypeCode = sdVo.getPtypeCode();
                        if(org.apache.commons.lang.StringUtils.isNotEmpty(pTypeCode)){
                            if(pTypeCode.equals(SchedulingGlobal.P_TYPE_CODE_DAY)){
                                forDay.add(sdVo);
                            }else{
                                forTime.add(sdVo);
                            }
                        }
                    }
                }

                //按天值班
                //去重按天值班信息分组个数
                List<String> number = new ArrayList<String>();
                //未排序有问题
                if(!CollectionUtils.isEmpty(forDay)){
                    for(int k=0;k<forDay.size();k++){
                        if(k==0){
                            number.add(forDay.get(k).getDutyTeamId());
                        }else{
                            if(!forDay.get(k).getDutyTeamId().equals(forDay.get(k-1).getDutyTeamId())){
                                number.add(forDay.get(k).getDutyTeamId());
                            }else{
                                continue;
                            }
                        }
                    }
                }

                //根据去重后的分组个数创建分组list
                Map<String,List<SchedulingVo>> map = new HashMap<String,List<SchedulingVo>>();
                for (String num:number) {
                    List<SchedulingVo> list = new ArrayList<SchedulingVo>();
                    map.put(num,list);
                }

                //添加相应数据到分组list
                for (SchedulingVo vo:forDay) {
                    //获取key值有问题
                    Set<String> key = map.keySet();
                    if (key.contains(vo.getDutyTeamId())){
                        List<SchedulingVo> team = map.get(vo.getDutyTeamId());
                        team.add(vo);
                    }
                }

                //遍历map拼装数据
                for (Map.Entry<String,List<SchedulingVo>> entry :map.entrySet()) {
                    List<SchedulingVo> schedulingDay = entry.getValue();
                    //判断同一个组下的多个人员
                    if (!CollectionUtils.isEmpty(schedulingDay)){
                        if (schedulingDay.size()==1){
                            String personId = schedulingDay.get(0).getPersonId();
                            if (!StringUtils.isBlank(personId)){
                                List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                //处理人员信息
                                PersonVo personVo = new PersonVo();
                                personVo.setId(schedulingDay.get(0).getPersonId());
                                personVo.setAddrName(schedulingDay.get(0).getPersonName());
                                personInfo.add(personVo);
                                schedulingDay.get(0).setPersonInfo(personInfo);
                                Schedulings.add(schedulingDay.get(0));
                            }else{
                                List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                schedulingDay.get(0).setPersonInfo(personInfo);
                                Schedulings.add(schedulingDay.get(0));
                            }
                        }else{
                            SchedulingVo sdVos = schedulingDay.get(0);
                            List<PersonVo> personInfo = new ArrayList<PersonVo>();
                            for (SchedulingVo vos:schedulingDay) {
                                String personId = vos.getPersonId();
                                if (!StringUtils.isBlank(personId)){
                                    //处理人员信息
                                    PersonVo personVo = new PersonVo();
                                    personVo.setId(vos.getPersonId());
                                    personVo.setAddrName(vos.getPersonName());
                                    personInfo.add(personVo);
                                }
                            }
                            sdVos.setPersonInfo(personInfo);
                            Schedulings.add(sdVos);
                        }
                    }
                }

                //按班次值班
                //按班次去重
                List<String> num = new ArrayList<String>();
                if (!CollectionUtils.isEmpty(forTime)){
                    for (SchedulingVo vos:forTime) {
                        num = forTime.stream().map(temp -> temp.getShiftPatternId()).collect(Collectors.toList());
                    }
                }
                Set set = new HashSet(num);
                num.clear();
                num.addAll(set);

                //根据去重后的班次个数创建分组list
                Map<String,List<SchedulingVo>> mapShift = new HashMap<String,List<SchedulingVo>>();
                for (String numberShift:num) {
                    List<SchedulingVo> list = new ArrayList<SchedulingVo>();
                    mapShift.put(numberShift,list);
                }

                //添加相应数据到分组list
                for (SchedulingVo timeVo:forTime) {
                    //获取key值有问题
                    Set<String> key = mapShift.keySet();
                    if (key.contains(timeVo.getShiftPatternId())){
                        List<SchedulingVo> team = mapShift.get(timeVo.getShiftPatternId());
                        team.add(timeVo);
                    }
                }

                //去重后的班次，需要去重组
                //遍历去重后的班次map
                for (Map.Entry<String,List<SchedulingVo>> entry :mapShift.entrySet()) {
                    //班次
                    List<SchedulingVo> schedulingShift = entry.getValue();
                    SchedulingVo sv = new SchedulingVo();
                    //拼接前台数据
                    if (!CollectionUtils.isEmpty(schedulingShift)){
                        sv = schedulingShift.get(0);
                    }
                    List<SchedulingVo> schedulingDetail = new ArrayList<SchedulingVo>();
                    //同一班次下去重分组
                    List<String> shiftsDuty = new ArrayList<String>();
                    if (!CollectionUtils.isEmpty(schedulingShift)) {
                        for (SchedulingVo vos : schedulingShift) {
                            shiftsDuty = schedulingShift.stream().map(temp -> temp.getDutyTeamId()).collect(Collectors.toList());
                        }
                    }
                    Set s = new HashSet(shiftsDuty);
                    shiftsDuty.clear();
                    shiftsDuty.addAll(s);

                    //创建同一班次下不同的分组list
                    Map<String, List<SchedulingVo>> sdMap = new HashMap<String, List<SchedulingVo>>();
                    for (String numb : shiftsDuty) {
                        List<SchedulingVo> list = new ArrayList<SchedulingVo>();
                        sdMap.put(numb, list);
                    }
                    //添加相应数据到分组list
                    for (SchedulingVo timeVo : schedulingShift) {
                        //获取key值有问题
                        Set<String> key = sdMap.keySet();
                        if (key.contains(timeVo.getDutyTeamId())) {
                            List<SchedulingVo> team = sdMap.get(timeVo.getDutyTeamId());
                            team.add(timeVo);
                        }
                    }

                    //遍历map拼装数据
                    for (Map.Entry<String, List<SchedulingVo>> sdEntry : sdMap.entrySet()) {
                        List<SchedulingVo> schedulingTimes = sdEntry.getValue();
                        //判断同一个组下的多个人员
                        if (!CollectionUtils.isEmpty(schedulingTimes)) {
                            if (schedulingTimes.size() == 1) {
                                String personId = schedulingTimes.get(0).getPersonId();
                                if (!StringUtils.isBlank(personId)){
                                    List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                    //处理人员信息
                                    PersonVo personVo = new PersonVo();
                                    personVo.setId(schedulingTimes.get(0).getPersonId());
                                    personVo.setAddrName(schedulingTimes.get(0).getPersonName());
                                    personInfo.add(personVo);
                                    schedulingTimes.get(0).setPersonInfo(personInfo);
                                    Schedulings.add(schedulingTimes.get(0));
                                }else{
                                    List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                    schedulingTimes.get(0).setPersonInfo(personInfo);
                                    Schedulings.add(schedulingTimes.get(0));
                                }
                            } else {
                                SchedulingVo sdVos = schedulingTimes.get(0);
                                List<PersonVo> personInfo = new ArrayList<PersonVo>();
                                for (SchedulingVo vos : schedulingTimes) {
                                    String personId = vos.getPersonId();
                                    if (!StringUtils.isBlank(personId)){
                                        //处理人员信息
                                        PersonVo personVo = new PersonVo();
                                        personVo.setId(vos.getPersonId());
                                        personVo.setAddrName(vos.getPersonName());
                                        personInfo.add(personVo);
                                    }
                                }
                                sdVos.setPersonInfo(personInfo);
                                Schedulings.add(sdVos);
                            }
                        }
                    }
                }
                ssVo.setSchedulings(Schedulings);
                schedulingsListVo.add(ssVo);
            }

        }
        return schedulingsListVo;
    }

    /**
     * 接受通知条件查询列表----分页
     * @param noticeReceiveVo
     * @return
     */
    public RestPageImpl<NoticeReceiveOrgVo> findNoticeReceivePage(NoticeReceiveVo noticeReceiveVo,OAuth2Authentication principal) {
        Assert.notNull(noticeReceiveVo,"noticeReceiveVo 不能为null");
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //接收单位id
        noticeReceiveVo.setRevOrgId(orgId);
        ResponseEntity<RestPageImpl<NoticeReceiveOrgVo>> resultVo = noticeReceiveOrgClient.findNoticeReceivePage(noticeReceiveVo);
        RestPageImpl<NoticeReceiveOrgVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询通知公告列表-分页
     * params参数key为title,noticeTypeId,sendStartTime,sendEndTime,sendStatus,orgId(可选)
     * page,size
     * @param params
     * @return
     */
    public RestPageImpl<NoticeVo> findPage(Map<String,Object> params, OAuth2Authentication principal){

        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        params.put("buildOrgId",orgId);
        ResponseEntity<RestPageImpl<NoticeVo>> resultVo = noticeClient.findPage(convertMap2MultiValueMap(params));
        RestPageImpl<NoticeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 接受通知公告查看
     * @param noticeRecId
     * @return
     */
    public NoticeReceiveOrgVo findByNoticeRecId(String noticeRecId) {
        Assert.hasText(noticeRecId,"noticeRecId不能为空字符串");
        NoticeReceiveOrgVo orgResultVo = new NoticeReceiveOrgVo();
        ResponseEntity<NoticeReceiveOrgVo> resultVo = noticeReceiveOrgClient.findByNoticeRecId(noticeRecId);
        NoticeReceiveOrgVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        ResponseEntity<NoticeVo> entity = noticeClient.findOne(vo.getNoticeId());
        NoticeVo noticeVo = ResponseEntityUtils.achieveResponseEntityBody(entity);
        orgResultVo.setId(vo.getId());
        orgResultVo.setNoticeId(vo.getNoticeId());
        orgResultVo.setTitle(vo.getTitle());
        orgResultVo.setNoticeTypeId(vo.getNoticeTypeId());
        orgResultVo.setNoticeTypeName(vo.getNoticeTypeName());
        orgResultVo.setContent(vo.getContent());
        orgResultVo.setBuildOrgId(vo.getBuildOrgId());
        orgResultVo.setBuildOrgName(vo.getBuildOrgName());
        orgResultVo.setSendBy(vo.getSendBy());
        orgResultVo.setReceiveOrgId(vo.getReceiveOrgId());
        orgResultVo.setIsFeedback(vo.getIsFeedback());
        orgResultVo.setSendTime(vo.getSendTime());
        return orgResultVo;
    }

    /**
     * 根据id获取某条通知公告信息
     * @param id
     * @return
     */
    public NoticeVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<NoticeVo> resultVo = noticeClient.findOne(id);
        NoticeVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据entityId获取附件集合
     * @param entityId
     * @return
     */
    public List<DocAttVo> findDocAtts(String entityId){
        Assert.hasText(entityId,"id不能为空字符串");
        ResponseEntity<List<DocAttVo>> list = docAttClient.findList(entityId);
        List<DocAttVo> listVos = ResponseEntityUtils.achieveResponseEntityBody(list);
        return listVos;
    }

    //根据id获取单个事件信息
    public EventVo findEventById(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<EventVo> resultVo = eventClient.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

}
