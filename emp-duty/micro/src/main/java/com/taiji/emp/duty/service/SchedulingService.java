package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.entity.Scheduling;
import com.taiji.emp.duty.repository.SchedulingRepository;
import com.taiji.emp.duty.searchVo.DutyMan;
import com.taiji.emp.duty.searchVo.SearchAllDutyVo;
import com.taiji.emp.duty.vo.SchedulingSearchVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.micro.common.service.BaseService;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SchedulingService extends BaseService<Scheduling,String> {

    @Autowired
    private SchedulingRepository repository;

    /**
     * 根据条件查询获取单个班次的值班人员列表
     * @param vo
     * @return
     */
    public List<Scheduling> findList(SchedulingVo vo) {
        List<Scheduling> list = repository.findList(vo);
        return list;
    }

    /**
     * 保存单个班次的值班人员列表 SchedulingListVo不能为空
     * @param vo
     * @return
     */
    public Scheduling createList(List<Scheduling> vo) {
        Scheduling scheduling = null;
        if (null != vo && vo.size() > 0) {

            for(Scheduling entity : vo){
                //清除旧数据
                repository.delete(entity);
            }

            for (Scheduling entity : vo) {
                scheduling = repository.save(entity);
            }
        }
        return scheduling;
    }

    /**
     * 保存单个班次的值班人员的换班 SchedulingVo不能为空
     * @param entity
     * @return
     */
    public Scheduling create(Scheduling entity) {
        Scheduling scheduling = repository.updateScheduling(entity);
        return scheduling;
    }

    /**
     *  获取当天参与交接班的值班人员列表
     * @return
     */
    public List<Scheduling> findCurrentDutys() {
        //当前时间
        String date = DateUtil.getCurrentDate();
        String time = DateUtil.stringToString(date,DateUtil.FORMAT_DAY);
        return repository.findDutysByTimes(time,null,null);
    }

    /**
     *  获取当天与下一天参与交接班的值班人员列表
     * @return
     */
    public List<Scheduling> findNextDutys(String orgId) {
        //当前时间
        String date = DateUtil.getDateStr(new Date());
        //下一天
        String nextDay = DateUtil.addDay(date,DateUtil.FORMAT_DAY,1);
        return repository.findDutysByTimes(date,nextDay,orgId);
    }

    /**
     * 获取personId集合
     * @param vo
     * @return
     */
    public List<Person> findPersonIds(SchedulingVo vo) {
        List<Person> list = repository.findPersonIds(vo);
        return list;
    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    public List<Scheduling> findListByPersonId(SchedulingVo vo) {
        List<Scheduling> list = repository.findListByPersonId(vo);
        return list;
    }

    /**
     * 获取teamId集合
     * @param orgId
     * @return
     */
    public List<Scheduling> findTeamIds(String orgId) {
        List<Scheduling> list = repository.findTeamIds(orgId);
        return list;
    }

    /**
     * 根据这种模式获取当前时间的值班人员信息
     * @param vo
     * @return
     */
    public List<Scheduling> findListByMultiCondition(SchedulingVo vo) {
        List<Scheduling> list = repository.findListByMultiCondition(vo);
        return list;
    }

    /**
     * 获取某种模式下的分组ID列表
     * @param vo
     * @return
     */
    public List<Scheduling> findTeamIdList(SchedulingVo vo) {
        List<Scheduling> list = repository.findTeamIdList(vo);
        return list;
    }

    /**
     * 前一个的teamId
     * @param vo
     * @return
     */
    public List<Scheduling> findPreTeamId(SchedulingVo vo) {
        LocalDateTime preStartTime = repository.findPreStartTime(vo);
        vo.setStartTime(DateUtil.getDateTimeStr(preStartTime));
        List<Scheduling> entity = repository.findPreTeamId(vo);
        return entity;
    }

    /**
     * 根据这种模式和分組ID获取前一个的值班人员信息
     * @param vo
     * @return
     */
    public List<Scheduling> findPreListByMultiCondition(SchedulingVo vo) {
        List<Scheduling> list = repository.findPreListByMultiCondition(vo);
        return list;
    }

    /**
     * 后一个的teamId
     * @param vo
     * @return
     */
    public List<Scheduling> findNextTeamId(SchedulingVo vo) {
        List<Scheduling> entity = repository.findNextTeamId(vo);
        return entity;
    }

    /**
     * 根据这种模式和分組ID获取后一个的值班人员信息
     * @param vo
     * @return
     */
    public List<Scheduling> findNextListByMultiCondition(SchedulingVo vo) {
        List<Scheduling> list = repository.findNextListByMultiCondition(vo);
        return list;
    }

    /**
     * 日历设置批量插入
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    public List<Scheduling> createBatch(List<SchedulingVo> vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling>  result = repository.saveBatch(vo);
        return result;
    }

    /**
     * 值班信息分组查询
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    public List<Scheduling> findByCode(SchedulingVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling>  result = repository.findByCode(vo);
        return result;
    }

    /**
     * 去重获取某一天的班次集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    public List<Scheduling> findByShifts(SchedulingVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling>  result = repository.findByShifts(vo);
        return result;
    }

    /**
     * 去重获取某一天按天集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    /*public List<Scheduling> findByDutyTeamIds(SchedulingVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling>  result = repository.findByDutyTeamIds(vo);
        return result;
    }*/

    /**
     *  获取某个班次的人员集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    public List<Scheduling> findPersons(SchedulingVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling>  result = repository.findPersons(vo);
        return result;
    }

    /**
     * 去重获取某一天按天集合
     * @param  vo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    public List<Scheduling> findByDutyTeamIds(SchedulingVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<Scheduling>  result = repository.findByDutyTeamIds(vo);
        return result;
    }

    /**
     * 根据当前时间获取当前班次Id
     * @param localDateTime
     * @return
     */
    public Scheduling findShiftPattId(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime,"localDateTime不能为空");
        Scheduling  result = repository.findShiftPattId(localDateTime);
        return result;
    }

    /**
     * 查看当月是否有排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    public List<Scheduling> findSchedulingFlag(SchedulingVo vo) {
        List<Scheduling> list = repository.findSchedulingFlag(vo);
        return list;
    }

    /**
     * 值班统计
     * @param vo
     * @return
     */
    public List<Scheduling> findListCondition(SchedulingVo vo) {
        List<Scheduling> list = repository.findListCondition(vo);
        return list;
    }

    /**
     * 获取当前值班模式下的人员值班
     * @param vo
     * @return
     */
    public List<Scheduling> findPersonsByDTypeCode(SchedulingVo vo) {
        List<Scheduling> list = repository.findPersonsByDTypeCode(vo);
        return list;
    }

    /**
     * 获取当前班次的开始时间
     * @param vo
     * @return
     */
    public List<Scheduling> findCurrentStartTime(SchedulingVo vo) {
        List<Scheduling> list = repository.findCurrentStartTime(vo);
        return list;
    }

    /**
     * 获取前一个班次的信息
     * @param vo
     * @return
     */
    public List<Scheduling> findPrevPersons(SchedulingVo vo) {
        LocalDateTime preStartTime = repository.findPreviousStartTime(vo);
        List<Scheduling> info = getPreviousInfo(preStartTime, vo);
        return info;
    }

    /**
     * 根据前一个班次的开始时间 查询在那一天的起止时间之内
     * @param vo
     * @return
     */
    public Scheduling findPreviousPerson(SchedulingVo vo) {
        Assert.notNull(vo,"vo不能为空");
        LocalDateTime preStartTime = repository.findPreviousStartTime(vo);
        vo.setStartTime(DateUtil.getDateTimeStr(preStartTime));
        if (null != preStartTime) {
            Scheduling result = repository.findPreviousPerson(vo);
            return result;
        }else {
            return null;
        }
    }

    /**
     * 按天的值班信息
     * @param vo
     * @return
     */
    public Scheduling findDayPersonInfo(SchedulingVo vo) {
        Assert.notNull(vo,"vo不能为空");
        Scheduling  result = repository.findDayPersonInfo(vo);
        return result;
    }

    /**
     * 获取前一个班次的信息(当前班次的开始时间 为空)
     * @param vo
     * @return
     */
    public List<Scheduling> findPrevPersonsInfo(SchedulingVo vo) {
        LocalDateTime preStartTime = repository.findPrStartTime(vo);
        List<Scheduling> info = getPreviousInfo(preStartTime, vo);
        return info;
    }

    private List<Scheduling> getPreviousInfo(LocalDateTime preStartTime,SchedulingVo vo){
        if (null != preStartTime) {
            vo.setStartTime(DateUtil.getDateTimeStr(preStartTime));
            List<Scheduling> list = repository.findNextPersons(vo);
            return list;
        }else {
            return null;
        }
    }

    public Scheduling findDayInfo(SchedulingVo vo) {
        LocalDateTime preStartTime = repository.findPrStartTime(vo);
        LocalDate prevDate = DateUtil.strToLocalDate(vo.getDutyDate()).minusDays(1);//前一天的值排班日期
        vo.setDutyDate(DateUtil.getDateStr(prevDate));
        if (null != preStartTime) {
            vo.setStartTime(DateUtil.getDateTimeStr(preStartTime));
            Scheduling result = repository.findDayPersonInfo(vo);
            return result;
        }else {
            return repository.findDayPersonInfo(vo);
        }
    }

    /**
     * 获取后一个班次的信息
     * @param vo
     * @return
     */
    public List<Scheduling> findNextPersons(SchedulingVo vo) {
        LocalDateTime nextStartTime = repository.findNextStartTime(vo);
        if (null != nextStartTime) {
            vo.setStartTime(DateUtil.getDateTimeStr(nextStartTime));
            List<Scheduling> list = repository.findNextPersons(vo);
            return list;
        }else {
            return null;
        }
    }

    /**
     * 获取后一个天的值班信息
     * @param vo
     * @return
     */
    public Scheduling findNextDay(SchedulingVo vo) {
        LocalDateTime nextStartTime = repository.findNextStartTime(vo);
        if (null != nextStartTime) {
            vo.setStartTime(DateUtil.getDateTimeStr(nextStartTime));
            Scheduling entity = repository.findDayPersonInfo(vo);
            return entity;
        }else {
            return null;
        }
    }

    /**
     * 获取后一个班次的信息(班次)
     * @param vo
     * @return
     */
    public List<Scheduling> findNextPersonsInfo(SchedulingVo vo) {
        LocalDateTime nextStartTime = repository.findNeStartTime(vo);
        LocalDate prevDate = DateUtil.strToLocalDate(vo.getDutyDate()).plusDays(1);//后一天的值排班日期
        vo.setDutyDate(DateUtil.getDateStr(prevDate));
        if (null != nextStartTime){
            vo.setStartTime(DateUtil.getDateTimeStr(nextStartTime));
            List<Scheduling> list = repository.findNextPersons(vo);
            return list;
        }else {
            return null;
        }
    }

    /**
     * 按天的值班信息
     * @param vo
     * @return
     */
    public Scheduling findNextDayInfo(SchedulingVo vo) {
        LocalDateTime nextStartTime = repository.findNeStartTime(vo);
        LocalDate prevDate = DateUtil.strToLocalDate(vo.getDutyDate()).plusDays(1);//后一天的值排班日期
        vo.setDutyDate(DateUtil.getDateStr(prevDate));
        if (null != nextStartTime) {
            vo.setStartTime(DateUtil.getDateTimeStr(nextStartTime));
            Scheduling entity = repository.findDayPersonInfo(vo);
            return entity;
        }else {
            return null;
        }
    }

    /**
     * 根据日期获取当天的集团及各板块的值班人员列表，包括领导和值班员等，供浙能首页使用
     * @param vo
     * @return
     */
    public List<DutyMan> getAllDutysByDate(SearchAllDutyVo vo) {
        List<DutyMan> list = repository.getAllDutysByDate(vo);
        return list;
    }

    /**
     * 先获取当月的天或班次的所有数据
     * @param searchVo
     * @return
     */
    public List<Scheduling> findMonthAllData(SchedulingSearchVo searchVo) {
        List<Scheduling> list = repository.findMonthAllData(searchVo);
        return list;
    }

    public LocalDateTime findPrStartTime(SchedulingVo schedulingVo) {
        LocalDateTime preStartTime = repository.findPrStartTime(schedulingVo);
        return preStartTime;
    }

    /**
     * 获取上个月最后一天的值班信息(天或班次)
     * @param schedulingVo
     * @return
     */
    public List<Scheduling> findfirstDayInfo(SchedulingVo schedulingVo) {
        List<Scheduling> list = repository.findfirstDayInfo(schedulingVo);
        return list;
    }

    /**
     * 数据更新批量入库
     * @param list
     * @return
     */
    public List<Scheduling> updateBatch(List<Scheduling> list) {
        List<Scheduling> schedulingList = repository.updateBatch(list);
        return schedulingList;
    }

    public LocalDateTime findLastStartTime(SchedulingVo schedulingVo) {
        LocalDateTime preStartTime = repository.findLastStartTime(schedulingVo);
        return preStartTime;
    }

    /**
     * 查询该月是否有人员排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    public List<Scheduling> findPersonsList(SchedulingVo vo) {
        List<Scheduling> list = repository.findPersonsList(vo);
        return list;
    }

    /**
     * 删除整月数据 SchedulingVo不能为空
     * @param vo
     * @return
     */
    public void deteleSchedulingsList(SchedulingVo vo) {
        repository.deteleSchedulingsList(vo);
    }

    /**
     * 当前班次的开始时间
     * @param schedulingVo
     * @return
     */
    public LocalDateTime findCurrentTimesOfStartTime(SchedulingVo schedulingVo) {
        LocalDateTime startTime = repository.findCurrentTimesOfStartTime(schedulingVo);
        return startTime;
    }

    /**
     * 下个班次的开始时间
     * @param schedulingVo
     * @return
     */
    public LocalDateTime findNextTimesOfStartTime(SchedulingVo schedulingVo) {
        LocalDateTime startTime = repository.findNextTimesOfStartTime(schedulingVo);
        return startTime;
    }

    /**
     * 获取下个班次值班人员信息
     * @param schedulingVo
     * @return
     */
    public List<Scheduling> findNextTimesInfo(SchedulingVo schedulingVo) {
        List<Scheduling> list = repository.findNextTimesInfo(schedulingVo);
        return list;
    }
}
