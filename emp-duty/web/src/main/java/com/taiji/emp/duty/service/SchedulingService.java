package com.taiji.emp.duty.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.feign.*;
import com.taiji.emp.duty.searchVo.*;
import com.taiji.emp.duty.util.SchedulingUtil;
import com.taiji.emp.duty.vo.*;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulingService extends BaseService {

    @Autowired
    SchedulingClient schedulingClient;
    @Autowired
    PatternSettingClient patternSettingClient;
    @Autowired
    ShiftPatternClient shiftPatternClient;
    @Autowired
    PersonTypePatternClient personTypePatternClient;
    @Autowired
    DutyTeamClient dutyTeamClient;
    @Autowired
    PersonClient personClient;
    @Autowired
    private UtilsFeignClient utilsFeignClient;
    @Autowired
    private CalenSettingClient calenSettingClient;
    @Autowired
    private OrgClient orgClient;
    @Autowired
    private DicItemClient dicItemClient;
    @Autowired
    private SigninClient signinClient;


    /**
     * 获取单个班次的值班人员列表 SchedulingVo不能为空
     *
     * @param vo
     * @return
     */
    public List<SchedulingVo> findList(SchedulingVo vo) {
        Assert.notNull(vo, "SchedulingVo不能为null");
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findList(vo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 保存单个班次的值班人员列表 SchedulingListVo不能为空
     *
     * @param vo
     * @return
     */
    public void createSchedulings(SchedulingListVo vo,OAuth2Authentication principal) {
        Assert.notNull(vo, "SchedulingListVo不能为null");
        List<SchedulingVo> schedulingVoList = vo.getData();
        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        for(SchedulingVo schedulingVo : schedulingVoList){
            schedulingVo.setOrgId(orgId);
        }
        schedulingClient.createList(schedulingVoList);
    }

    /**
     * 保存单个班次的值班人员的换班 SchedulingVo不能为空
     *
     * @param vo
     * @return
     */
    public void createExchangeSchedulings(SchedulingVo vo) {
        Assert.notNull(vo, "SchedulingVo不能为null");
        schedulingClient.create(vo);
    }

    /**
     * 获取personId集合
     *
     * @param vo
     * @return
     */
    public List<SchedulingVo> findPersonIds(SchedulingVo vo) {
        Assert.notNull(vo, "vo不能为null");
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findPersonIds(vo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 值班统计
     *
     * @param vo
     * @return
     */
    public List<SchedulingVo> findListByPersonId(SchedulingVo vo) {
        Assert.notNull(vo, "vo不能为null");
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findListByPersonId(vo);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 获取teamId集合
     *
     * @param orgId
     * @return
     */
    public List<String> findTeamIds(String orgId) {
        Assert.hasText(orgId, "orgId不能为null");
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findTeamIds(orgId);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        List<String> teamList = new ArrayList<>(voList.size());
        for (SchedulingVo vo : voList) {
            teamList.add(vo.getDutyTeamId());
        }
        return teamList;
    }

    /**
     * 获取值排班数据列表 SchedulingVo不能为空
     *
     * @param vo
     * @return
     */
    public List<SchedulingsListVo> findListSchedulings(SchedulingVo vo, OAuth2Authentication principal) {
        Assert.notNull(vo, "SchedulingVo不能为null");
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        if (StringUtils.isBlank(orgId)) {
            vo.setOrgId(orgId);
        }
        //添加人员查询条件
        List<String> personIds = vo.getPersonIds();
        return getList(vo.getMonth(), vo.getOrgId(),personIds);
    }

    public MonthSchedulingVo findCalSchedulings(SchedulingVo vo, OAuth2Authentication principal) {
        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        String orgName = userMap.get("orgName").toString(); //创建单位名称
        vo.setOrgName(orgName);
        if (StringUtils.isBlank(orgId)) {
            vo.setOrgId(orgId);
        }
        int number = SchedulingUtil.getMonthNumber(vo.getMonth());
        CalenSettingVo calenSettingVo = new CalenSettingVo();
        calenSettingVo.setDay(String.valueOf(number));
        calenSettingVo.setOrgId(vo.getOrgId());
        calenSettingVo.setOrgName(vo.getOrgName());
        calenSettingVo.setMonth(vo.getMonth());
        //是否有日历设置
        ResponseEntity<List<CalenSettingVo>> calVos = calenSettingClient.findAll(calenSettingVo);
        List<CalenSettingVo> calvosVoList = ResponseEntityUtils.achieveResponseEntityBody(calVos);
        if (!CollectionUtils.isEmpty(calvosVoList)){
            //该月是否有排班
            ResponseEntity<List<SchedulingVo>> schedulingVo = schedulingClient.findList(vo);
            List<SchedulingVo> schedulingVoList = ResponseEntityUtils.achieveResponseEntityBody(schedulingVo);
            if (CollectionUtils.isEmpty(schedulingVoList)){
                //是否有值班模式设置
                //调用保存方法
                List<SchedulingVo> list = saveSchedulings(vo);
                if (CollectionUtils.isEmpty(list)){
                    return null;
                }else{
                    //获取返回值拼接前台日历数据
                    return packList(vo.getMonth(), vo.getOrgId());
                }
            }
            return packList(vo.getMonth(), vo.getOrgId());
        }else{
            return null;
        }
    }

    /**
     * 获取自动值排班数据 SchedulingVo不能为空
     *
     * @param vo
     * @return
     */
    public void findAutoSchedulings(SchedulingVo vo, Principal principal) {
        Assert.notNull(vo, "SchedulingVo不能为null");
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        String orgId = vo.getOrgId();
        if (StringUtils.isBlank(orgId)) {
            vo.setOrgId(profileVo.getOrgId());
        }
        //编写自动排班
        schedulingClient.autoScheduling(vo);
    }

    //自动排班
    public MonthSchedulingVo autoScheduling(String date, String orgId) {
        //获取数据
        Calendar caLen = SchedulingUtil.dayForCalen(date);
        //当月第一天是周几
        int week = caLen.get(Calendar.DAY_OF_WEEK) - 1;

        return packList(date,orgId);
    }

    //组装前端需要数据

    //组装前端需要数据
    public MonthSchedulingVo packList(String date, String orgId) {
        //当前月份
//        int month = calen.get(Calendar.MONTH) + 1;

        //获取数据
        Calendar calen = SchedulingUtil.dayForCalen(date);
        //当前天数
        int day = SchedulingUtil.getMonthNumber(date);
        //当月第一天是周几
        int week = calen.get(Calendar.DAY_OF_WEEK) - 1;

        MonthSchedulingVo monthSchedulingVo = new MonthSchedulingVo();
        SchedulingSearchVo schedulingSearchVo = new SchedulingSearchVo();
        List<SchedulingSearchVo> searchVoList = new ArrayList<SchedulingSearchVo>();
        SchedulingVo scheVo = new SchedulingVo();
        scheVo.setOrgId(orgId);
        scheVo.setMonth(date);
        ResponseEntity<List<SchedulingVo>> voS = schedulingClient.findSchedulingFlag(scheVo);
        List<SchedulingVo> vosList = ResponseEntityUtils.achieveResponseEntityBody(voS);
        if (!CollectionUtils.isEmpty(vosList)){
            monthSchedulingVo.setSchedulingFlag(SchedulingGlobal.SCHEDULING_FLAG_YES);
        }else{
            monthSchedulingVo.setSchedulingFlag(SchedulingGlobal.SCHEDULING_FLAG_NO);
        }
        scheVo.setDaysNumber(day);
        scheVo.setDutyDate(date);
        //获取整月数据
        ResponseEntity<List<List<SchedulingVo>>> lists = schedulingClient.findCalList(scheVo);
        List<List<SchedulingVo>> listAll = ResponseEntityUtils.achieveResponseEntityBody(lists);

        if (week == 1) {
            searchVoList.add(schedulingSearchVo);
            forSchedulings(listAll,searchVoList);
            //补齐后面的空缺
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        } else if (week == 2) {
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            forSchedulings(listAll,searchVoList);
            //补齐后面的空缺
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        } else if (week == 3) {
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            forSchedulings(listAll,searchVoList);
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        } else if (week == 4) {
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            forSchedulings(listAll,searchVoList);
            //补齐后面的空缺
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        } else if (week == 5) {
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            forSchedulings(listAll,searchVoList);
            //补齐后面的空缺
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        } else if (week == 6) {
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            searchVoList.add(schedulingSearchVo);
            forSchedulings(listAll,searchVoList);
            //补齐后面的空缺
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        } else if (week == 0) {
            forSchedulings(listAll,searchVoList);
            //补齐后面的空缺
            appendSchedulingSearchVo(date,searchVoList,day,week);
            monthSchedulingVo.setCalScheduling(searchVoList);
        }
        return monthSchedulingVo;
    }

    /**
     * 获取值班模式
     *
     * @return
     */
    public CalenSettingVo findSettingDate() {
        LocalDateTime currentDateTime = getCurrentTime();
        LocalDate currentDate = currentDateTime.toLocalDate();
        ResponseEntity<CalenSettingVo> resultVo = calenSettingClient.findSettingDate(null,null);
        CalenSettingVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据这种模式获取[当前时间]的值班人员信息(班次)
     * @return
     */
    public List<SchedulingVo> findShiftListByMultiCondition(Principal principal) {
        //当前时间
        LocalDateTime currentDateTime = getCurrentTime();
        String pTypeCode = SchedulingGlobal.P_TYPE_CODE_TIMES;
        List<SchedulingVo> voList = findCurrentListByMultiCondition(currentDateTime,pTypeCode,principal);
        return voList;
    }

    /**
     * 根据这种模式获取[当前时间]的值班人员信息(天)
     * @return
     */
    public List<SchedulingVo> findDayListByMultiCondition(Principal principal) {
        //当前时间
        LocalDateTime currentDateTime = getCurrentTime();
        String pTypeCode = SchedulingGlobal.P_TYPE_CODE_DAY;
        List<SchedulingVo> voList = findCurrentListByMultiCondition(currentDateTime,pTypeCode,principal);
        return voList;
    }

    /**
     * 根据这种模式获取[前一个]的值班人员信息(班次)
     * @return
     */
    public List<SchedulingVo> findPerShiftListByMultiCondition(Principal principal) {
        //当前时间
        LocalDateTime currentDateTime = getCurrentTime();
        String pTypeCode = SchedulingGlobal.P_TYPE_CODE_TIMES;
        List<SchedulingVo> voList = findPreListByMultiCondition(currentDateTime, pTypeCode,principal);
        return voList;
    }

    /**
     * 根据这种模式获取[前一个]的值班人员信息(天)
     * @return
     */
    public List<SchedulingVo> findPerDayListByMultiCondition(Principal principal) {
        //当前时间
        LocalDateTime currentDateTime = getCurrentTime();
        String pTypeCode = SchedulingGlobal.P_TYPE_CODE_DAY;
        List<SchedulingVo> voList = findPreListByMultiCondition(currentDateTime,pTypeCode,principal);
        return voList;
    }

    /**
     * 根据这种模式获取[后一个]的值班人员信息(班次)
     * @return
     */
    public List<SchedulingVo> findNextShiftListByMultiCondition(Principal principal) {
        //当前时间
        LocalDateTime currentDateTime = getCurrentTime();
        String pTypeCode = SchedulingGlobal.P_TYPE_CODE_TIMES;
        List<SchedulingVo> voList = findNextListByMultiCondition(currentDateTime,pTypeCode,principal);
        return voList;
    }

    /**
     * 根据这种模式获取[后一个]的值班人员信息(天)
     * @return
     */
    public List<SchedulingVo> findNextDayListByMultiCondition(Principal principal) {
        //当前时间
        LocalDateTime currentDateTime = getCurrentTime();
        String pTypeCode = SchedulingGlobal.P_TYPE_CODE_DAY;
        List<SchedulingVo> voList = findNextListByMultiCondition(currentDateTime,pTypeCode,principal);
        return voList;
    }

    /**
     * 根据这种模式获取[当前时间]的值班人员信息
     * @param currentDateTime
     * @param pTypeCode
     * @return
     */
    private List<SchedulingVo> findCurrentListByMultiCondition(LocalDateTime currentDateTime,String pTypeCode,Principal principal){

        //获取当前用户orgId
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        String orgId = profileVo.getOrgId();
        //获取值班模式
        SchedulingVo vo = getCaLenSetting(currentDateTime,pTypeCode,orgId);
        //获取分组ID列表
        ResponseEntity<List<SchedulingVo>> teamIdList = schedulingClient.findTeamIdList(vo);
        List<SchedulingVo> voTeamIdList = ResponseEntityUtils.achieveResponseEntityBody(teamIdList);
        //获取其他数据
        List<SchedulingVo> voList = getOtherData(vo,voTeamIdList);
        return voList;
    }

    /**
     * 根据这种模式获取[前一个]的值班人员信息
     * @param currentDateTime
     * @param pTypeCode
     * @return
     */
    private List<SchedulingVo> findPreListByMultiCondition(LocalDateTime currentDateTime,String pTypeCode,Principal principal){
        List<SchedulingVo> voList = null;
        //获取当前用户orgId
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        String orgId = profileVo.getOrgId();
        //获取值班模式
        SchedulingVo vo = getCaLenSetting(currentDateTime,pTypeCode,orgId);
        //获取分组ID列表
        ResponseEntity<List<SchedulingVo>> teamIdList = schedulingClient.findTeamIdList(vo);
        List<SchedulingVo> voTeamIdList = ResponseEntityUtils.achieveResponseEntityBody(teamIdList);
        if(null != voTeamIdList && voTeamIdList.size() > 0){
            SchedulingVo vo1 = voTeamIdList.get(0);
            vo1.setPtypeCode(pTypeCode);  //按天或班次
            //前一个的teamId
            ResponseEntity<List<SchedulingVo>> entityVo = schedulingClient.findPreTeamId(vo1);
            List<SchedulingVo> schedulingVo = ResponseEntityUtils.achieveResponseEntityBody(entityVo);
            if (null != schedulingVo && schedulingVo.size() > 0) {
                for (SchedulingVo entity : schedulingVo) {
                    vo.setDutyTeamId(null);
                    vo.setDutyTeamId(entity.getDutyTeamId());
                    entity.setDateTypeCode(Integer.valueOf(entity.getDateTypeCode()));
                    entity.setPtypeCode(pTypeCode);
                    entity.setDutyTeamId(null);
                    //值班日期、值班分组ID、值班分组名称、班次名称
                    ResponseEntity<List<SchedulingVo>> list = schedulingClient.findPreListByMultiCondition(entity);
                    voList = ResponseEntityUtils.achieveResponseEntityBody(list);
                    getPersonDataAndIsShift(voList);
                }
            }

        }
        return voList;
    }

    /**
     * 根据这种模式获取[后一个]的值班人员信息
     * @param currentDateTime
     * @return
     */
    private List<SchedulingVo> findNextListByMultiCondition(LocalDateTime currentDateTime,String pTypeCode,Principal principal){
        List<SchedulingVo> voList = null;
        //获取当前用户orgId
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        String orgId = profileVo.getOrgId();
        //获取值班模式
        SchedulingVo vo = getCaLenSetting(currentDateTime,pTypeCode,orgId);
        //获取分组ID列表
        ResponseEntity<List<SchedulingVo>> teamIdList = schedulingClient.findTeamIdList(vo);
        List<SchedulingVo> voTeamIdList = ResponseEntityUtils.achieveResponseEntityBody(teamIdList);
        if(null != voTeamIdList && voTeamIdList.size() > 0){
            for (SchedulingVo vo1 : voTeamIdList){
                //后一个的teamId
                ResponseEntity<List<SchedulingVo>> entityVo = schedulingClient.findNextTeamId(vo1);
                List<SchedulingVo> schedulingVo = ResponseEntityUtils.achieveResponseEntityBody(entityVo);
                if (null != schedulingVo && schedulingVo.size() > 0) {
                    for (SchedulingVo entity : schedulingVo) {
                        vo.setDutyTeamId(null);
                        vo.setDutyTeamId(entity.getDutyTeamId());
                        entity.setDateTypeCode(Integer.valueOf(entity.getDateTypeCode()));
                        entity.setPtypeCode(pTypeCode);
                        entity.setDutyTeamId(null);
                        //值班日期、值班分组ID、值班分组名称、班次名称
                        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findNextListByMultiCondition(entity);
                        voList = ResponseEntityUtils.achieveResponseEntityBody(list);
                        getPersonDataAndIsShift(voList);
                    }
                }
            }
        }
        return voList;
    }

    private List<SchedulingVo> getOtherData(SchedulingVo vo,List<SchedulingVo> voTeamIdList){
        List<SchedulingVo> voList = null;
        if (null != voTeamIdList && voTeamIdList.size() > 0){
            for (SchedulingVo entity : voTeamIdList){
                vo.setDutyTeamId(null);
                vo.setDutyTeamId(entity.getDutyTeamId());
                //值班日期、值班分组ID、值班分组名称、班次名称
                ResponseEntity<List<SchedulingVo>> list = schedulingClient.findListByMultiCondition(vo);
                voList = ResponseEntityUtils.achieveResponseEntityBody(list);
                getPersonDataAndIsShift(voList);
            }
        }
        return voList;
    }

    private void getPersonDataAndIsShift(List<SchedulingVo> voList){
        if (null != voList && voList.size() > 0){
            for(SchedulingVo entityVo : voList){
                String dutyTeamId = entityVo.getDutyTeamId();
                //根据分组ID获取 是否交接班
                ResponseEntity<DutyTeamVo> responseEntity = dutyTeamClient.findOne(dutyTeamId);
                DutyTeamVo dutyTeamVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
                String isShift = dutyTeamVo.getIsShift();
                entityVo.setIsShift(isShift);
                List<String> teamList = new ArrayList<String>(1);
                teamList.add(dutyTeamId);
                List<String> personIdList = new ArrayList<String>(1);
                if(null != entityVo.getPersonId()) {
                    personIdList.add(entityVo.getPersonId());
                    entityVo.setTeamList(teamList);
                    entityVo.setPersonIds(personIdList);
                    //值班人员ID、值班人员姓名
                    ResponseEntity<List<SchedulingVo>> personIds = schedulingClient.findPersonIds(entityVo);
                    List<SchedulingVo> personsVo = ResponseEntityUtils.achieveResponseEntityBody(personIds);
                    List<PersonVo> personInfo = new ArrayList<PersonVo>(personsVo.size());
                    if (null != personsVo && personsVo.size() > 0) {
                        for (SchedulingVo schedulingVo : personsVo) {
                            PersonVo personVo = new PersonVo();
                            personVo.setId(schedulingVo.getPersonId());
                            personVo.setAddrName(schedulingVo.getPersonName());
                            personInfo.add(personVo);
                        }
                        entityVo.setPersonInfo(personInfo);
                    } else {
                        entityVo.setPersonInfo(personInfo);
                    }
                }else {
                    entityVo.setPersonInfo(null);
                }
            }
        }
    }

    /*********************************上一班次***************************************/
    public List<SchedulingVo> findPrevDutyPersons(Principal principal,String shiftFlag) {
        List<SchedulingVo> prevList = new ArrayList<>();
        SchedulingVo entityDay = null;
        List<SchedulingVo> prevTimePersonsList = null;
        LocalDateTime currentDateTime = getCurrentTime();
        //获取当前用户orgId
        String orgId = getOrgId(principal);
        //①、获取值班模式(班次)
        SchedulingVo vo = getCaLenSetting(currentDateTime,null,orgId);
        vo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_TIMES);
        vo.setOrgId(orgId);
        //获取分组信息
        ResponseEntity<List<DutyTeamVo>> teamClientList = dutyTeamClient.findList(orgId);
        List<DutyTeamVo> dutyTeamVoList = ResponseEntityUtils.achieveResponseEntityBody(teamClientList);
        //获取当前班次的开始时间
        ResponseEntity<List<SchedulingVo>> responseList = schedulingClient.findCurrentStartTime(vo);
        List<SchedulingVo> startTimeList = ResponseEntityUtils.achieveResponseEntityBody(responseList);
        if (!CollectionUtils.isEmpty(startTimeList)){
            //1）当前班次的开始时间 不为空
            SchedulingVo schedulingVo = startTimeList.get(SchedulingGlobal.SCHEDULING_ZERO);
            //获取当前班次的开始时间
            String normalStartTime = schedulingVo.getStartTime();
            vo.setCurrentDutyDate(normalStartTime);
            //获取前一个班次的信息
            ResponseEntity<List<SchedulingVo>> responsePersonsList = schedulingClient.findPrevPersons(vo);
            prevTimePersonsList = ResponseEntityUtils.achieveResponseEntityBody(responsePersonsList);

            //②、获取值班模式(天)
            SchedulingVo dayVo = new SchedulingVo();
            dayVo.setOrgId(orgId);
            dayVo.setDateTypeCode(vo.getDateTypeCode());
            dayVo.setCurrentDutyDate(vo.getCurrentDutyDate());
            dayVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            //根据前一个班次的开始时间 查询在那一天的起止时间之内
            ResponseEntity<SchedulingVo> responseStartTime = schedulingClient.findPreviousPerson(dayVo);
            SchedulingVo startTimeEntity = ResponseEntityUtils.achieveResponseEntityBody(responseStartTime);

            if (null != startTimeEntity) {
                String previousStartTime = startTimeEntity.getStartTime();
                dayVo.setCurrentDutyDate(previousStartTime);
            }else {
                currentDateTime = currentDateTime.minusDays(1);
                LocalDate dutydate = currentDateTime.toLocalDate();
                dayVo.setDutyDate(DateUtil.getDateStr(dutydate));
            }
            //按天的值班信息
            ResponseEntity<SchedulingVo> responsePerson = schedulingClient.findDayPersonInfo(dayVo);
            entityDay = ResponseEntityUtils.achieveResponseEntityBody(responsePerson);
        }else {
            //2）当前班次的开始时间 为空
            LocalDate currentDate = currentDateTime.toLocalDate();
            vo.setDutyDate(DateUtil.getDateStr(currentDate));
            vo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_TIMES);
            //获取前一个班次的信息(班次)
            ResponseEntity<List<SchedulingVo>> responsePersonsList = schedulingClient.findPrevPersonsInfo(vo);
            prevTimePersonsList = ResponseEntityUtils.achieveResponseEntityBody(responsePersonsList);
            //按天的值班信息
            vo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            ResponseEntity<SchedulingVo> responsePerson = schedulingClient.findDayInfo(vo);
            entityDay = ResponseEntityUtils.achieveResponseEntityBody(responsePerson);
        }
        //添加死人
        List<SchedulingVo> collect = prevTimePersonsList.stream()
                .filter(temp -> temp.getPersonId() != null)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)){
            setPersonInfo(collect,shiftFlag,dutyTeamVoList);
        }
        prevList.addAll(prevTimePersonsList);
        if (null == entityDay){
            SchedulingVo schedulingVo = new SchedulingVo();
            schedulingVo.setOrgId(orgId);
            schedulingVo.setPtypeCode(DateUtil.getDateStr(currentDateTime.minusDays(1).toLocalDate()));
            schedulingVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            ResponseEntity<SchedulingVo> responsePerson = schedulingClient.findDayPersonInfo(schedulingVo);
            entityDay = ResponseEntityUtils.achieveResponseEntityBody(responsePerson);
        }
        prevList.add(entityDay);
        List<SchedulingVo> teamList = getSchedulingVoList(dutyTeamVoList, prevList);
        return teamList;
    }
    /*********************************当前班次***************************************/
    public List<SchedulingVo> findCurrentDutyPersons(Principal principal,String shiftFlag) {
        LocalDateTime currentDateTime = getCurrentTime();
        //获取当前用户orgId
        String orgId = getOrgId(principal);
        //获取值班模式
        SchedulingVo vo = getCaLenSetting(currentDateTime,null,orgId);
        //获取当前值班模式下的人员值班
        ResponseEntity<List<SchedulingVo>> responseList = schedulingClient.findPersonsByDTypeCode(vo);
        //含有按天或班次的
        List<SchedulingVo> resultList = ResponseEntityUtils.achieveResponseEntityBody(responseList);
        //获取分组信息
        ResponseEntity<List<DutyTeamVo>> teamClientList = dutyTeamClient.findList(orgId);
        List<DutyTeamVo> dutyTeamVoList = ResponseEntityUtils.achieveResponseEntityBody(teamClientList);

        List<SchedulingVo> dayList = resultList.stream()
                .filter(temp -> temp.getPtypeCode().equals("1") && temp.getPersonId() != null)
                .collect(Collectors.toList());

        List<SchedulingVo> timesList = resultList.stream()
                .filter(temp -> temp.getPtypeCode().equals("0") && temp.getPersonId() != null)
                .collect(Collectors.toList());

        //按天的值班人员
        if (dayList.size() == 0) {
            SchedulingVo dayVo = new SchedulingVo();
            dayVo.setOrgId(orgId);
            dayVo.setDutyDate(DateUtil.getDateStr(currentDateTime.toLocalDate()));
            dayVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            ResponseEntity<SchedulingVo> responsePerson = schedulingClient.findDayPersonInfo(dayVo);
            SchedulingVo schedulingVo = ResponseEntityUtils.achieveResponseEntityBody(responsePerson);
            if (null != schedulingVo) {
                resultList.add(schedulingVo);
                if (timesList.size() == 0) {
                    //设置死人
                    setPersonInfo(timesList,shiftFlag,dutyTeamVoList);
                    resultList.addAll(timesList);
                }
            }
        }

        //组装数据结构
        List<SchedulingVo> teamList = getSchedulingVoList(dutyTeamVoList, resultList);
        return teamList;
    }
    /*********************************下一班次***************************************/
    public List<SchedulingVo> findNextDutyPersons(Principal principal,String shiftFlag) {
        LocalDateTime currentDateTime = getCurrentTime();
        List<SchedulingVo> prevList = new ArrayList<>();
        SchedulingVo entityDay = null;
        List<SchedulingVo> nextTimePersonsList = null;
        //获取当前用户orgId
        String orgId = getOrgId(principal);
        //获取分组信息
        ResponseEntity<List<DutyTeamVo>> teamClientList = dutyTeamClient.findList(orgId);
        List<DutyTeamVo> dutyTeamVoList = ResponseEntityUtils.achieveResponseEntityBody(teamClientList);
        //①、获取值班模式(班次)
        SchedulingVo vo = getCaLenSetting(currentDateTime,null,orgId);
        vo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_TIMES);
        vo.setOrgId(orgId);
        //获取当前班次的开始时间
        ResponseEntity<List<SchedulingVo>> responseList = schedulingClient.findCurrentStartTime(vo);
        List<SchedulingVo> startTimeList = ResponseEntityUtils.achieveResponseEntityBody(responseList);
        if (!CollectionUtils.isEmpty(startTimeList)){
            //当前班次的开始时间 不为空
            SchedulingVo schedulingVo = startTimeList.get(SchedulingGlobal.SCHEDULING_ZERO);
            //当前班次的开始时间
            String normalStartTime = schedulingVo.getStartTime();
            vo.setCurrentDutyDate(normalStartTime);
            //获取后一个班次的信息
            ResponseEntity<List<SchedulingVo>> responseNextList = schedulingClient.findNextPersons(vo);
            nextTimePersonsList = ResponseEntityUtils.achieveResponseEntityBody(responseNextList);
            //获取值班模式(天)
            SchedulingVo dayVo = new SchedulingVo();
            dayVo.setOrgId(orgId);
            dayVo.setDateTypeCode(vo.getDateTypeCode());
            dayVo.setCurrentDutyDate(vo.getCurrentDutyDate());
            dayVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            //获取后一个天的值班信息
            ResponseEntity<SchedulingVo> responseNextEntity = schedulingClient.findNextDay(dayVo);
            entityDay = ResponseEntityUtils.achieveResponseEntityBody(responseNextEntity);
        }else {
            //当前班次的开始时间 为空
            LocalDate currentDate = currentDateTime.toLocalDate();
            vo.setDutyDate(DateUtil.getDateStr(currentDate));
            vo.setStartTime(DateUtil.getDateTimeStr(currentDateTime));
            vo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_TIMES);
            //获取后一个班次的信息(班次)
            ResponseEntity<List<SchedulingVo>> responsePersonsList = schedulingClient.findNextPersonsInfo(vo);
            nextTimePersonsList = ResponseEntityUtils.achieveResponseEntityBody(responsePersonsList);
            //按天的值班信息
            vo.setOrgId(orgId);
            vo.setDateTypeCode(vo.getDateTypeCode());
            vo.setCurrentDutyDate(vo.getCurrentDutyDate());
            vo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            ResponseEntity<SchedulingVo> responsePerson = schedulingClient.findNextDayInfo(vo);
            entityDay = ResponseEntityUtils.achieveResponseEntityBody(responsePerson);
        }
        //添加死人
        List<SchedulingVo> collect = nextTimePersonsList.stream()
                .filter(temp -> temp.getPersonId() != null).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)){
            setPersonInfo(collect,shiftFlag,dutyTeamVoList);
        }
        prevList.addAll(nextTimePersonsList);
        //按天
        if (null == entityDay){
            SchedulingVo schedulingVo = new SchedulingVo();
            schedulingVo.setOrgId(orgId);
            schedulingVo.setDutyDate(DateUtil.getDateStr(currentDateTime.plusDays(1).toLocalDate()));
            schedulingVo.setPtypeCode(SchedulingGlobal.P_TYPE_CODE_DAY);
            ResponseEntity<SchedulingVo> responsePerson = schedulingClient.findDayPersonInfo(schedulingVo);
            entityDay = ResponseEntityUtils.achieveResponseEntityBody(responsePerson);
        }
        prevList.add(entityDay);
        List<SchedulingVo> teamList = getSchedulingVoList(dutyTeamVoList, prevList);
        return teamList;
    }


    private void setPersonInfo(List<SchedulingVo> collect,String shiftFlag,List<DutyTeamVo> dutyTeamVoList){
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("dicCode", SchedulingGlobal.ITEM_DIC_CODE);
        ResponseEntity<List<DicGroupItemVo>> list = dicItemClient.findList(params);
        List<DicGroupItemVo> dicGroupItemVos = ResponseEntityUtils.achieveResponseEntityBody(list);
        //升序
        List<DicGroupItemVo> itemVoList = dicGroupItemVos.stream()
                .sorted(Comparator.comparing(DicGroupItemVo::getOrders))
                .collect(Collectors.toList());
        if(collect.size() > 0) {
            for (int i = 0; i < collect.size(); i++) {
                DicGroupItemVo dicGroupItemVo = itemVoList.get(i);
                SchedulingVo schedulingVo = collect.get(i);
                List<PersonVo> personInfo = new ArrayList<>(1);
                PersonVo personVo = new PersonVo();
                personVo.setId(dicGroupItemVo.getId());
                personVo.setAddrName(dicGroupItemVo.getItemName());
                personInfo.add(personVo);
                schedulingVo.setPersonInfo(personInfo);
            }
        }else {
            LocalDateTime currentTime = null;
            if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_CURRENT)){
                currentTime = getCurrentTime();
            }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_PREVIOUS)){
                currentTime = getCurrentTime().minusDays(1);
            }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_NEXT)){
                currentTime = getCurrentTime().plusDays(1);
            }

            //查询某天的某个班次的值班组信息
            List<DutyTeamVo> teamVoList = dutyTeamVoList.stream().filter(temp -> temp.getIsShift().equals("1")).collect(Collectors.toList());
            String dutyDate = DateUtil.getDateStr(currentTime.toLocalDate());
            for (int j = 0;j < itemVoList.size();j++){
                DicGroupItemVo vo = itemVoList.get(j);
                DutyTeamVo teamVo = teamVoList.get(j);
                SchedulingVo entity = new SchedulingVo();
                entity.setDutyDate(dutyDate);
                entity.setDutyTeamId(teamVo.getId());
                entity.setDutyTeamName(teamVo.getTeamName());
                entity.setPersonId(vo.getId());
                entity.setPersonName(vo.getItemName());
                List<PersonVo> personInfo = new ArrayList<>(1);
                PersonVo personVo = new PersonVo();
                personVo.setId(vo.getId());
                personVo.setAddrName(vo.getItemName());
                personInfo.add(personVo);
                entity.setPersonInfo(personInfo);
                collect.add(entity);
            }
        }
    }

    public List<SchedulingVo> getSchedulingVoList(List<DutyTeamVo> dutyTeamVoList,List<SchedulingVo> resultList){
        List<SchedulingVo> teamList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dutyTeamVoList)){
            for (DutyTeamVo teamVo : dutyTeamVoList){
                String id = teamVo.getId();
                SchedulingVo entity = new SchedulingVo();
                List<PersonVo> personInfo = new ArrayList<>();
                if (!CollectionUtils.isEmpty(resultList)){
                    resultList = resultList.stream().filter((temp) -> temp.getPersonId() != null).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(resultList)) {
                        for (SchedulingVo schedulingVo : resultList) {
                            String dutyTeamId = schedulingVo.getDutyTeamId();
                            if (id.equals(dutyTeamId)) {
                                entity.setDutyDate(schedulingVo.getDutyDate());
                                entity.setDutyTeamId(id);
                                entity.setDutyTeamName(teamVo.getTeamName());
                                entity.setOrderTeam(teamVo.getOrderTeam());
                                entity.setIsShift(teamVo.getIsShift());
                                entity.setShiftPatternId(schedulingVo.getShiftPatternId());
                                entity.setShiftPatternName(schedulingVo.getShiftPatternName());
                                //添加值班开始、结束时间
                                entity.setStartTime(schedulingVo.getStartTime());
                                entity.setEndTime(schedulingVo.getEndTime());
                                PersonVo personVo = new PersonVo();
                                personVo.setId(schedulingVo.getPersonId());
                                personVo.setAddrName(schedulingVo.getPersonName());
                                personInfo.add(personVo);
                                entity.setPersonInfo(personInfo);
                            }
                        }
                    }
                }
                teamList.add(entity);
            }
        }
        return teamList;
    }

    /**
     * 获取值班模式
     * @param currentDateTime
     * @return
     */
    private SchedulingVo getCaLenSetting(LocalDateTime currentDateTime,String pTypeCode,String orgId){
        LocalDate currentDate = currentDateTime.toLocalDate();
        String dateStr = DateUtil.getDateStr(currentDate);
        ResponseEntity<CalenSettingVo> resultVo = calenSettingClient.findSettingDate(dateStr,orgId);
        CalenSettingVo calenSettingVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        SchedulingVo vo = new SchedulingVo();
        if (null != calenSettingVo) {
            Integer dTypeCode = calenSettingVo.getDateTypeCode();
            vo.setDateTypeCode(dTypeCode);
            vo.setCurrentDateTime(currentDateTime);
            vo.setPtypeCode(pTypeCode);  //按天或班次
        }
        return vo;
    }

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    private LocalDateTime getCurrentTime(){
        LocalDateTime currentDateTime = utilsFeignClient.now().getBody();
        return currentDateTime;
    }

    /**
     * 当前时间 yyyy-MM-dd
     * @return
     */
    private Date getCurrentDate(){
        LocalDateTime currentDateTime = utilsFeignClient.now().getBody();
        String dateStr = DateUtil.getDateStr(currentDateTime);
        Date date = DateUtil.stringToDate(dateStr, "yyyy-MM-dd");
        return date;
    }

    //值班列表数据
    public List<SchedulingsListVo> getList(String date,String orgId,List<String> personIds) {

        //当前天数
        int day = SchedulingUtil.getMonthNumber(date);
        //当月第一天是周几
        List<SchedulingsListVo> schedulingsListVo = new ArrayList<SchedulingsListVo>();

        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        schedulingVo.setDutyDate(date);
        schedulingVo.setDaysNumber(day);
        schedulingVo.setPersonIds(personIds);
        //获取整月数据
        ResponseEntity<List<List<SchedulingVo>>> lists = schedulingClient.findCalList(schedulingVo);
        List<List<SchedulingVo>> listAll = ResponseEntityUtils.achieveResponseEntityBody(lists);

        for (int i = 0; i < listAll.size(); i++) {
            List<SchedulingVo> sd = listAll.get(i);
            SchedulingsListVo ssVo = new SchedulingsListVo();
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
                                //添加历史值班人员id ,name
                                personVo.setHisPersonId(schedulingDay.get(0).getHisPersonId());
                                personVo.setHisPersonName(schedulingDay.get(0).getHisPersonName());
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
                                    //添加历史值班人员id ,name
                                    personVo.setHisPersonId(vos.getHisPersonId());
                                    personVo.setHisPersonName(vos.getHisPersonName());
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
                                    //添加历史值班人员id ,name
                                    personVo.setHisPersonId(schedulingTimes.get(0).getHisPersonId());
                                    personVo.setHisPersonName(schedulingTimes.get(0).getHisPersonName());
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
                                        //添加历史值班人员id ,name
                                        personVo.setHisPersonId(vos.getHisPersonId());
                                        personVo.setHisPersonName(vos.getHisPersonName());
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

    //值排班数据入库
    public List<SchedulingVo> saveSchedulings(SchedulingVo vo){
        int number = SchedulingUtil.getMonthNumber(vo.getMonth());
        CalenSettingVo calenSettingVo = new CalenSettingVo();
        calenSettingVo.setDay(String.valueOf(number));
        calenSettingVo.setOrgId(vo.getOrgId());
        calenSettingVo.setOrgName(vo.getOrgName());
        calenSettingVo.setMonth(vo.getMonth());
        //获取排班整月数据
        ResponseEntity<List<SchedulingVo>> vos = schedulingClient.saveSchedulings(calenSettingVo);
        List<SchedulingVo> voLists = ResponseEntityUtils.achieveResponseEntityBody(vos);
        //保存值班信息整月
//        ResponseEntity<List<SchedulingVo>> schedulingVos = schedulingClient.createBatch(voLists);
//        List<SchedulingVo> schedulings = ResponseEntityUtils.achieveResponseEntityBody(schedulingVos);
        return voLists;
    }

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
                            //添加历史值班人员id ,name
                            personVo.setHisPersonId(schedulingDay.get(0).getHisPersonId());
                            personVo.setHisPersonName(schedulingDay.get(0).getHisPersonName());
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
                                //添加历史值班人员id ,name
                                personVo.setHisPersonId(vos.getHisPersonId());
                                personVo.setHisPersonName(vos.getHisPersonName());
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
                                //添加历史值班人员id ,name
                                personVo.setHisPersonId(schedulingTimes.get(0).getHisPersonId());
                                personVo.setHisPersonName(schedulingTimes.get(0).getHisPersonName());
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
                                    //添加历史值班人员id ,name
                                    personVo.setHisPersonId(vos.getHisPersonId());
                                    personVo.setHisPersonName(vos.getHisPersonName());
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

            ssVo.setSchedulingsForTime(schedulingTimeVo);
            ssVo.setSchedulingsForDay(schedulingsForDay);
        }
        return ssVo;
    }

    /**
     * 获取当天参与交接班的值班人员列表
     */
    public List<SchedulingVo> findCurrentDutys(OAuth2Authentication principal) {
        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        SchedulingVo schedulingVo = new SchedulingVo();
        schedulingVo.setOrgId(orgId);
        String date = DateUtil.getDateStr(new Date());
        schedulingVo.setDutyDate(date);
        ResponseEntity<List<SchedulingVo>> schedulingVos = schedulingClient.findList(schedulingVo);
        List<SchedulingVo> schedulings = ResponseEntityUtils.achieveResponseEntityBody(schedulingVos);
        //去重值班分组id
        List<String> countDutys = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(schedulings)) {
            for (SchedulingVo vos : schedulings) {
                countDutys = schedulings.stream().map(temp -> temp.getDutyTeamId()).collect(Collectors.toList());
            }
        }
        Set set = new HashSet(countDutys);
        countDutys.clear();
        countDutys.addAll(set);
        //查询值班分组判断是否参与交接班
        DutyTeamVo dutyTeamVo = new DutyTeamVo();
        dutyTeamVo.setOrgId(orgId);
        dutyTeamVo.setDutyTeamIds(countDutys);
        ResponseEntity<List<DutyTeamVo>> dutyTeamVos = dutyTeamClient.findDutyList(dutyTeamVo);
        List<DutyTeamVo> dutyTeamVoList = ResponseEntityUtils.achieveResponseEntityBody(dutyTeamVos);
        List<String> numberDutys = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(dutyTeamVoList)){
            for (DutyTeamVo vo:dutyTeamVoList) {
                String isShift = vo.getIsShift();
                if (StringUtils.isNotEmpty(isShift)){
                    if (isShift.equals(SchedulingGlobal.SCHEDULING_IS_SHIFT_YES)){
                        numberDutys.add(vo.getId());
                    }
                }
            }
        }

        SchedulingVo sdVo = new SchedulingVo();
        sdVo.setOrgId(orgId);
        sdVo.setDutyTeamIds(numberDutys);
        sdVo.setDutyDate(date);
        ResponseEntity<List<SchedulingVo>> sdVos = schedulingClient.findList(sdVo);
        List<SchedulingVo> sdVoLists = ResponseEntityUtils.achieveResponseEntityBody(sdVos);
        List<SchedulingVo> voLists = new ArrayList<SchedulingVo>();
        for (SchedulingVo sdVoList:sdVoLists) {
            String p = sdVoList.getPersonId();
            if (StringUtils.isNotEmpty(p)){
                sdVoList.setPersonId(p);
                voLists.add(sdVoList);
            }
        }
        //无班次人员查询字典表
        if (!CollectionUtils.isEmpty(voLists)){
            return voLists;
        }else{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("dicCode", SchedulingGlobal.ITEM_DIC_CODE);
            ResponseEntity<List<DicGroupItemVo>> list = dicItemClient.findList(params);
            List<DicGroupItemVo> dicGroupItemVos = ResponseEntityUtils.achieveResponseEntityBody(list);
            //升序
            List<DicGroupItemVo> itemVoList = dicGroupItemVos.stream()
                    .sorted(Comparator.comparing(DicGroupItemVo::getOrders))
                    .collect(Collectors.toList());
            for (DicGroupItemVo item:itemVoList) {
                SchedulingVo vos = new SchedulingVo();
                vos.setShiftPatternName(SchedulingGlobal.SHIFT_PATTERN_NAME);
                vos.setPersonId(item.getId());
                vos.setPersonName(item.getItemName());
                voLists.add(vos);
            }
            return voLists;
        }
    }

    /**
     * 获取当天与下一天参与交接班的值班人员列表
     */
    public List<SchedulingVo> findNextDutys(OAuth2Authentication principal) {
        //获取当前单位
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String orgId = userMap.get("orgId").toString(); //创建单位id
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findNextDutys(orgId);
        List<SchedulingVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        //去重值班分组id
        List<String> countDuty = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(voList)) {
            for (SchedulingVo vos : voList) {
                countDuty = voList.stream().map(temp -> temp.getDutyTeamId()).collect(Collectors.toList());
            }
        }
        Set set = new HashSet(countDuty);
        countDuty.clear();
        countDuty.addAll(set);
        //查询值班分组判断是否参与交接班
        DutyTeamVo dutyTeamVo = new DutyTeamVo();
        dutyTeamVo.setOrgId(orgId);
        dutyTeamVo.setDutyTeamIds(countDuty);
        ResponseEntity<List<DutyTeamVo>> dutyTeamVos = dutyTeamClient.findDutyList(dutyTeamVo);
        List<DutyTeamVo> dutyTeamVoList = ResponseEntityUtils.achieveResponseEntityBody(dutyTeamVos);
        List<String> numberDutys = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(dutyTeamVoList)){
            for (DutyTeamVo vo:dutyTeamVoList) {
                String isShift = vo.getIsShift();
                if (StringUtils.isNotEmpty(isShift)){
                    if (isShift.equals(SchedulingGlobal.SCHEDULING_IS_SHIFT_YES)){
                        numberDutys.add(vo.getId());
                    }
                }
            }
        }

        SchedulingVo sdVo = new SchedulingVo();
        String date = DateUtil.getDateStr(new Date());
        String nextDay = DateUtil.addDay(date,DateUtil.FORMAT_DAY,1);
        sdVo.setOrgId(orgId);
        sdVo.setDutyTeamIds(numberDutys);
        sdVo.setCurrentDutyDate(date);
        sdVo.setNextDutyDate(nextDay);
        ResponseEntity<List<SchedulingVo>> sdVos = schedulingClient.findList(sdVo);
        List<SchedulingVo> sdVoLists = ResponseEntityUtils.achieveResponseEntityBody(sdVos);
        List<SchedulingVo> voLists = new ArrayList<SchedulingVo>();
        for (SchedulingVo sdVoList:sdVoLists) {
            String p = sdVoList.getPersonId();
            if (StringUtils.isNotEmpty(p)){
                sdVoList.setPersonId(p);
                voLists.add(sdVoList);
            }
        }
        return voLists;
    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    public List<SchedulingCountVo> findListCondition(SchedulingVo vo) {
        List<Integer> sortList = Arrays.asList(1,2,3,4,5);
        //组装的数据
        List<SchedulingCountVo> voList = new ArrayList<>();
        ResponseEntity<List<SchedulingVo>> list = schedulingClient.findListCondition(vo);
        List<SchedulingVo> listVo = ResponseEntityUtils.achieveResponseEntityBody(list);
        //去重personId和personName
        List<SchedulingVo> listNew = new ArrayList<SchedulingVo>();
        Set set=new HashSet();
        if (!CollectionUtils.isEmpty(listVo)) {
            for (SchedulingVo schedulingVo : listVo) {
                if (null != schedulingVo.getPersonId()) {
                    if (set.add(schedulingVo.getPersonId())) {
                        listNew.add(schedulingVo);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(listNew)) {
            for (SchedulingVo voNew : listNew) {
                SchedulingCountVo countVo = new SchedulingCountVo();
                List<SchedulingVo> data = new ArrayList<>();
                Integer total = 0;
                String personId = voNew.getPersonId();
                countVo.setPersonId(personId);
                countVo.setPersonName(voNew.getPersonName());
                //当前某个人的dateTypeCode集合
                List<Integer> dateTypeCodeList = new ArrayList<>();
                for (SchedulingVo schedulingVo : listVo) {
                    if (personId.equals(schedulingVo.getPersonId())){
                        dateTypeCodeList.add(schedulingVo.getDateTypeCode());
                    }
                }
                //去重后的dateTypeCode集合
                List<Integer> dateTypeCodeNew = new ArrayList<>();
                Set setList=new HashSet();
                if (!CollectionUtils.isEmpty(dateTypeCodeList)){
                    for (Integer dateTypeCode : dateTypeCodeList){
                        if (setList.add(dateTypeCode)) {
                            dateTypeCodeNew.add(dateTypeCode);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(dateTypeCodeNew)){
                    for (Integer newEntity : dateTypeCodeNew){
                        Integer temp = 0;
                        SchedulingVo schedulingVo = new SchedulingVo();
                        schedulingVo.setDateTypeCode(newEntity);
                        for (Integer oldEntity : dateTypeCodeList){
                            if (newEntity.equals(oldEntity)){
                                temp++;
                            }
                        }
                        schedulingVo.setNumber(temp.longValue());
                        total += temp;
                        data.add(schedulingVo);
                    }
                    List<Integer> differentList = getDiffrentList(dateTypeCodeNew, sortList);
                    for (Integer none : differentList){
                        SchedulingVo schedulingVo = new SchedulingVo();
                        schedulingVo.setDateTypeCode(none);
                        schedulingVo.setNumber(0L);
                        data.add(schedulingVo);
                    }
                }
                //排序
                List<SchedulingVo> listSort = data.stream()
                        .sorted(Comparator.comparing(SchedulingVo::getDateTypeCode,(x,y) -> {
                            if(x == null && y != null){
                                return 1;
                            }else if(x !=null && y == null){
                                return -1;
                            }else if(x == null && y == null){
                                return -1;
                            }else{
                                //按照读取的list顺序排序
                                for(Integer sort : sortList){
                                    if(sort.equals(x) || sort.equals(y)){
                                        if(x.equals(y)){
                                            return 0;
                                        }else if(sort.equals(x)){
                                            return -1;
                                        }else{
                                            return 1;
                                        }
                                    }
                                }
                                return 0;
                            }
                        }))
                        .collect(Collectors.toList());
                countVo.setData(listSort);
                countVo.setTotal(total);
                voList.add(countVo);
            }
        }
        return voList;
    }

    public List<Integer> getDiffrentList(List<Integer> list1,List<Integer> list2){
        Map<Integer,Integer> map = new HashMap<Integer,Integer>(list1.size()+list2.size());
        List<Integer> diff = new ArrayList<Integer>();
        List<Integer> maxList = list1;
        List<Integer> minList = list2;
        if(list2.size()>list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        for (Integer string : maxList)
        {
            map.put(string, 1);
        }
        for (Integer string : minList)
        {
            Integer cc = map.get(string);
            if(cc!=null)
            {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for(Map.Entry<Integer, Integer> entry:map.entrySet())
        {
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    private String getOrgId(Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo profileVo = userVo.getProfile();
        String orgId = profileVo.getOrgId();
        return orgId;
    }

    /**
     * 根据日期获取当天的集团及各板块的值班人员列表，包括领导和值班员等，供浙能首页使用
     *  此接口的yyyy-MM-dd HH:mm:SS全是yyyy-MM-dd HH:mm
     * @param vo
     * @return
     */
    public List<CalSchedulingForOrg> getAllDutysByDate(SearchAllDutyVo vo) {
        List<CalSchedulingForOrg> resultList = new ArrayList<>();
        //人员ID、名字、手机号
        ResponseEntity<List<DutyMan>> sdVos = schedulingClient.getAllDutysByDate(vo);
        List<DutyMan> voList = ResponseEntityUtils.achieveResponseEntityBody(sdVos);
        if (!CollectionUtils.isEmpty(voList)){
            List<String> orgIds = vo.getOrgIds();
            ResponseEntity<List<OrgVo>> voOrg = orgClient.findOrgInfo(orgIds);
            List<OrgVo> orgVoList = ResponseEntityUtils.achieveResponseEntityBody(voOrg);
            for (OrgVo orgVo : orgVoList){
                String id = orgVo.getId();
                CalSchedulingForOrg calSchedulingForOrg = new CalSchedulingForOrg();
                List<DutyMan> list = new ArrayList<>();//存当前部门下的值班人员
                for (DutyMan dutyMan : voList) {
                    String orgId = dutyMan.getOrgId();
                    if (id.equals(orgId)) {
                        list.add(dutyMan);
                    }
                }
                calSchedulingForOrg.setOrgId(id);
                calSchedulingForOrg.setOrgName(orgVo.getOrgName());
                calSchedulingForOrg.setDutyManList(list);
                resultList.add(calSchedulingForOrg);
            }
        }
        return resultList;
    }


    private void forSchedulings( List<List<SchedulingVo>> listAll,List<SchedulingSearchVo> searchVoList){
        for (int i = 0; i < listAll.size(); i++) {
            List<SchedulingVo> schedulings = listAll.get(i);
            //按需拼接前台数据
            searchVoList.add(packMain(schedulings));
        }
    }
    private SchedulingSearchVo packMain(List<SchedulingVo> schedulings){
        SchedulingSearchVo ssVo = new SchedulingSearchVo();
        SchedulingVo sd = new SchedulingVo();
        if (!CollectionUtils.isEmpty(schedulings)){
            sd = schedulings.get(0);
        }
        ssVo.setDutyDate(sd.getDutyDate());
        ssVo.setDateTypeCode(sd.getDateTypeCode());
        String name = SchedulingUtil.getName(sd.getDateTypeCode());
        ssVo.setDateTypeName(name);
        ssVo.setHolidayName(sd.getHolidayName());
        String days = sd.getDutyDate();
        days = days.substring(days.length() - 2, days.length());
        ssVo.setDay(days);
        Date d = DateUtil.stringToDate(sd.getDutyDate(), "yyyy-MM-dd");
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
                        //添加历史值班人员id ,name
                        personVo.setHisPersonId(schedulingDay.get(0).getHisPersonId());
                        personVo.setHisPersonName(schedulingDay.get(0).getHisPersonName());
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
                            //添加历史值班人员id ,name
                            personVo.setHisPersonId(vos.getHisPersonId());
                            personVo.setHisPersonName(vos.getHisPersonName());
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
        List<String> numberShiftsOne = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(forTime)){
            for (SchedulingVo vos:forTime) {
                numberShiftsOne = forTime.stream().map(temp -> temp.getShiftPatternId()).collect(Collectors.toList());
            }
        }
        Set h = new HashSet(numberShiftsOne);
        numberShiftsOne.clear();
        numberShiftsOne.addAll(h);


        //根据去重后的班次个数创建分组list
        Map<String,List<SchedulingVo>> mapShift = new HashMap<String,List<SchedulingVo>>();
        for (String numberShift:numberShiftsOne) {
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
            List<String> shiftsDutyOne = new ArrayList<String>();
            if (!CollectionUtils.isEmpty(schedulingShift)) {
                for (SchedulingVo vos : schedulingShift) {
                    shiftsDutyOne = schedulingShift.stream().map(temp -> temp.getDutyTeamId()).collect(Collectors.toList());
                }
            }
            Set set = new HashSet(shiftsDutyOne);
            shiftsDutyOne.clear();
            shiftsDutyOne.addAll(set);

            //创建同一班次下不同的分组list
            Map<String, List<SchedulingVo>> sdMap = new HashMap<String, List<SchedulingVo>>();
            for (String num : shiftsDutyOne) {
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
                            //添加历史值班人员id ,name
                            personVo.setHisPersonId(schedulingTimes.get(0).getHisPersonId());
                            personVo.setHisPersonName(schedulingTimes.get(0).getHisPersonName());
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
                                //添加历史值班人员id ,name
                                personVo.setHisPersonId(vos.getHisPersonId());
                                personVo.setHisPersonName(vos.getHisPersonName());
                                personInfo.add(personVo);
                            }
                        }
                        sdVos.setPersonInfo(personInfo);
                        schedulingDetail.add(sdVos);
                    }
                }
            }

            //排序schedulingTimeVo
            schedulingDetail.sort(new Comparator<SchedulingVo>() {
                @Override
                public int compare(SchedulingVo o1, SchedulingVo o2) {
                    try {
                        return Integer.valueOf(o1.getOrderTeam()) - Integer.valueOf(o2.getOrderTeam());
                    } catch (Exception var3) {
                        return 0;
                    }
                }
            });

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
        return ssVo;
    }
    private void appendSchedulingSearchVo(String date,List<SchedulingSearchVo> searchVoList,int day,int week){
        String str = date + "-"+ String.valueOf(day);
        Date time = DateUtil.stringToDate(str,"yyyy-MM-dd");
        String weekDay = DateUtil.getWeek(time);
        if (!weekDay.equals("星期六")) {
            day = day - (7 - week);
            int add = day % 7;
            int addDay = 7 - add;
            for (int i = 1; i <= addDay; i++) {
                searchVoList.add(new SchedulingSearchVo());
            }
        }
    }


    /**
     * 根据查询条件导出值班列表Excel
     * @param vo
     */
    public void exportToExcelForSchedulings(OutputStream os, SchedulingVo vo,OAuth2Authentication principal)
            throws Exception{
        List<SchedulingsListVo> list = findListSchedulings(vo,principal);
        String fileName = SchedulingGlobal.SCHEDULING_FILE_NAME;
        String month = vo.getMonth();
        if (!StringUtils.isBlank(month)){
            String[] split = month.split("-");
            fileName = split[0] + "年" + split[1] + "月"+fileName;
        }
        processScheduling(os,fileName,list,vo.getOrgId());

    }

    /**
     * 导出EXCEL文件
     * @param os
     * @param fileName
     * @param list
     * @throws IOException
     * @throws IndexOutOfBoundsException
     */
    private void processScheduling(OutputStream os,String fileName,List<SchedulingsListVo> list,String orgId)
            throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);

        //1、标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);//字体大小；
        font.setFontName("宋体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setFillForegroundColor(HSSFColor.BROWN.index);
        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setFont(font);

        //2、标题
        HSSFRow titleRow = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 6));//合并单元格
        HSSFCell cell = titleRow.createCell(0);//第一个单元格
        cell.setCellStyle(titleStyle);
        cell.setCellValue(fileName);

        //表头样式
        HSSFCellStyle colStyle = workbook.createCellStyle();
        Font colFont = workbook.createFont();
        colFont.setFontHeightInPoints((short) 14);//字体大小；
        colFont.setFontName("宋体");
        colStyle.setFont(colFont);
        colStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);


        //3、表头
        HSSFRow tableRow = sheet.createRow(1);
//        sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, 0, (short) 0));//合并单元格
//        sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 1, (short) 3));//合并单元格
//        sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 4, (short) 6));//合并单元格
//        sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, 7, (short) 7));//合并单元格


        HSSFCell blankCell0 = tableRow.createCell(0);
        blankCell0.setCellStyle(colStyle);
        blankCell0.setCellValue("序号");


        HSSFCell blankCell1 = tableRow.createCell(1);
        blankCell1.setCellStyle(colStyle);
        blankCell1.setCellValue("日期");

        HSSFCell blankCell2 = tableRow.createCell(2);
        blankCell2.setCellStyle(colStyle);
        blankCell2.setCellValue("星期");

        HSSFCell blankCell3 = tableRow.createCell(3);
        blankCell3.setCellStyle(colStyle);
        blankCell3.setCellValue("日期类型");

        HSSFCell blankCell4 = tableRow.createCell(4);
        blankCell4.setCellStyle(colStyle);
        blankCell4.setCellValue("带班领导");

        HSSFCell blankCell5 = tableRow.createCell(5);
        blankCell5.setCellStyle(colStyle);
        blankCell5.setCellValue("值班领导");

        HSSFCell blankCell6 = tableRow.createCell(6);
        blankCell6.setCellStyle(colStyle);
        blankCell6.setCellValue("值班干部");

        //数据题样式
        HSSFCellStyle dataStyle = workbook.createCellStyle();
        HSSFCellStyle dataStyle1 = workbook.createCellStyle();
//        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        dataStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        for(int i = 0 ;i< 7;i++){
            sheet.setColumnWidth(i,5000);
        }
        String next = "\r\n";
        int rowData = 2;
        String  dutyTeamName = null;
        String dutyTeamLeader = null;
        String dutyTeamCadre = null;
        //获取分组信息
        ResponseEntity<List<DutyTeamVo>> dutyTeamVos = dutyTeamClient.findList(orgId);
        List<DutyTeamVo> dutyTeamVoList = ResponseEntityUtils.achieveResponseEntityBody(dutyTeamVos);
        if (!CollectionUtils.isEmpty(dutyTeamVoList)){
            dutyTeamName =dutyTeamVoList.get(0).getTeamName();
            dutyTeamLeader =dutyTeamVoList.get(1).getTeamName();
            dutyTeamCadre =dutyTeamVoList.get(2).getTeamName();
        }
        final String  dutyTeamNamef = dutyTeamName;
        final String  dutyTeamLeaderf = dutyTeamLeader;
        final String  dutyTeamCadref = dutyTeamCadre;
        for (int i = 0 ;i< list.size();i++){

            SchedulingsListVo schedulingsListVo = list.get(i);

            HSSFRow row = sheet.createRow(rowData+i);
            //设置居中
            row.setRowStyle(dataStyle);

            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(i+1);//序号
            row0.setCellStyle(dataStyle);

            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(schedulingsListVo.getDutyDate());//日期
            row1.setCellStyle(dataStyle);

            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(schedulingsListVo.getWeekDay());//星期
            row2.setCellStyle(dataStyle);

            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(schedulingsListVo.getDateTypeName());//日期类型
            row3.setCellStyle(dataStyle);

            List<SchedulingVo> schedulings = schedulingsListVo.getSchedulings();
            List<SchedulingVo> collect1 = schedulings.stream().filter(schedulingVo -> dutyTeamNamef.equals(schedulingVo.getDutyTeamName())).collect(Collectors.toList());
            List<SchedulingVo> collect2 = schedulings.stream().filter(schedulingVo -> dutyTeamLeaderf.equals(schedulingVo.getDutyTeamName())).collect(Collectors.toList());
            List<SchedulingVo> collect3 = schedulings.stream().filter(schedulingVo -> dutyTeamCadref.equals(schedulingVo.getDutyTeamName())).collect(Collectors.toList());

            HSSFCell row4 = row.createCell(4);

            if (!CollectionUtils.isEmpty(collect1)){

                String hisPersonId = collect1.get(0).getHisPersonId();
                if (!StringUtils.isBlank(hisPersonId)){
                    row4.setCellValue(collect1.get(0).getPersonName()+"（换："+collect1.get(0).getHisPersonName()+")");//带班领导
                }else{
                    row4.setCellValue(collect1.get(0).getPersonName());//带班领导
                }
                row4.setCellStyle(dataStyle);
            }

            HSSFCell row5 = row.createCell(5);
            if (!CollectionUtils.isEmpty(collect2)){
                dataStyle1.setWrapText(true);
                row5.setCellStyle(dataStyle1);
                if (collect2.size()==1){
                    String hisPersonId = collect2.get(0).getHisPersonId();
                    if (!StringUtils.isBlank(hisPersonId)){
                        row5.setCellValue(collect2.get(0).getShiftPatternName()+"："+collect2.get(0).getPersonName()+"（换："+collect2.get(0).getHisPersonName()+")");//值班领导
                    }else{
                        row5.setCellValue(collect2.get(0).getShiftPatternName()+"："+collect2.get(0).getPersonName());//值班领导
                    }

                }else{
                    String hisPersonId1 = collect2.get(0).getHisPersonId();
                    String hisPersonId2 = collect2.get(1).getHisPersonId();
                    String str =null;
                    String str1 =null;
                    if (StringUtils.isBlank(hisPersonId1) && !StringUtils.isBlank(hisPersonId2)){
                        str = collect2.get(0).getShiftPatternName()+"："+collect2.get(0).getPersonName();
                        str1 = collect2.get(1).getShiftPatternName()+"："+collect2.get(1).getPersonName()+"（换："+collect2.get(1).getHisPersonName()+")";
                    }else if(StringUtils.isBlank(hisPersonId1) && StringUtils.isBlank(hisPersonId2)){
                        str = collect2.get(0).getShiftPatternName()+"："+collect2.get(0).getPersonName();
                        str1 = collect2.get(1).getShiftPatternName()+"："+collect2.get(1).getPersonName();
                    }else if (StringUtils.isBlank(hisPersonId2) && !StringUtils.isBlank(hisPersonId1)){
                        str = collect2.get(0).getShiftPatternName()+"："+collect2.get(0).getPersonName()+"（换："+collect2.get(0).getHisPersonName()+")";
                        str1 = collect2.get(1).getShiftPatternName()+"："+collect2.get(1).getPersonName();
                    }else if (!StringUtils.isBlank(hisPersonId2) && !StringUtils.isBlank(hisPersonId1)){
                        str = collect2.get(0).getShiftPatternName()+"："+collect2.get(0).getPersonName()+"（换："+collect2.get(0).getHisPersonName()+")";
                        str1 = collect2.get(1).getShiftPatternName()+"："+collect2.get(1).getPersonName()+"（换："+collect2.get(1).getHisPersonName()+")";
                    }
                    row5.setCellValue(new HSSFRichTextString(str+next+str1));//值班领导
                }

            }

            HSSFCell row6 = row.createCell(6);
            if (!CollectionUtils.isEmpty(collect3)){
                dataStyle1.setWrapText(true);
                row6.setCellStyle(dataStyle1);
                if (collect3.size()==1){
                    String hisPersonId = collect3.get(0).getHisPersonId();
                    if (!StringUtils.isBlank(hisPersonId)){
                        row6.setCellValue(collect3.get(0).getShiftPatternName()+"："+collect3.get(0).getPersonName()+"（换："+collect3.get(0).getHisPersonName()+")");//值班干部
                    }else{
                        row6.setCellValue(collect3.get(0).getShiftPatternName()+"："+collect3.get(0).getPersonName());//值班干部
                    }
                }else{
                    String hisPersonId1 = collect3.get(0).getHisPersonId();
                    String hisPersonId2 = collect3.get(1).getHisPersonId();
                    String str =null;
                    String str1 =null;
                    if (StringUtils.isBlank(hisPersonId1) && !StringUtils.isBlank(hisPersonId2)){
                        str = collect3.get(0).getShiftPatternName()+"："+collect3.get(0).getPersonName();
                        str1 = collect3.get(1).getShiftPatternName()+"："+collect3.get(1).getPersonName()+"（换："+collect3.get(1).getHisPersonName()+")";
                    }else if(StringUtils.isBlank(hisPersonId1) && StringUtils.isBlank(hisPersonId2)){
                        str = collect3.get(0).getShiftPatternName()+"："+collect3.get(0).getPersonName();
                        str1 = collect3.get(1).getShiftPatternName()+"："+collect3.get(1).getPersonName();
                    }else if (StringUtils.isBlank(hisPersonId2) && !StringUtils.isBlank(hisPersonId1)){
                        str = collect3.get(0).getShiftPatternName()+"："+collect3.get(0).getPersonName()+"（换："+collect3.get(0).getHisPersonName()+")";
                        str1 = collect3.get(1).getShiftPatternName()+"："+collect3.get(1).getPersonName();
                    }else if (!StringUtils.isBlank(hisPersonId2) && !StringUtils.isBlank(hisPersonId1)){
                        str = collect3.get(0).getShiftPatternName()+"："+collect3.get(0).getPersonName()+"（换："+collect3.get(0).getHisPersonName()+")";
                        str1 = collect3.get(1).getShiftPatternName()+"："+collect3.get(1).getPersonName()+"（换："+collect3.get(1).getHisPersonName()+")";
                    }
                    row6.setCellValue(new HSSFRichTextString(str+next+str1));//值班领导
                }
            }
        }
        tableRowSpan(sheet,2,4,workbook,4);
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * POI 表格合并根据值相同进行合并
     * @param t:开始合并行数
     * @param cell:需要合并的列
     * @param cellValue:需要比较的值在哪一列
     */
    public static void tableRowSpan( HSSFSheet sheet,int t,int cell,Workbook workbook,int cellValue){
        String deptNameCell = sheet.getRow(3).getCell(cellValue).getStringCellValue();
        int r = 1;
        for(int i=t+1;i<sheet.getPhysicalNumberOfRows();i++){
            HSSFRow row = sheet.getRow(i);
            String deptNameCellNow =row.getCell(cellValue).getStringCellValue();
            if(deptNameCell.equals(deptNameCellNow)){
                r++;
            }else {
                CellRangeAddress address1 = new CellRangeAddress(t, (short) (t + r - 1), cell, (short) cell);
                sheet.addMergedRegion(address1);
                //setRegionBorder(1,address1,sheet,workbook);
                deptNameCell = deptNameCellNow;
                t=t+r;
                r=1;
            }
            if(i==(sheet.getPhysicalNumberOfRows()-1)){
                CellRangeAddress address1 = new CellRangeAddress(t, (short) (t + r - 1), cell, (short) cell);
                sheet.addMergedRegion(address1);
                //setRegionBorder(1,address1,sheet,workbook);
            }
        }
    }


    public static void setRegionBorder(int border, CellRangeAddress address, HSSFSheet sheet, Workbook wb){
        RegionUtil.setBorderBottom(border,address,sheet,wb);
        RegionUtil.setBorderLeft(border,address,sheet,wb);
        RegionUtil.setBorderRight(border,address,sheet,wb);
        RegionUtil.setBorderTop(border,address,sheet,wb);
    }





    /**
     * 根据查询条件导出专家信息Excel
     * @param voList
     */
    public void exportToExcel(OutputStream os, List<SchedulingCountVo> voList,SchedulingVo vo)
            throws IOException,IndexOutOfBoundsException{
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        String fileName = startTime + " 至 " + endTime +" 值班统计";
        process(os,fileName,voList);

    }


    private void process(OutputStream os,String fileName,List<SchedulingCountVo> list)
            throws IOException,IndexOutOfBoundsException{
        String array [] = {"序号","姓名","工作日","双休日","法定节假日","特殊节假日","其他","合计"};
        int rowNum = 0;//初始行号
        //1、创建EXCEL文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2、创建一个Excel表单
        HSSFSheet sheet = workbook.createSheet(fileName);
        //3、创建第一行
        HSSFRow headerRow = sheet.createRow(rowNum);
        int lastCol = array.length - 1;
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) lastCol));//合并单元格
        HSSFCell cell = headerRow.createCell(0);//第一个单元格
        cell.setCellStyle(titleStyle(workbook));//设置样式
        cell.setCellValue(fileName);

        //4、表头
        HSSFRow tableRow = sheet.createRow(rowNum+=1);
        HSSFCell blankCell0 = tableRow.createCell(0);
        blankCell0.setCellStyle(colStyle(workbook));
        blankCell0.setCellValue(array[0]);

        HSSFCell blankCell1 = tableRow.createCell(1);
        blankCell1.setCellStyle(colStyle(workbook));
        blankCell1.setCellValue(array[1]);

        HSSFCell blankCell2 = tableRow.createCell(2);
        blankCell2.setCellStyle(colStyle(workbook));
        blankCell2.setCellValue(array[2]);

        HSSFCell blankCell3 = tableRow.createCell(3);
        blankCell3.setCellStyle(colStyle(workbook));
        blankCell3.setCellValue(array[3]);

        HSSFCell blankCell4 = tableRow.createCell(4);
        blankCell4.setCellStyle(colStyle(workbook));
        blankCell4.setCellValue(array[4]);

        HSSFCell blankCell5 = tableRow.createCell(5);
        blankCell5.setCellStyle(colStyle(workbook));
        blankCell5.setCellValue(array[5]);

        HSSFCell blankCell6 = tableRow.createCell(6);
        blankCell6.setCellStyle(colStyle(workbook));
        blankCell6.setCellValue(array[6]);

        HSSFCell blankCell7 = tableRow.createCell(7);
        blankCell7.setCellStyle(colStyle(workbook));
        blankCell7.setCellValue(array[7]);
        //设置每列数据的宽度
        for(int i = 0 ;i< array.length;i++){
            sheet.setColumnWidth(i,5000);
        }

        for (int jj = 0; jj<list.size(); jj++){
            SchedulingCountVo vo = list.get(jj);
            List<SchedulingVo> data = vo.getData();
            Long gongzuo = 0l;
            Long shaung = 0l;
            Long fading = 0l;
            Long teshu = 0l;
            Long qita =0l;
            for (int i=0;i<data.size();i++) {
                SchedulingVo schedulingVo = data.get(i);
                Integer dateTypeCode = schedulingVo.getDateTypeCode();
                switch (dateTypeCode){
                    case 1:
                        gongzuo = schedulingVo.getNumber();
                        break;
                    case 2:
                        shaung = schedulingVo.getNumber();
                        break;
                    case 3:
                        fading = schedulingVo.getNumber();
                        break;
                    case 4:
                        teshu = schedulingVo.getNumber();
                        break;
                    case 5:
                        qita = schedulingVo.getNumber();
                        break;
                }
            }
            HSSFRow row = sheet.createRow(jj+rowNum+1);
            HSSFCell row0 = row.createCell(0);
            row0.setCellValue(jj+1);//序号
            row0.setCellStyle(dataStyle(workbook));
            HSSFCell row1 = row.createCell(1);
            row1.setCellValue(vo.getPersonName());//姓名
            row1.setCellStyle(dataStyle(workbook));
            HSSFCell row2 = row.createCell(2);
            row2.setCellValue(gongzuo);//工作单位
            row2.setCellStyle(dataStyle(workbook));
            HSSFCell row3 = row.createCell(3);
            row3.setCellValue(shaung);
            row3.setCellStyle(dataStyle(workbook));
            HSSFCell row4 = row.createCell(4);
            row4.setCellValue(fading);
            row4.setCellStyle(dataStyle(workbook));
            HSSFCell row5 = row.createCell(5);
            row5.setCellValue(teshu);
            row5.setCellStyle(dataStyle(workbook));
            HSSFCell row6 = row.createCell(6);
            row6.setCellValue(qita);
            row6.setCellStyle(dataStyle(workbook));
            HSSFCell row7 = row.createCell(7);
            row7.setCellValue(vo.getTotal());
            row7.setCellStyle(dataStyle(workbook));
        }
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 获取当前班次人员的签入状态
     */
    public String getSigninStatus(String shiftFlag,Principal principal){
        //获取当前单位
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //String orgId = userProfileVo.getOrgId();
        String username = userProfileVo.getName();
        List<SchedulingVo> currentDutyPersons = new ArrayList<SchedulingVo>();
        //获取当前时间的值班信息值班人员
        if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_CURRENT)){
            currentDutyPersons = findCurrentDutyPersons(principal, shiftFlag);
        }else if(shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_PREVIOUS)){
            currentDutyPersons = findPrevDutyPersons(principal, shiftFlag);
        }else if (shiftFlag.equals(SchedulingGlobal.INFO_SHIFT_NEXT)){
            currentDutyPersons = findNextDutyPersons(principal,shiftFlag);
        }

        //去除带班领导
        List<SchedulingVo> collect1 = currentDutyPersons.stream().filter(schedulingVo -> !"0".equals(schedulingVo.getIsShift())).collect(Collectors.toList());
        List<String> personIds = new ArrayList<String>();
        //获取班次开始时间、结束时间
        String shiftStartTime = null;
        //遍历获取值班人员信息
        for (SchedulingVo collect:collect1) {
            shiftStartTime = collect.getStartTime();
            List<PersonVo> personInfo = collect.getPersonInfo();
            for (PersonVo personVo:personInfo) {
                String s = personVo.getId();
                personIds.add(s);
            }
        }
        //添加45分钟限制
        String startTime = null;
        String endTime = null;
        LocalDateTime localDateStartTime = null;
        LocalDateTime localDateEndTime = null;
        Integer rangeTime = Integer.valueOf(SchedulingGlobal.SHIFT_TIME);

        if (!StringUtils.isBlank(shiftStartTime)){
            startTime = DateUtil.addMinutes(shiftStartTime, -rangeTime);
            endTime = DateUtil.addMinutes(shiftStartTime, rangeTime);
            localDateStartTime = DateUtil.strToLocalDateTime(startTime);
            localDateEndTime = DateUtil.strToLocalDateTime(endTime);
        }

        //获取当前班次人员的签入状态
        SigninListVo signinListVo = new SigninListVo();
        signinListVo.setStartTime(localDateStartTime);
        signinListVo.setEndTime(localDateEndTime);
        signinListVo.setDutyPersonIds(personIds);
        signinListVo.setFlag("0");
        signinListVo.setUserName(username);
        ResponseEntity<List<SigninVo>>resultVo = signinClient.findList(signinListVo);
        List<SigninVo> signinVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        String status = null;
        if(signinVo.size() == personIds.size()){
            //已全部签到
            status = "0";
        }else{
            //未全部签到
            status = "1";
        }
        return status;
    }


}