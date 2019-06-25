package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.entity.*;
import com.taiji.emp.duty.feign.ISchedulingRestService;
import com.taiji.emp.duty.mapper.DutyTeamMapper;
import com.taiji.emp.duty.mapper.PersonMapper;
import com.taiji.emp.duty.mapper.SchedulingMapper;
import com.taiji.emp.duty.searchVo.DutyMan;
import com.taiji.emp.duty.searchVo.SearchAllDutyVo;
import com.taiji.emp.duty.service.*;
import com.taiji.emp.duty.util.SchedulingUtil;
import com.taiji.emp.duty.vo.SchedulingSearchVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController extends BaseController implements ISchedulingRestService {

    SchedulingService service;
    SchedulingMapper mapper;

    PersonService personService;
    PersonMapper personMapper;

    DutyTeamService dutyTeamService;
    DutyTeamMapper dutyTeamMapper;

    CalenSettingService calenSettingService;
    PatternSettingService patternSettingService;
    ShiftPatternService shiftPatternService;
    PersonTypePatternService personTypePatternService;
    SchedulingService schedulingService;
    SchedulingMapper schedulingMapper;

    /**
     * 自动排班
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<Void> autoScheduling(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        String month = vo.getMonth();
        //获取数据
        Calendar caLen = SchedulingUtil.dayForCalen(month);
        //当月第一天是周几
        int week = caLen.get(Calendar.DAY_OF_WEEK) - 1;
        //获取当月一共多少天
        int monthCount = SchedulingUtil.getMonthNumber(month);
        //当月的第一天
        String firstDay = month + "-" + SchedulingGlobal.SCHEDULING_FIRST_DAY;
        //当月的最后一天
        String lastDay = month + "-" + monthCount;
        //查询条件信息
        SchedulingSearchVo searchVo = new SchedulingSearchVo();
        String orgId = vo.getOrgId();
        searchVo.setOrgId(orgId);
        searchVo.setFirstDay(firstDay);
        searchVo.setLastDay(lastDay);
        //1、先获取当月的天或班次的所有数据
        List<Scheduling> monthList = service.findMonthAllData(searchVo);
        //1.1、先获取当月的天的所有数据
        List<Scheduling> monthDayList = monthList.stream()
                .filter(temp -> temp.getPtypeCode().equals(SchedulingGlobal.SCHEDULING_IS_SHIFT_YES))
                .sorted(Comparator.comparing(temp -> temp.getDutyTeamId().getId()))
                .collect(Collectors.toList());

        //1.2、先获取当月班次的所有数据
        List<Scheduling> monthTimesList = monthList.stream()
                .filter(temp -> temp.getPtypeCode().equals(SchedulingGlobal.SCHEDULING_IS_SHIFT_NO))
                .sorted(Comparator.comparing(temp -> temp.getDutyTeamId().getId()))
                .collect(Collectors.toList());

        //2、获取值班分组信息
        List<DutyTeam> dutyTeamList = dutyTeamService.findAutoList(orgId);
        //2.1、获取值班分组信息(天)
        List<DutyTeam> dayTeamList = dutyTeamList.stream().filter(temp -> temp.getIsShift().equals(SchedulingGlobal.SCHEDULING_IS_SHIFT_NO)).collect(Collectors.toList());
        //2.2、获取值班分组信息(班次)
        List<DutyTeam> timesTeamList = dutyTeamList.stream().filter(temp -> temp.getIsShift().equals(SchedulingGlobal.SCHEDULING_IS_SHIFT_YES)).collect(Collectors.toList());
        //3、获取分组下的人员信息
        //3.1、获取分组下的人员信息(天)
        Map<String, List<Person>> dayPersonInfo = new HashMap<String, List<Person>>();
        for (DutyTeam day : dayTeamList){
            String teamId = day.getId();
            List<Person> dayPersonList = personService.findListByTeamId(teamId);
            dayPersonInfo.put(teamId,dayPersonList);
        }
        //3.2、获取分组下的人员信息(班次)
        Map<String, List<Person>> timesPersonInfo = new HashMap<String, List<Person>>();
        for (DutyTeam times : timesTeamList){
            String teamId = times.getId();
            List<Person> timesPersonList = personService.findListByTeamId(teamId);
            timesPersonInfo.put(teamId,timesPersonList);
        }
        //上个年月
        String lastYearAndMonth = SchedulingUtil.getPreviousYearAndMonth(month);
        //上个年月天数
        int dayCount = SchedulingUtil.getMonthNumber(lastYearAndMonth);
        //上一个年月日
        String previousDutyDate = lastYearAndMonth + "-" + dayCount;
        //4、按班次、获取上个月最后一天的、最后一个班次——>确定按班次的组内的最后一个人
        //4.1、按(天)获取上个月最后一天的——>确定按天的组内的最后一个人
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        schedulingVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
        schedulingVo.setDutyDate(previousDutyDate);
        //开始时间
        LocalDateTime startTime = service.findLastStartTime(schedulingVo);
        schedulingVo.setStartTime(DateUtil.getDateTimeStr(startTime));
        //上个月最后一天某个组的最后一个人
        List<Scheduling> lastDayList = service.findfirstDayInfo(schedulingVo);

        //4.2、按(班次)、获取上个月最后一天的、最后一个班次——>确定按班次的组内的最后一个人
        schedulingVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_TIMES);
        //开始时间
        LocalDateTime startTimes = service.findLastStartTime(schedulingVo);
        schedulingVo.setStartTime(DateUtil.getDateTimeStr(startTimes));
        //上个月最后一天最后一个班次某些组的最后一个人
        List<Scheduling> lastTimesList = service.findfirstDayInfo(schedulingVo);

        //某个组下要插入的人员(天) 含上个月的最后一个人 (准备人员排班)
        Map<String, List<Person>> personInfoForDay = getInsertPersonInfoForDay(lastDayList, dayPersonInfo);

        //某个组下要插入的人员(班次) 不含上个月的最后一个人 (准备人员排班)
        Map<String, List<Person>> personInfoForTimes = getInsertPersonInfoForTimes(lastTimesList, timesPersonInfo,monthTimesList);

        //5、排班
        if (week == SchedulingGlobal.SCHEDULING_Monday){    //当月周一
            //5.1按天排班
//            for (int i = 0;i < monthDayList.size();i++){
//                Scheduling scheduling = monthDayList.get(i);
//                String dutyTeamId = scheduling.getDutyTeamId().getId();
//                for (Map.Entry<String, List<Person>> day : personInfoForDay.entrySet()){
//                    String teamId = day.getKey();
//                    List<Person> personList = day.getValue();
//                    int num = 0;
//                    int arrIndex = 1;//人员数组下标
//                    if (dutyTeamId.equals(teamId)){
//                        num++;
//                        if(num % 7 == 1) {
//                            arrIndex++;
//                        }
//                        Person person = personList.get(arrIndex);
//                        scheduling.setPerson(person);
//                        scheduling.setPersonName(person.getAddrName());
//                    }
//                }
//            }
            int dayNum = SchedulingGlobal.SCHEDULING_SEVEN;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);
        }else if (week == SchedulingGlobal.SCHEDULING_Tuesday){    //当月周二
            //5.1按天排班
            int dayNum = SchedulingGlobal.SCHEDULING_SIX;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);

        }else if (week == SchedulingGlobal.SCHEDULING_Wednesday){    //当月周三
            //5.1按天排班
            int dayNum = SchedulingGlobal.SCHEDULING_FIVE;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);

        }else if (week == SchedulingGlobal.SCHEDULING_Thursday){    //当月周四
            //5.1按天排班
            int dayNum = SchedulingGlobal.SCHEDULING_FOUR;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);

        }else if (week == SchedulingGlobal.SCHEDULING_Friday){    //当月周五
            //5.1按天排班
            int dayNum = SchedulingGlobal.SCHEDULING_THREE;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);

        }else if (week == SchedulingGlobal.SCHEDULING_Saturday){    //当月周六
            //5.1按天排班    num和arrIndex的位置不对
            int dayNum = SchedulingGlobal.SCHEDULING_TWO;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);

        }else if (week == SchedulingGlobal.SCHEDULING_Sunday){    //当月周日
            //5.1按天排班
            int dayNum = SchedulingGlobal.SCHEDULING_ONE;
            getSchedulingByDay(dayNum,monthDayList,personInfoForDay);
        }
        //5.2按班次排班
        int arrIndex = 0;//人员数组下标
        String lastTeamId = null; //记录上一次组的ID
        for (int k = 0;k < monthTimesList.size();k++){
            Scheduling scheduling = monthTimesList.get(k);
            String dutyTeamId = scheduling.getDutyTeamId().getId();
            for (Map.Entry<String, List<Person>> times : personInfoForTimes.entrySet()){
                String teamId = times.getKey();
                List<Person> personList = times.getValue();
                if (dutyTeamId.equals(teamId)){
                    if (!dutyTeamId.equals(lastTeamId)){
                        arrIndex = 0;
                    }
                    int result = arrIndex - personList.size();
                    if (result >= 0){
                        arrIndex = 0;
                    }
                    setDayPersonInfo(arrIndex,personList,scheduling);
                    arrIndex++;
                }
            }
            lastTeamId = dutyTeamId;
        }
        //6、数据更新批量入库
        monthTimesList.addAll(monthDayList);
        service.updateBatch(monthTimesList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 按天排班
     * @param dayNum 上个人还需要值班的次数
     * @param monthDayList
     * @param personInfoForDay
     */
    public void getSchedulingByDay(int dayNum,List<Scheduling> monthDayList,Map<String, List<Person>> personInfoForDay){
        int arrIndex = 0;//人员数组下标
        String lastTeamId = null; //记录上一次组的ID
        int num = 0;
        for (int i = 0;i < monthDayList.size();i++){
            Scheduling scheduling = monthDayList.get(i);
            String dutyTeamId = scheduling.getDutyTeamId().getId();
            for (Map.Entry<String, List<Person>> day : personInfoForDay.entrySet()){
                String teamId = day.getKey();
                List<Person> personList = day.getValue();
                if (dutyTeamId.equals(teamId)) {
                    if (!dutyTeamId.equals(lastTeamId)){
                        arrIndex = 0;
                    }
                    if (i >= dayNum) {
                        num++;
                        if (num % 7 == 1) {
                            arrIndex++;
                        }
                    }
                    //轮询
                    int result = arrIndex - personList.size();
                    if(result == 0){
                        arrIndex = 0;
                    }
                    setDayPersonInfo(arrIndex,personList,scheduling);
                }
            }
            lastTeamId = dutyTeamId;
        }
    }



    /**
     * 按天按排人员排班
     * @param arrIndex
     * @param personList
     * @param scheduling
     */
    public void setDayPersonInfo(int arrIndex,List<Person> personList,Scheduling scheduling){
        Person person = personList.get(arrIndex);
        scheduling.setPerson(person);
        scheduling.setPersonName(person.getAddrName());
    }

    /**
     * 某个组下要插入的人员(天) 含上个月的最后一个人 (准备人员排班)
     * @param lastDayList
     * @param dayPersonInfo
     * @return
     */
    public Map<String,List<Person>> getInsertPersonInfoForDay(List<Scheduling> lastDayList,Map<String, List<Person>> dayPersonInfo){
        //上个月最后一天的分组下的值班人员(天)
        //获取某个组当前值班人
        Map<String, String> dayLastPersonInfo = getTeamInfo(lastDayList);
        //从某个组中获取要插入的人员(天)
        Map<String,List<Person>> mapDayPerson = new HashMap<>();//key:teamId;value:person
        if (!CollectionUtils.isEmpty(dayLastPersonInfo)){
            for (Map.Entry<String,String> entry : dayLastPersonInfo.entrySet()){
                String dutyTeamId = entry.getKey();
                String personId = entry.getValue();
                List<Person> personInfoList = new ArrayList<>(6);//要插入的人员
                if (!CollectionUtils.isEmpty(dayPersonInfo)){
                    for (Map.Entry<String, List<Person>> mapDay : dayPersonInfo.entrySet()){
                        String teamId = mapDay.getKey();
                        if (dutyTeamId.equals(teamId)){
                            List<Person> personList = mapDay.getValue();
                            //某个组下要插入的人员(天) 含上个月的最后一个人
                            List<Person> dayPersons = getDayPersons(personList, personId, personInfoList);
                            mapDayPerson.put(teamId,dayPersons);
                        }
                    }
                }
            }
        }else {
            //上个月没有排班
            for (Map.Entry<String, List<Person>> mapDay : dayPersonInfo.entrySet()){
                String teamId = mapDay.getKey();
                mapDayPerson.put(teamId,mapDay.getValue());
            }
        }
        return mapDayPerson;
    }

    /**
     * 某个组下要插入的人员(班次) 含上个月的最后一个人 (准备人员排班)
     * @param lastTimesList
     * @param timesPersonInfo
     * @return
     */
    public Map<String,List<Person>> getInsertPersonInfoForTimes(List<Scheduling> lastTimesList,Map<String, List<Person>> timesPersonInfo,List<Scheduling> monthTimesList) {
        //上个月最后一天的分组下的值班人员(班次)
        //获取某个组当前值班人
        Map<String, String> timesLastPersonInfo = getTeamInfo(lastTimesList);
        //从某个组中获取要插入的人员(班次)
        Map<String,List<Person>> mapTimesPerson = new HashMap<>();//key:teamId;value:person
        if (!CollectionUtils.isEmpty(timesLastPersonInfo)){
            for (Map.Entry<String,String> entries : timesLastPersonInfo.entrySet()){
                String dutyTeamId = entries.getKey();
                String personId = entries.getValue();
                int count = monthTimesList.stream().filter(temp -> temp.getDutyTeamId().getId().equals(dutyTeamId)).collect(Collectors.toList()).size();
                List<Person> personInfoList = new ArrayList<>(count);//要插入的人员
                if (!CollectionUtils.isEmpty(timesPersonInfo)){
                    for (Map.Entry<String, List<Person>> times : timesPersonInfo.entrySet()){
                        String teamId = times.getKey();
                        if (dutyTeamId.equals(teamId)){
                            List<Person> personList = times.getValue();
                            //某个组下要插入的人员(天) 含上个月的最后一个人
                            List<Person> dayPersons = getTimesPersons(personList, personId, personInfoList,count);
                            mapTimesPerson.put(teamId,dayPersons);
                        }
                    }
                }
            }
        }else {
            //上个月没有排班
            for (Map.Entry<String, List<Person>> times : timesPersonInfo.entrySet()){
                String teamId = times.getKey();
                mapTimesPerson.put(teamId,times.getValue());
            }
        }
        return mapTimesPerson;
    }

    /**
     * 上个月最后一天的分组下的值班人员(天或班次)
     * @param lastList
     * @return
     */
    public Map<String, String> getTeamInfo(List<Scheduling> lastList){
        Map<String, String> lastPersonInfo = new HashMap<>();
        if (!CollectionUtils.isEmpty(lastList)){
            for (Scheduling entity : lastList){
                String dutyTeamId = entity.getDutyTeamId().getId();
                String personId = entity.getPerson().getId();
                lastPersonInfo.put(dutyTeamId,personId);
            }
        }
        return lastPersonInfo;
    }

    /**
     * 安排每个组的人员值班(班次)
     * 不包含上个月的最后一天的那个值班人员
     * @return
     */
    public List<Person> getTimesPersons(List<Person> personList,String personId,List<Person> personInfoList,int count){

        //指定上一个值班人员的下标
        int index = 0;
        for (int i = 0;i < personList.size();i++) {
            Person person = personList.get(i);
            if (personId.equals(person.getId())) {
                index = i;
                break;
            }
        }
        for (int j = index + 1;j < personList.size();j++) {
            Person person = personList.get(j);
            personInfoList.add(person);
        }
        //不够补上
        int num = count - personInfoList.size();
        list(personList,personInfoList,num,count);
        return personInfoList;
    }

    /**
     * 按天排班,某个组下需要安排的人
     * 含上个月的最后一个人
     * @param personList
     * @param personId
     * @param personInfoList
     * @return
     */
    public List<Person> getDayPersons(List<Person> personList,String personId,List<Person> personInfoList){
        //指定上一个值班人员的下标
        int index = 0;
        int count = 6;//组下的排班人员数量
        for (int i = 0;i < personList.size();i++) {
            Person person = personList.get(i);
            if (personId.equals(person.getId())) {
                index = i;
                break;
            }
        }
        for (int j = index;j < personList.size();j++) {
            Person person = personList.get(j);
            personInfoList.add(person);
        }
        //不够补上
        int num = count - personInfoList.size();
        list(personList,personInfoList,num,count);
        return personInfoList;
    }

    /**
     * 不够补上
     * @param personList
     * @param personInfoList
     * @param num
     */
    public void list(List<Person> personList,List<Person> personInfoList,int num,int count){
        if(num > 0) {
            for (int j = 0;j < personList.size();j++) {
                if(num == (j)) {
                    break;
                }else {
                    personInfoList.add(personList.get(j));
                }
            }
            int result = count - personInfoList.size();
            if(result > 0) {
                list(personList,personInfoList,result,count);
            }else {
                return;
            }
        }
    }


    /**
     * 获取单个班次的值班人员列表 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findList(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);

    }

    /**
     * 保存单个班次的值班人员列表 SchedulingListVo不能为空
     * @param schedulingVoList
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> createList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody List<SchedulingVo> schedulingVoList) {
        List<Scheduling> schedulingList = mapper.voListToEntityList(schedulingVoList);
        Scheduling result = service.createList(schedulingList);
        SchedulingVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 保存单个班次的值班人员的换班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SchedulingVo vo) {
        Scheduling entity = mapper.voToEntity(vo);
        Scheduling result = service.create(entity);
        SchedulingVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     *  获取当天参与交接班的值班人员列表
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findCurrentDutys() {
        List<Scheduling> list = service.findCurrentDutys();
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     *  获取当天与下一天参与交接班的值班人员列表
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findNextDutys(String orgId) {
        List<Scheduling> list = service.findNextDutys(orgId);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取personId集合
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPersonIds(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Person> personList = service.findPersonIds(vo);
        List<Scheduling> list = new ArrayList<Scheduling>(personList.size());
        for (Person person : personList){
            Scheduling entity = new Scheduling();
            Person person_ = new Person();
            person_.setId(person.getId());
            entity.setPerson(person_);
            entity.setPersonName(person.getAddrName());
            list.add(entity);
        }
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findListByPersonId(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findListByPersonId(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取teamId集合
     * @param orgId
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findTeamIds(
            @Validated
            @NotNull(message = "orgId不能为空")String orgId) {
        List<Scheduling> list = service.findTeamIds(orgId);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 根据这种模式获取当前时间的值班人员信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findListByMultiCondition(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findListByMultiCondition(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取某种模式下的分组ID列表
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findTeamIdList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findTeamIdList(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 前一个的teamId
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPreTeamId(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> entity = service.findPreTeamId(vo);
        List<SchedulingVo> entityVo = mapper.entityListToVoList(entity);
        return ResponseEntity.ok(entityVo);
    }

    /**
     * 根据这种模式和分組ID获取前一个的值班人员信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPreListByMultiCondition(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findPreListByMultiCondition(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 后一个的teamId
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findNextTeamId(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> entity = service.findNextTeamId(vo);
        List<SchedulingVo> entityVo = mapper.entityListToVoList(entity);
        return ResponseEntity.ok(entityVo);
    }

    /**
     * 根据这种模式和分組ID获取后一个的值班人员信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findNextListByMultiCondition(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findNextListByMultiCondition(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 值班信息批量插入
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> createBatch(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody List<SchedulingVo> vo) {
        List<Scheduling> list = service.createBatch(vo);
        List<SchedulingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 值班信息分组查询
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findByCode(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findByCode(vo);
        List<SchedulingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 去重获取某一天的班次集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findByShifts(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findByShifts(vo);
        List<SchedulingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 去重获取某一天按天集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    /*@Override
    public ResponseEntity<List<SchedulingVo>> findByDutyTeamIds(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findByDutyTeamIds(vo);
        List<SchedulingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }*/

    /**
     *  获取某个班次的人员集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPersons(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findPersons(vo);
        List<SchedulingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 去重获取某一天按天集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findByDutyTeamIds(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findByDutyTeamIds(vo);
        List<SchedulingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据当前时间获取当前班次Id
     * @param  localDateTime
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findShiftPattId(
            @Validated
            @NotNull(message = "localDateTime不能为空")
            @RequestBody LocalDateTime localDateTime) {
        Scheduling entity = service.findShiftPattId(localDateTime);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }

    /**
     * 查看当月是否有排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findSchedulingFlag(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findSchedulingFlag(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);

    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findListCondition(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findListCondition(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取当前值班模式下的人员值班
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPersonsByDTypeCode(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findPersonsByDTypeCode(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取当前班次的开始时间
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findCurrentStartTime(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findCurrentStartTime(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取前一个班次的信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPrevPersons(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findPrevPersons(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 根据前一个班次的开始时间 查询在那一天的起止时间之内
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findPreviousPerson(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        Scheduling entity = service.findPreviousPerson(vo);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }

    /**
     * 按天的值班信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findDayPersonInfo(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        Scheduling entity = service.findDayPersonInfo(vo);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }

    /**
     * 获取前一个班次的信息(当前班次的开始时间 为空)
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPrevPersonsInfo(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findPrevPersonsInfo(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取前一个排班的信息(当前天的开始时间 为空) (天)
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findDayInfo(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        Scheduling entity = service.findDayInfo(vo);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }

    /**
     * 获取后一个班次的信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findNextPersons(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findNextPersons(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 获取后一个天的值班信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findNextDay(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        Scheduling entity = service.findNextDay(vo);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }

    /**
     * 获取后一个班次的信息(班次)
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findNextPersonsInfo(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findNextPersonsInfo(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 按天的值班信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findNextDayInfo(@Validated
                                                        @NotNull(message = "vo不能为空")
                                                        @RequestBody SchedulingVo vo) {
        Scheduling entity = service.findNextDayInfo(vo);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }

    /**
     * 根据日期获取当天的集团及各板块的值班人员列表，包括领导和值班员等，供浙能首页使用
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<DutyMan>> getAllDutysByDate(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SearchAllDutyVo vo) {
        List<DutyMan> list = service.getAllDutysByDate(vo);
//        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(list);
    }

    /**
     * 获取一个月值班人员日历 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<List<SchedulingVo>>> findCalList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        Integer daysNumber = vo.getDaysNumber();
        List<List<SchedulingVo>> listAll  = new ArrayList <List<SchedulingVo>>();
        String date = vo.getDutyDate();
        for (int i = 1; i <= daysNumber; i++) {
            String dutyDate = null;
            if (i < 10) {
                dutyDate = date +"-"+ "0" + i;
            } else {
                dutyDate = date +"-"+ i;
            }
            vo.setDutyDate(dutyDate);
            List<Scheduling> list = service.findList(vo);
            List<SchedulingVo> voResult = mapper.entityListToVoList(list);
            listAll.add(voResult);
        }
        return ResponseEntity.ok(listAll);
    }

    /**
     * 值班信息插入
     * @param  calenSettingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> saveSchedulings(
            @NotNull(message="calenSettingVo不能为空")
            @Validated
            @RequestBody CalenSettingVo calenSettingVo) {

        //整月日历设置
        List<CalenSetting> vos = calenSettingService.findList(calenSettingVo);
        //获取整月数据入库
        List<SchedulingVo> schedulingVoList = new ArrayList<SchedulingVo>();
        //遍历每一天的值班班次信息拼接值班表信息入库
        for (CalenSetting calenSettings : vos) {
            Integer type = Integer.valueOf(calenSettings.getDateTypeCode());
            String orgIds = calenSettings.getOrgId();
            LocalDate date = calenSettings.getSettingDate();
            String time = DateUtil.getDateStr(date);
            String holidayName = calenSettings.getHolidayName();
            //查询模式设置表Id
            PatternSettingVo patternSettingVo = new PatternSettingVo();
            patternSettingVo.setDtypeCode(type);
            patternSettingVo.setOrgId(orgIds);
            PatternSetting ps = patternSettingService.findOne(patternSettingVo);
            String id = ps.getId();//模式设置id
            //查询值班人员模式
            List<PersonTypePattern> personTypePatternVo = personTypePatternService.findList(id);
            //遍历判断按班次或者是按天值班

            //如果值班人员模式为空的话
            if (CollectionUtils.isEmpty(personTypePatternVo)){
                //该天未设置值班人员模式
                SchedulingVo schedulingVo = new SchedulingVo();
                schedulingVo.setOrgId(orgIds);
                schedulingVo.setDutyDate(time);
                schedulingVo.setDateTypeCode(type);
                String name = SchedulingUtil.getName(type);
                schedulingVo.setDateTypeName(name);
                schedulingVo.setHolidayName(holidayName);
                schedulingVoList.add(schedulingVo);
                return null;
            }else{
                for (PersonTypePattern personType : personTypePatternVo) {
                    String dutyTypeCode = personType.getDutyTypeCode();
                    //（0：按班次值班，1：按天值班）
                    if (dutyTypeCode.equals(SchedulingGlobal.P_TYPE_CODE_DAY)) {
                        //组装数据存入值班表
                        //按天值班
                        SchedulingVo schedulingVo = new SchedulingVo();
                        schedulingVo.setOrgId(orgIds);
                        schedulingVo.setOrgName(calenSettingVo.getOrgName());
                        schedulingVo.setDutyDate(time);
                        schedulingVo.setDateTypeCode(type);
                        String name = SchedulingUtil.getName(type);
                        schedulingVo.setDateTypeName(name);
                        schedulingVo.setHolidayName(holidayName);
                        schedulingVo.setDutyTeamId(personType.getTeamId());
                        schedulingVo.setDutyTeamName(personType.getTeamName());
                        schedulingVo.setPtypeCode(dutyTypeCode);
                        //根据模式设置id查询班次
                        List<ShiftPattern> shiftPatternVo = shiftPatternService.findList(id);
                        if (!CollectionUtils.isEmpty(shiftPatternVo)){

                            ShiftPattern startTime = shiftPatternVo.get(0);
                            ShiftPattern endTime = shiftPatternVo.get(shiftPatternVo.size()-1);
                            String isToday = endTime.getIsToday();
                            String start = null;
                            String end = null;
                            if (!StringUtils.isBlank(isToday)){
                                if (isToday.equals(SchedulingGlobal.SCHEDULING_IS_TODAY_YES)){
                                    //当日
                                    start = time + " " + startTime.getStartTime() + ":00";
                                    end = time + " " + endTime.getEndTime() + ":00";
                                }else{
                                    start = time + " " + startTime.getStartTime() + ":00";
                                    //下一日
                                    String addDay = DateUtil.addOneDay(time,"yyyy-MM-dd");
                                    end = addDay + " " + endTime.getEndTime() + ":00";
                                }
                            }
                            schedulingVo.setStartTime(start);
                            schedulingVo.setEndTime(end);
                        }
                        schedulingVoList.add(schedulingVo);
                    } else {
                        //按班次值班
                        //查询班次信息
                        List<ShiftPattern> shiftPatternVo = shiftPatternService.findList(id);
                        for (ShiftPattern shift : shiftPatternVo) {
                            SchedulingVo schedulingVo = new SchedulingVo();
                            schedulingVo.setOrgId(orgIds);
                            schedulingVo.setOrgName(calenSettingVo.getOrgName());
                            schedulingVo.setDutyDate(time);
                            schedulingVo.setDateTypeCode(type);
                            String name = SchedulingUtil.getName(type);
                            schedulingVo.setDateTypeName(name);
                            schedulingVo.setHolidayName(holidayName);
                            schedulingVo.setDutyTeamId(personType.getTeamId());
                            schedulingVo.setDutyTeamName(personType.getTeamName());
                            schedulingVo.setShiftPatternId(shift.getId());
                            schedulingVo.setShiftPatternName(shift.getShiftName());
                            String isToday = shift.getIsToday();
                            String startTime = null;
                            String endTime = null;
                            if (!StringUtils.isBlank(isToday)){
                                if (isToday.equals(SchedulingGlobal.SCHEDULING_IS_TODAY_YES)){
                                    //当日
                                    startTime = time + " " + shift.getStartTime() + ":00";
                                    endTime = time + " " + shift.getEndTime() + ":00";
                                }else{
                                    startTime = time + " " + shift.getStartTime() + ":00";
                                    //下一日
                                    String addDay = DateUtil.addOneDay(time,"yyyy-MM-dd");
                                    endTime = addDay + " " + shift.getEndTime() + ":00";
                                }
                            }
                            schedulingVo.setStartTime(startTime);
                            schedulingVo.setEndTime(endTime);
                            schedulingVo.setPtypeCode(dutyTypeCode);
                            schedulingVoList.add(schedulingVo);
                        }
                    }
                }
            }
        }
        //保存值班信息
        List<Scheduling> list = schedulingService.createBatch(schedulingVoList);
        List<SchedulingVo> voList = schedulingMapper.entityListToVoList(list);
        return  ResponseEntity.ok(voList);
    }

    /**
     * 查询该月是否有人员排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findPersonsList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        List<Scheduling> list = service.findPersonsList(vo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);

    }

    /**
     * 删除整月数据 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<Void> deteleSchedulingsList(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody SchedulingVo vo) {
        service.deteleSchedulingsList(vo);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取下个班次的值班人员信息
     * @param schedulingVo
     * @return
     */
    @Override
    public ResponseEntity<List<SchedulingVo>> findNextTimesList(@Validated @NotNull(message = "vo不能为空") @RequestBody SchedulingVo schedulingVo) {
        //获取当前班次的开始时间
        LocalDateTime startTime = service.findCurrentTimesOfStartTime(schedulingVo);
        if (null != startTime) {
            //获取下个班次的开始时间
            schedulingVo.setStartTime(DateUtil.getDateTimeStr(startTime));
            LocalDateTime startTimeNext = service.findNextTimesOfStartTime(schedulingVo);
            if (null != startTimeNext){
                schedulingVo.setStartTime(DateUtil.getDateTimeStr(startTimeNext));
                //获取下个班次值班人员信息
                List<Scheduling> list = service.findNextTimesInfo(schedulingVo);
                List<SchedulingVo> voResult = mapper.entityListToVoList(list);
                return ResponseEntity.ok(voResult);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

}
