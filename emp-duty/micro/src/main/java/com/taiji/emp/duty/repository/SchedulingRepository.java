package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.entity.QScheduling;
import com.taiji.emp.duty.entity.Scheduling;
import com.taiji.emp.duty.mapper.SchedulingMapper;
import com.taiji.emp.duty.searchVo.DutyMan;
import com.taiji.emp.duty.searchVo.SearchAllDutyVo;
import com.taiji.emp.duty.vo.SchedulingSearchVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SchedulingRepository extends BaseJpaRepository<Scheduling,String> {

    @Autowired
    SchedulingMapper mapper;

    @Autowired
    SqlFormat sqlFormat;

    @Value("${spring.datasource.platform}")
    private String platform;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    protected EntityManager em;


    @Override
    @Transactional
    public Scheduling save(Scheduling entity) {
        Assert.notNull(entity,"entity不能为空");
        Scheduling result;
        entity.setNumber(0L);
        result = super.save(entity);
        /*if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            Scheduling temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }*/
        return result;
    }

    @Transactional
    public Scheduling updateScheduling(Scheduling entity) {
        Assert.notNull(entity,"entity不能为空");
        Scheduling result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            Scheduling temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 清除旧数据
     * @param entity
     */
    @Override
    @Transactional
    public void delete(Scheduling entity) {
        QScheduling scheduling = QScheduling.scheduling;
        BooleanBuilder builder = new BooleanBuilder();
        JPADeleteClause deleteClause = querydslDelete(scheduling);
        if (StringUtils.hasText(entity.getShiftPatternId())){
            builder.and(scheduling.shiftPatternId.eq(entity.getShiftPatternId()));
        }
        builder.and(scheduling.dutyDate.eq(entity.getDutyDate()));
        if (StringUtils.hasText(entity.getDutyTeamId().getId())){
            builder.and(scheduling.dutyTeamId.id.eq(entity.getDutyTeamId().id));
        }
        if (StringUtils.hasText(entity.getPtypeCode())){
            builder.and(scheduling.ptypeCode.eq(entity.getPtypeCode()));
        }
        deleteClause.where(builder).execute();
    }

    /**
     * 根据条件查询获取单个班次的值班人员列表
     * @param vo
     * @return
     */
    public List<Scheduling> findList(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String orgId = vo.getOrgId();
        String month = vo.getMonth();
        String dutyDate = vo.getDutyDate();
        String nextDutyDate = vo.getNextDutyDate();
        String currentDutyDate = vo.getCurrentDutyDate();
        LocalDate date = DateUtil.strToLocalDate(dutyDate);
        String dutyTeamId = vo.getDutyTeamId();
        String shiftPattId = vo.getShiftPatternId();
        List<String> personIds = vo.getPersonIds();
        String pTypeCode = vo.getPtypeCode();
        List<String> dutyTeamIds = vo.getDutyTeamIds();

        String personName = vo.getPersonName();
        String personId = vo.getPersonId();
        String shiftPatternName = vo.getShiftPatternName();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

        if (StringUtils.hasText(pTypeCode)){
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }

        if (StringUtils.hasText(month)){
            //开始时间
            String endMonth = month;
            month = month + "-01";
            //结束时间
            int number = getMonthNumber(month);
            String num = String.valueOf(number);
            num = endMonth + "-"+ num;
            LocalDate start = DateUtil.strToLocalDate(month);
            LocalDate end = DateUtil.strToLocalDate(num);
            builder.and(scheduling.dutyDate.between(start,end));
        }

        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.id.eq(dutyTeamId));
        }

        if (StringUtils.hasText(shiftPattId)){
            builder.and(scheduling.shiftPatternId.eq(shiftPattId));
        }

        if (StringUtils.hasText(dutyDate)){
            builder.and(scheduling.dutyDate.eq(date));
        }

        if (StringUtils.hasText(currentDutyDate) && StringUtils.hasText(nextDutyDate)){
            LocalDate start = DateUtil.strToLocalDate(currentDutyDate);
            LocalDate end = DateUtil.strToLocalDate(nextDutyDate);
            builder.and(scheduling.dutyDate.between(start,end));
        }

        if (null != personIds && personIds.size() > 0){
            builder.and(scheduling.person.id.in(personIds));
        }

        if (null != dutyTeamIds && dutyTeamIds.size() > 0){
            builder.and(scheduling.dutyTeamId.id.in(dutyTeamIds));
        }

        if (StringUtils.hasText(personName)){
            builder.and(scheduling.person.addrName.eq(personName));
        }

        if (StringUtils.hasText(personId)){
            builder.and(scheduling.person.id.eq(personId));
        }

        if (StringUtils.hasText(shiftPatternName)){
            builder.and(scheduling.shiftPatternName.eq(shiftPatternName));
        }

        query/*.select(Projections.bean(Scheduling.class
                ,scheduling.id
                ,scheduling.orgId
                ,scheduling.orgName
                ,scheduling.dutyDate
                ,scheduling.dateTypeCode
                ,scheduling.dateTypeName
                ,scheduling.holidayName
                ,scheduling.shiftPatternId
                ,scheduling.shiftPatternName
                ,scheduling.dutyTeamId
                ,scheduling.dutyTeamName
                ,scheduling.ptypeCode
                ,scheduling.person
                ,scheduling.personName
                ,scheduling.hisPersonId
                ,scheduling.hisPersonName
                ,scheduling.startTime
                ,scheduling.endTime
        ))*/.where(builder)
                .orderBy(scheduling.shiftPatternId.asc())
                .orderBy(scheduling.dutyTeamId.id.asc());
        return findAll(query);
    }

    /**
     * 获取当天参与交接班的值班人员列表
     * @return
     */
    public List<Scheduling> findDutysByTimes(String day,String nextDay,String orgId) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

