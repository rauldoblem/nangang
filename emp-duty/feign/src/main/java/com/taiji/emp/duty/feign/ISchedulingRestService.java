package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.searchVo.DutyMan;
import com.taiji.emp.duty.searchVo.SearchAllDutyVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 值班表 feign 接口服务类
 */
@FeignClient(value = "micro-duty-scheduling")
public interface ISchedulingRestService {

    /**
     * 获取单个班次的值班人员列表 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findList")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findList(@RequestBody SchedulingVo vo);

    /**
     * 保存单个班次的值班人员列表 SchedulingListVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/createList")
    @ResponseBody
    ResponseEntity<SchedulingVo> createList(@RequestBody List<SchedulingVo> vo);

    /**
     * 保存单个班次的值班人员的换班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<SchedulingVo> create(@RequestBody SchedulingVo vo);

    /**
     *  获取当天参与交接班的值班人员列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findCurrentDutys")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findCurrentDutys();

    /**
     *  获取当天与下一天参与交接班的值班人员列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findNextDutys")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextDutys(@RequestParam(value = "orgId") String orgId);

    /**
     * 获取personId集合
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/personIds")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPersonIds(@RequestBody SchedulingVo vo);

    /**
     * 值班统计
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list/personId")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findListByPersonId(@RequestBody SchedulingVo vo);

    /**
     * 获取teamId集合
     * @param orgId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/teamIds")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findTeamIds(@RequestParam(value = "orgId") String orgId);

    /**
     * 根据这种模式获取当前时间的值班人员信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findList/multiCondition")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findListByMultiCondition(@RequestBody SchedulingVo vo);

    /**
     * 获取某种模式下的分组ID列表
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findList/teamId/list")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findTeamIdList(@RequestBody SchedulingVo vo);

    /**
     * 前一个的teamId
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/preTeamId")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPreTeamId(@RequestBody SchedulingVo vo);

    /**
     * 根据这种模式和分組ID获取前一个的值班人员信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/preList/multiCondition")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPreListByMultiCondition(@RequestBody SchedulingVo vo);

    /**
     * 后一个的teamId
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/nextTeamId")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextTeamId(@RequestBody SchedulingVo vo);

    /**
     * 根据这种模式和分組ID获取后一个的值班人员信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/nextList/multiCondition")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextListByMultiCondition(@RequestBody SchedulingVo vo);

    /**
     * 值班信息批量插入
     * @param  schedulingVoList
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/saveBatch")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> createBatch(List<SchedulingVo> schedulingVoList);

    /**
     * 值班信息分组查询
     * @param  schedulingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findByCode")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findByCode(SchedulingVo schedulingVo);

    /**
     * 去重获取某一天的班次集合
     * @param  schedulingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findByShifts")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findByShifts(SchedulingVo schedulingVo);

    /**
     * 去重获取某一天按天集合
     * @param  schedulingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findByDutyTeamIds")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findByDutyTeamIds(SchedulingVo schedulingVo);

    /**
     *  获取某个班次的人员集合
     * @param  schedulingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findPersons")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPersons(SchedulingVo schedulingVo);

    /**
     * 去重获取某一天按天集合
     * @param  schedulingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
//    @RequestMapping(method = RequestMethod.POST,path = "/findByDutyTeamIds")
//    @ResponseBody
//    ResponseEntity<List<SchedulingVo>> findByDutyTeamIds(SchedulingVo schedulingVo);

    /**
     *  根据当前时间获得当前shiftPattId
     * @param  localDateTime
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findShiftPattId")
    @ResponseBody
    ResponseEntity<SchedulingVo> findShiftPattId(@RequestBody LocalDateTime localDateTime);

    /**
     * 查看当月是否有排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findSchedulingFlag")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findSchedulingFlag(@RequestBody SchedulingVo vo);

    /**
     * 值班统计
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list/condition")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findListCondition(@RequestBody SchedulingVo vo);

    /**
     * 获取当前值班模式下的人员值班
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/persons/dTypeCode")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPersonsByDTypeCode(@RequestBody SchedulingVo vo);

    /**
     * 获取当前班次的开始时间
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/current/startTime")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findCurrentStartTime(@RequestBody SchedulingVo vo);

    /**
     * 获取前一个班次的信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/prev/persons")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPrevPersons(@RequestBody SchedulingVo vo);

    /**
     * 根据前一个班次的开始时间 查询在那一天的起止时间之内
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/previous/person")
    @ResponseBody
    ResponseEntity<SchedulingVo> findPreviousPerson(@RequestBody SchedulingVo vo);

    /**
     * 按天的值班信息
     * @param dayVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/day/person/info")
    @ResponseBody
    ResponseEntity<SchedulingVo> findDayPersonInfo(@RequestBody SchedulingVo dayVo);

    /**
     * 获取前一个排班的信息(当前班次的开始时间 为空)(班次)
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/prev/person/info")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPrevPersonsInfo(@RequestBody SchedulingVo vo);

    /**
     * 获取前一个排班的信息(当前天的开始时间 为空) (天)
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/day/info")
    @ResponseBody
    ResponseEntity<SchedulingVo> findDayInfo(@RequestBody SchedulingVo vo);

    /**
     * 获取后一个班次的信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/next/persons")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextPersons(@RequestBody SchedulingVo vo);

    /**
     * 获取后一个天的值班信息
     * @param dayVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/next/day")
    @ResponseBody
    ResponseEntity<SchedulingVo> findNextDay(@RequestBody SchedulingVo dayVo);

    /**
     * 获取后一个班次的信息(班次)
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/next/persons/info")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextPersonsInfo(@RequestBody SchedulingVo vo);

    /**
     * 按天的值班信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/nextDay/info")
    @ResponseBody
    ResponseEntity<SchedulingVo> findNextDayInfo(@RequestBody SchedulingVo vo);


    /**
     * 根据日期获取当天的集团及各板块的值班人员列表，包括领导和值班员等，供浙能首页使用
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/allDutys/date")
    @ResponseBody
    ResponseEntity<List<DutyMan>> getAllDutysByDate(@RequestBody SearchAllDutyVo vo);

    /**
     * 自动排班
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/auto/scheduling")
    @ResponseBody
    ResponseEntity<Void> autoScheduling(@RequestBody SchedulingVo vo);

    /**
     * 获取单个班次的值班人员列表 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findCalList")
    @ResponseBody
    ResponseEntity<List<List<SchedulingVo>>> findCalList(@RequestBody SchedulingVo vo);

    /**
     * 值班信息插入
     * @param  calenSettingVo
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/saveSchedulings")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> saveSchedulings(@RequestBody CalenSettingVo calenSettingVo);

    /**
     * 查询该月是否有人员排班 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findPersonsList")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findPersonsList(@RequestBody SchedulingVo vo);

    /**
     * 删除整月数据 SchedulingVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/deteleSchedulingsList")
    @ResponseBody
    ResponseEntity<Void> deteleSchedulingsList(@RequestBody SchedulingVo vo);

    /**
     * 获取下个班次的值班人员信息
     * @param schedulingVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/nextTimes/list")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextTimesList(@RequestBody SchedulingVo schedulingVo);
}