//        if (StringUtils.hasText(day)){
//            LocalDate localDate = DateUtil.strToLocalDate(day);
//            builder.and(scheduling.dutyDate.eq(localDate));
//        }

        if (StringUtils.hasText(day) && StringUtils.hasText(nextDay)){
            LocalDate current = DateUtil.strToLocalDate(day);
            LocalDate next = DateUtil.strToLocalDate(nextDay);
            builder.and(scheduling.dutyDate.between(current,next));
        }
        query.where(builder);
        return findAll(query);
    }


    /**
     * 获取personId集合
     * @param vo
     * @return
     */
    public List<Person> findPersonIds(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

//        List<String> teamList = vo.getTeamList();
        List<String> personIds = vo.getPersonIds();

        if (null != personIds && personIds.size() > 0){
            builder.and(scheduling.person.id.in(personIds));
        }
//        if (null != teamList && teamList.size() > 0){
//            builder.and(scheduling.person.dutyTeam.id.in(teamList));
//        }
        List<Person> list = query.select(Projections.bean(Person.class,scheduling.person.id,scheduling.person.addrName))
                .where(builder).fetchAll()
                .groupBy(scheduling.person.id).fetch();
        return list;
    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    public List<Scheduling> findListByPersonId(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        String personId = vo.getPersonId();
        LocalDate startTime = DateUtil.strToLocalDate(vo.getStartTime());
        LocalDate endTime = DateUtil.strToLocalDate(vo.getEndTime());

        if (StringUtils.hasText(personId)){
            builder.and(scheduling.person.id.eq(personId));
        }

        if (StringUtils.hasText(vo.getStartTime()) && StringUtils.hasText(vo.getEndTime())){
            booleanBuilder.and(scheduling.dutyDate.between(startTime,endTime));
        }
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dateTypeCode.count().as("number")
                ,scheduling.dateTypeCode.as("dateTypeCode"))).leftJoin(scheduling.person).where(booleanBuilder)
                .groupBy(scheduling.dateTypeCode,scheduling.person.id)
                .having(builder);
        return findAll(query);
    }

    /**
     * 获取teamId集合
     * @param orgId
     * @return
     */
    public List<Scheduling> findTeamIds(String orgId) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        query.select(Projections.bean(Scheduling.class,scheduling.person.dutyTeam.id))
                .where(builder)
                .groupBy(Projections.bean(Scheduling.class
                        ,scheduling.person.dutyTeam.id));
        return findAll(query);
    }

    /**
     * 根据这种模式获取当前时间的值班人员信息
     * @param vo
     * @return
     */
    public List<Scheduling> findListByMultiCondition(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String dTypeCode = String.valueOf(vo.getDateTypeCode());
        LocalDateTime currentDateTime = vo.getCurrentDateTime();
        if (StringUtils.hasText(dTypeCode)){
            builder.and(scheduling.dateTypeCode.eq(dTypeCode));
        }
        builder.and(scheduling.startTime.lt(currentDateTime));
        builder.and(scheduling.endTime.gt(currentDateTime));
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyDate
                ,scheduling.dutyTeamId
                ,scheduling.dutyTeamName
                ,scheduling.shiftPatternName
        )).where(builder);
        return findAll(query);
    }

    /**
     * 获取某种模式下的分组ID列表
     * @param vo
     * @return
     */
    public List<Scheduling> findTeamIdList(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String dTypeCode = String.valueOf(vo.getDateTypeCode());
        LocalDateTime currentDateTime = vo.getCurrentDateTime();
        if (StringUtils.hasText(dTypeCode)){
            builder.and(scheduling.dateTypeCode.eq(dTypeCode));
        }
        builder.and(scheduling.startTime.lt(currentDateTime));
        builder.and(scheduling.endTime.gt(currentDateTime));
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyTeamId
                ,scheduling.startTime
                ,scheduling.endTime))
                .where(builder)
                .groupBy(Projections.bean(Scheduling.class
                        ,scheduling.dutyTeamId
                        ,scheduling.startTime
                        ,scheduling.endTime));
        return findAll(query);
    }

    /**
     * 前一个的teamId
     * @param vo
     * @return
     */
    public List<Scheduling> findPreTeamId(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        LocalDateTime startTime = DateUtil.strToLocalDateTime(vo.getStartTime());
        String pTypeCode = vo.getPtypeCode();
        booleanBuilder.and(scheduling.ptypeCode.eq(pTypeCode));
        booleanBuilder.and(scheduling.startTime.eq(startTime));
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyTeamId
                ,scheduling.dateTypeCode
                ,scheduling.startTime))
                .where(booleanBuilder);
        List<Scheduling> schedulingList = findAll(query);
        return schedulingList;
    }

    /**
     * 根据这种模式和分組ID获取前一个的值班人员信息
     * @param vo
     * @return
     */
    public List<Scheduling> findPreListByMultiCondition(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String dTypeCode = String.valueOf(vo.getDateTypeCode());
        String dutyTeamId = vo.getDutyTeamId();
        String startTime = vo.getStartTime();
        if (StringUtils.hasText(dTypeCode)){
            builder.and(scheduling.dateTypeCode.eq(dTypeCode));
        }
        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.id.eq(dutyTeamId));
        }
        if (StringUtils.hasText(startTime)){
            builder.and(scheduling.startTime.eq(DateUtil.strToLocalDateTime(startTime)));
        }
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyDate
                ,scheduling.dutyTeamId
                ,scheduling.dutyTeamName
                ,scheduling.shiftPatternName)).where(builder);
        return findAll(query);
    }

    /**
     * 后一个的teamId
     * @param vo
     * @return
     */
    public List<Scheduling> findNextTeamId(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        String pTypeCode = vo.getPtypeCode();
        String endTime = vo.getEndTime();
        if (StringUtils.hasText(endTime)) {
            builder.and(scheduling.endTime.gt(DateUtil.strToLocalDateTime(endTime)));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        LocalDateTime time = query
                .select(scheduling.endTime.min())
                .where(builder)
                .orderBy(scheduling.endTime.desc())
                .fetchOne();
        booleanBuilder.and(scheduling.endTime.eq(time));
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyTeamId
                ,scheduling.dateTypeCode
                ,scheduling.endTime))
                .where(booleanBuilder);
        return findAll(query);
    }

    /**
     * 根据这种模式和分組ID获取后一个的值班人员信息
     * @param vo
     * @return
     */
    public List<Scheduling> findNextListByMultiCondition(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String dTypeCode = String.valueOf(vo.getDateTypeCode());
        String dutyTeamId = vo.getDutyTeamId();
        String endTime = vo.getEndTime();
        if (StringUtils.hasText(dTypeCode)){
            builder.and(scheduling.dateTypeCode.eq(dTypeCode));
        }
        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.id.eq(dutyTeamId));
        }
        if (StringUtils.hasText(endTime)){
            builder.and(scheduling.endTime.eq(DateUtil.strToLocalDateTime(endTime)));
        }
        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyDate
                ,scheduling.dutyTeamId
                ,scheduling.dutyTeamName
                ,scheduling.shiftPatternName)).where(builder);
        return findAll(query);
    }

    /**
     * 获取前一个值班开始时间
     * @param vo
     * @return
     */
    public LocalDateTime findPreStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        LocalDateTime startTime = DateUtil.strToLocalDateTime(vo.getStartTime());
        String pTypeCode = vo.getPtypeCode();
        builder.and(scheduling.startTime.lt(startTime));
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.max())
                .where(builder)
                .orderBy(scheduling.startTime.desc())
                .fetchOne();
        return time;
    }

    //值班信息批量插入
    @Transactional
    public List<Scheduling>  saveBatch(List<SchedulingVo> vo){
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling> entityList = mapper.voListToEntityList(vo);
        List<Scheduling> entitys = new ArrayList<>();
        if(!CollectionUtils.isEmpty(entityList)){
            for(Scheduling entity : entityList){
                if(null!=entity.getPerson()&&null==entity.getPerson().getId()){
                    entity.setPerson(null);
                }
                entitys.add(super.save(entity));
            }
        }
        return entitys;
    }

    //值班信息分组查询
    public List<Scheduling> findByCode(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String orgId = vo.getOrgId();
        String dutyDate = vo.getDutyDate();
        LocalDate date = DateUtil.strToLocalDate(dutyDate);

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

        if (StringUtils.hasText(dutyDate)){
            builder.and(scheduling.dutyDate.eq(date));
        }

        query.select(Projections.bean(Scheduling.class
                ,scheduling.ptypeCode
        )).where(builder).groupBy(scheduling.ptypeCode);
        return findAll(query);
    }

    //去重获取某一天的班次集合
    public List<Scheduling> findByShifts(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String dutyDate = vo.getDutyDate();
        LocalDate date = DateUtil.strToLocalDate(dutyDate);
        String code = vo.getPtypeCode();
        String ShiftPatternId = vo.getShiftPatternId();

        if (StringUtils.hasText(dutyDate)){
            builder.and(scheduling.dutyDate.eq(date));
        }

        if (StringUtils.hasText(code)){
            builder.and(scheduling.ptypeCode.eq(code));
        }

        if (StringUtils.hasText(ShiftPatternId)){
            builder.and(scheduling.shiftPatternId.eq(ShiftPatternId));
        }

        query.select(Projections.bean(Scheduling.class
                ,scheduling.shiftPatternId
        )).where(builder).groupBy(scheduling.shiftPatternId);
        return findAll(query);
    }

    //去重获取某一天的班次集合
    public List<Scheduling> findByDutyTeamIds(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String dutyDate = vo.getDutyDate();
        LocalDate date = DateUtil.strToLocalDate(dutyDate);
        String code = vo.getPtypeCode();
        String dutyTeamId  = vo.getDutyTeamId();

        if (StringUtils.hasText(dutyDate)){
            builder.and(scheduling.dutyDate.eq(date));
        }

        if (StringUtils.hasText(code)){
            builder.and(scheduling.ptypeCode.eq(code));
        }

        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.id.eq(dutyTeamId));
        }

        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyTeamId
        )).where(builder).groupBy(scheduling.dutyTeamId);
        return findAll(query);
    }

    //获取某个班次的人员集合
    public List<Scheduling> findPersons(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String dutyDate = vo.getDutyDate();
        LocalDate date = DateUtil.strToLocalDate(dutyDate);
        String shiftPatternId = vo.getShiftPatternId();
        String code = vo.getPtypeCode();
        String dutyTeamId = vo.getDutyTeamId();

        if (StringUtils.hasText(shiftPatternId)){
            builder.and(scheduling.shiftPatternId.eq(shiftPatternId));
        }

        if (StringUtils.hasText(dutyDate)){
            builder.and(scheduling.dutyDate.eq(date));
        }

        if (StringUtils.hasText(code)){
            builder.and(scheduling.ptypeCode.eq(code));
        }

        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.id.eq(dutyTeamId));
        }

        query.select(Projections.bean(Scheduling.class
                ,scheduling.shiftPatternId
                ,scheduling.person
                ,scheduling.personName
        )).where(builder).groupBy(scheduling.shiftPatternId,scheduling.person,scheduling.personName);
        return findAll(query);
    }
    /**
     * 去重获取某一天的班次集合
     */
    /*public List<Scheduling> findByDutyTeamIds(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String dutyDate = vo.getDutyDate();
        LocalDate date = DateUtil.strToLocalDate(dutyDate);
        String code = vo.getPtypeCode();
        String dutyTeamId  = vo.getDutyTeamId();

        if (StringUtils.hasText(dutyDate)){
            builder.and(scheduling.dutyDate.eq(date));
        }

        if (StringUtils.hasText(code)){
            builder.and(scheduling.ptypeCode.eq(code));
        }

        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.eq(dutyTeamId));
        }

        query.select(Projections.bean(Scheduling.class
                ,scheduling.dutyTeamId
        )).where(builder).groupBy(scheduling.dutyTeamId);
        return findAll(query);
    }*/

    //获取当月天数
    public int getMonthNumber(String date) {
        if (!StringUtils.isEmpty(date)) {
            String[] split = date.split("-");
            Calendar calen = Calendar.getInstance();
            //当前天数
            calen.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 0);
            int number = calen.get(Calendar.DAY_OF_MONTH);
            return number;
        } else {
            return 0;
        }
    }

    //根据当前时间获取shiftPatternId
    public Scheduling findShiftPattId(LocalDateTime localDateTime) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(localDateTime.toString())){
            builder.and(scheduling.startTime.before(localDateTime))
                    .and(scheduling.endTime.after(localDateTime))
                    .and(scheduling.shiftPatternId.isNotNull());
        }

        Scheduling result = query.select(
                Projections.bean(
                        Scheduling.class
                        , scheduling.shiftPatternId
                        ,scheduling.shiftPatternName
                )).where(builder).fetchFirst();
        return result;
    }

    /**
     * 根据条件查询获取单个班次的值班人员列表
     * @param vo
     * @return
     */
    public List<Scheduling> findSchedulingFlag(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String orgId = vo.getOrgId();
        String month = vo.getMonth();


        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

        if (StringUtils.hasText(month)){
            //开始时间
            String endMonth = month;
            month = month + "-01";
            //结束时间
            int number = getMonthNumber(month);
            String num = String.valueOf(number);
            num = endMonth + "-"+ num;
            LocalDate start = DateUtil.strToLocalDate(month);
            LocalDate end = DateUtil.strToLocalDate(num);
            builder.and(scheduling.dutyDate.between(start,end));
        }

        builder.and(scheduling.person.id.isNotEmpty());

        query.where(builder);
        return findAll(query);
    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    public List<Scheduling> findListCondition(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String dutyTeamId = vo.getDutyTeamId();
        List<String> personIds = vo.getPersonIds();
        LocalDate startTime = DateUtil.strToLocalDate(vo.getStartTime());
        LocalDate endTime = DateUtil.strToLocalDate(vo.getEndTime());
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(dutyTeamId)){
            builder.and(scheduling.dutyTeamId.id.eq(dutyTeamId));
        }
        if (StringUtils.hasText(vo.getStartTime()) && StringUtils.hasText(vo.getEndTime())){
            builder.and(scheduling.dutyDate.between(startTime,endTime));
        }
        if (null != personIds && personIds.size() > 0){
            builder.and(scheduling.person.id.in(personIds));
        }
        query.where(builder);
        return findAll(query);
    }

    /**
     * 获取当前值班模式下的人员值班
     * @param vo
     * @return
     */
    public List<Scheduling> findPersonsByDTypeCode(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        LocalDateTime currentDateTime = vo.getCurrentDateTime();
        Integer dTypeCode = vo.getDateTypeCode();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (null != dTypeCode){
            builder.and(scheduling.dateTypeCode.eq(String.valueOf(dTypeCode)));
        }
        if (null != currentDateTime){
            builder.and(scheduling.startTime.lt(currentDateTime));
            builder.and(scheduling.endTime.gt(currentDateTime));
        }
        query.where(builder);
        return findAll(query);
    }

    /**
     * 获取当前班次的开始时间
     * @param vo
     * @return
     */
    public List<Scheduling> findCurrentStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        LocalDateTime currentDateTime = vo.getCurrentDateTime();
        Integer dTypeCode = vo.getDateTypeCode();
        String ptypeCode = vo.getPtypeCode();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (null != dTypeCode){
            builder.and(scheduling.dateTypeCode.eq(String.valueOf(dTypeCode)));
        }
        if (null != currentDateTime){
            builder.and(scheduling.startTime.lt(currentDateTime));
            builder.and(scheduling.endTime.gt(currentDateTime));
        }
        if (null != ptypeCode){
            builder.and(scheduling.ptypeCode.eq(String.valueOf(ptypeCode)));
        }
        query.select(Projections.bean(Scheduling.class
                ,scheduling.startTime
        )).where(builder);
        return findAll(query);
    }


    public LocalDateTime findPreviousStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String pTypeCode = vo.getPtypeCode();
        Integer dTypeCode = vo.getDateTypeCode();
        if (StringUtils.hasText(vo.getCurrentDutyDate())) {
            LocalDateTime startTime = DateUtil.strToLocalDateTime(vo.getCurrentDutyDate());
            builder.and(scheduling.startTime.lt(startTime));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (null != dTypeCode){
            builder.and(scheduling.dateTypeCode.eq(String.valueOf(dTypeCode)));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.max())
                .where(builder)
                .orderBy(scheduling.startTime.desc())
                .fetchOne();
        return time;
    }

    /**
     * 获取前一个班次的信息
     * @param vo
     * @return
     */
    public List<Scheduling> findNextTimesInfo(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String startTime = vo.getStartTime();
        String ptypeCode = vo.getPtypeCode();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(startTime)){
            builder.and(scheduling.startTime.eq(DateUtil.strToLocalDateTime(startTime)));
        }
        if (null != ptypeCode){
            builder.and(scheduling.ptypeCode.eq(String.valueOf(ptypeCode)));
        }
        query.where(builder);
        return findAll(query);
    }

    /**
     * 根据前一个班次的开始时间 查询在那一天的起止时间之内
     * @param vo
     * @return
     */
    public Scheduling findPreviousPerson(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String startTimeStr = vo.getStartTime();
        String pTypeCode = vo.getPtypeCode();
        Integer dTypeCode = vo.getDateTypeCode();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (null != dTypeCode){
            builder.and(scheduling.dateTypeCode.eq(String.valueOf(dTypeCode)));
        }
        if (StringUtils.hasText(startTimeStr)) {
            builder.and(scheduling.startTime.loe(DateUtil.strToLocalDateTime(startTimeStr)));
            builder.and(scheduling.endTime.goe(DateUtil.strToLocalDateTime(startTimeStr)));
        }
        Scheduling entity = query.select(Projections.bean(Scheduling.class
                , scheduling.startTime
        )).where(builder).orderBy(scheduling.startTime.desc()).fetchOne();
        return entity;
    }

    /**
     * 按天的值班信息
     * @param vo
     * @return
     */
    public Scheduling findDayPersonInfo(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String pTypeCode = vo.getPtypeCode();
        String currentDutyDate = vo.getDutyDate();
        String startTimeStr = vo.getCurrentDutyDate();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(currentDutyDate)) {
            LocalDate dutyDate = DateUtil.strToLocalDate(currentDutyDate);
            builder.and(scheduling.dutyDate.eq(dutyDate));
        }
        if (StringUtils.hasText(startTimeStr)) {
            LocalDateTime startTime = DateUtil.strToLocalDateTime(startTimeStr);
            builder.and(scheduling.startTime.eq(startTime));
        }
        Scheduling entity = query.where(builder).fetchOne();
        return entity;
    }

    public LocalDateTime findPrStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String pTypeCode = vo.getPtypeCode();
        String dutyDateStr = vo.getDutyDate();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(dutyDateStr)) {
            LocalDate dutyDate = DateUtil.strToLocalDate(dutyDateStr);
            builder.and(scheduling.dutyDate.lt(dutyDate));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.max())
                .where(builder)
                .orderBy(scheduling.startTime.desc())
                .fetchOne();
        return time;
    }

    /**
     * 获取后一个班次的信息
     * @param vo
     * @return
     */
    public List<Scheduling> findNextPersons(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String startTime = vo.getStartTime();
        String pTypeCode = vo.getPtypeCode();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(startTime)){
            builder.and(scheduling.startTime.eq(DateUtil.strToLocalDateTime(startTime)));
        }
        query.where(builder);
        return findAll(query);
    }

    public LocalDateTime findNextStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String currentDutyDate = vo.getCurrentDutyDate();
        String pTypeCode = vo.getPtypeCode();
        Integer dateTypeCode = vo.getDateTypeCode();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (null != dateTypeCode){
            builder.and(scheduling.dateTypeCode.eq(String.valueOf(dateTypeCode)));
        }
        if (StringUtils.hasText(currentDutyDate)){
            builder.and(scheduling.startTime.gt(DateUtil.strToLocalDateTime(currentDutyDate)));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.min())
                .where(builder)
                .orderBy(scheduling.startTime.asc())
                .fetchOne();
        return time;
    }

    public LocalDateTime findNeStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String pTypeCode = vo.getPtypeCode();
        String dutyDateStr = vo.getDutyDate();
        String startTime = vo.getStartTime();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(startTime)) {
//            LocalDate dutyDate = DateUtil.strToLocalDate(dutyDateStr);
//            builder.and(scheduling.dutyDate.gt(dutyDate));
            LocalDateTime start = DateUtil.strToLocalDateTime(startTime);
            builder.and(scheduling.startTime.gt(start));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.min())
                .where(builder)
                .orderBy(scheduling.startTime.asc())
                .fetchOne();
        return time;
    }

    public Scheduling findCurrentShiftName(LocalDateTime time,String orgId) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

        if (null != time) {
            builder.and(scheduling.startTime.before(time));
            builder.and(scheduling.endTime.after(time));
            builder.and(scheduling.shiftPatternName.isNotEmpty());
        }
        Scheduling entity = query.select(Projections.bean(Scheduling.class
                ,scheduling.shiftPatternName
                ,scheduling.dutyDate
        )).where(builder).fetchOne();

        return entity;
    }

    /**
     * 根据日期获取当天的集团及各板块的值班人员列表，包括领导和值班员等，供浙能首页使用
     * @param vo
     * @return
     */
    public List<DutyMan> getAllDutysByDate(SearchAllDutyVo vo) {
        List<String> orgIds = vo.getOrgIds();
        String searchDate = vo.getSearchDate();
        StringBuilder builder = new StringBuilder();
        String dutyDate = "";
        if("mysql".equals(platform)){ //mysql环境
            dutyDate = " date_format(" + "'" +searchDate + "'" + ", '%Y-%m-%d')";
        }else { //oracle环境或postgre环境
            dutyDate = " to_date(" + "'" +searchDate + "'" + ",'yyyy-mm-dd')";
        }

        String orgIdString = "";
        if (!CollectionUtils.isEmpty(orgIds)){
            StringBuilder orgIdBuilder = new StringBuilder();
            orgIdBuilder.append("(");
            for (String orgId : orgIds){
                orgIdBuilder.append("'").append(orgId).append("'").append(",");
            }
            orgIdString = orgIdBuilder.toString();
            orgIdString = orgIdString.substring(0,orgIdString.length()-1);
            orgIdString += ")";
        }

        builder.append("select s.ORG_ID,s.PERSON_ID,p.ADDR_NAME as duty_name,p.MOBILE as duty_tel from")
                .append(" (SELECT ORG_ID,PERSON_ID from ED_SCHEDULING WHERE DUTY_DATE = ")
                .append(dutyDate)
                .append(" AND ORG_ID in ").append(orgIdString)
                .append(" AND PERSON_ID is not NULL")
                .append(" GROUP BY ORG_ID,PERSON_ID) s")
                .append(" left JOIN")
                .append(" (select p.id,p.ADDR_NAME,c.MOBILE from")
                .append(" ED_PERSON p , ED_CONTACT c")
                .append(" where p.ADDR_ID = c.id) p")
                .append(" on s.PERSON_ID = p.id");

        //根据数据源选择格式化原生sql的大小写
        String sql = sqlFormat.sqlFormat(builder.toString());
        List<DutyMan> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(DutyMan.class));
        return list;
    }

    /**
     * 先获取当月的天或班次的所有数据
     * @param searchVo
     * @return
     */
    public List<Scheduling> findMonthAllData(SchedulingSearchVo searchVo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = searchVo.getOrgId();
        String firstDay = searchVo.getFirstDay();
        String lastDay = searchVo.getLastDay();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(firstDay) && StringUtils.hasText(lastDay)) {
            builder.and(scheduling.dutyDate.between(DateUtil.strToLocalDate(firstDay),DateUtil.strToLocalDate(lastDay)));
        }
        query.where(builder).orderBy(scheduling.dutyDate.asc(),scheduling.dutyTeamId.id.asc(),scheduling.shiftPatternId.asc());
        return findAll(query);
    }

    /**
     * 获取上个月最后一天的值班信息(天或班次)
     * @param schedulingVo
     * @return
     */
    public List<Scheduling> findfirstDayInfo(SchedulingVo schedulingVo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = schedulingVo.getOrgId();
        String dutyDate = schedulingVo.getDutyDate();
        String pTypeCode = schedulingVo.getPtypeCode();
        String startTime = schedulingVo.getStartTime();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)){
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(dutyDate)) {
            builder.and(scheduling.dutyDate.eq(DateUtil.strToLocalDate(dutyDate)));
        }
        if (StringUtils.hasText(startTime)) {
            builder.and(scheduling.startTime.eq(DateUtil.strToLocalDateTime(startTime)));
        }
        query.select(Projections.bean(Scheduling.class
                ,scheduling.orgId
                ,scheduling.person
                ,scheduling.personName
                ,scheduling.dutyTeamId
                ,scheduling.dutyTeamName
        )).where(builder).orderBy(scheduling.dutyDate.asc());
        return findAll(query);
    }


    /**
     * 数据更新批量入库
     * @param list
     * @return
     */
    @Transactional
    public List<Scheduling> updateBatch(List<Scheduling> list) {
        Assert.notNull(list,"list对象不能为空");
        List<Scheduling> infoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (Scheduling entity : list){
                Scheduling scheduling = null;
                if (!StringUtils.isEmpty(entity.getId())) {
                    Scheduling temp = findOne(entity.getId());
                    BeanUtils.copyNonNullProperties(entity, temp);
                    scheduling = super.save(temp);
                }
                infoList.add(scheduling);
            }
        }
        return infoList;
    }

    public LocalDateTime findLastStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String pTypeCode = vo.getPtypeCode();
        String dutyDateStr = vo.getDutyDate();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(dutyDateStr)) {
            LocalDate dutyDate = DateUtil.strToLocalDate(dutyDateStr);
            builder.and(scheduling.dutyDate.eq(dutyDate));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.max())
                .where(builder)
                .orderBy(scheduling.startTime.desc())
                .fetchOne();
        return time;
    }

    /**
     * 查询该月是否有人员排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    public List<Scheduling> findPersonsList(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String orgId = vo.getOrgId();
        String month = vo.getMonth();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

        if (StringUtils.hasText(month)){
            //开始时间
            String endMonth = month;
            month = month + "-01";
            //结束时间
            int number = getMonthNumber(month);
            String num = String.valueOf(number);
            num = endMonth + "-"+ num;
            LocalDate start = DateUtil.strToLocalDate(month);
            LocalDate end = DateUtil.strToLocalDate(num);
            builder.and(scheduling.dutyDate.between(start,end));
            builder.and(scheduling.person.isNotNull());
        }

        query.where(builder);
        return findAll(query);
    }


    /**
     * 删除整月数据 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @Transactional
    public void deteleSchedulingsList(SchedulingVo vo) {
        List<Scheduling> list = findPersonList(vo);
        super.delete(list);
    }

    public List<Scheduling> findPersonList(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;

        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();

        String orgId = vo.getOrgId();
        String month = vo.getMonth();

        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }

        if (StringUtils.hasText(month)){
            //开始时间
            String endMonth = month;
            month = month + "-01";
            //结束时间
            int number = getMonthNumber(month);
            String num = String.valueOf(number);
            num = endMonth + "-"+ num;
            LocalDate start = DateUtil.strToLocalDate(month);
            LocalDate end = DateUtil.strToLocalDate(num);
            builder.and(scheduling.dutyDate.between(start,end));
        }
        query.where(builder);
        return findAll(query);
    }

    /**
     * 当前班次的开始时间
     * @param vo
     * @return
     */
    public LocalDateTime findCurrentTimesOfStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String pTypeCode = vo.getPtypeCode();
        LocalDateTime currentDateTime = vo.getCurrentDateTime();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        builder.and(scheduling.startTime.loe(currentDateTime));
        builder.and(scheduling.endTime.goe(currentDateTime));
        LocalDateTime time = query
                .select(scheduling.startTime)
                .where(builder)
                .fetchOne();
        return time;
    }

    /**
     * 下个班次的开始时间
     * @param vo
     * @return
     */
    public LocalDateTime findNextTimesOfStartTime(SchedulingVo vo) {
        QScheduling scheduling = QScheduling.scheduling;
        JPQLQuery<Scheduling> query = from(scheduling);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        String pTypeCode = vo.getPtypeCode();
        String startTimeStr = vo.getStartTime();
        if (StringUtils.hasText(orgId)){
            builder.and(scheduling.orgId.eq(orgId));
        }
        if (StringUtils.hasText(pTypeCode)) {
            builder.and(scheduling.ptypeCode.eq(pTypeCode));
        }
        if (StringUtils.hasText(startTimeStr)) {
            builder.and(scheduling.startTime.gt(DateUtil.strToLocalDateTime(startTimeStr)));
        }
        LocalDateTime time = query
                .select(scheduling.startTime.min())
                .where(builder)
                .orderBy(scheduling.startTime.asc())
                .fetchOne();
        return time;
    }
}
