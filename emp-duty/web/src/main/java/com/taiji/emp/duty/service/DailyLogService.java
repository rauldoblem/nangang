package com.taiji.emp.duty.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.feign.DailyLogClient;
import com.taiji.emp.duty.feign.DocAttClient;
import com.taiji.emp.duty.feign.UtilsFeignClient;
import com.taiji.emp.duty.searchVo.DailyLogSearchVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogTYVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyLogService extends BaseService {

    @Autowired
    DailyLogClient dailyLogClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;
    @Autowired
    private DocAttClient docAttClient;

    /**
     * 新增值班日志信息
     * @param vo
     * @param principal
     */
    public void create(DailyLogVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userVoProfile = userVo.getProfile();
        String userName = principal.getName();

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        vo.setOrgId(userVoProfile.getOrgId()); //所属部门ID
        vo.setOrgName(userVoProfile.getOrgName()); //所属部门名称

        String affirtTypeId = vo.getAffirtTypeId();
        Assert.hasText(affirtTypeId,"affirtTypeId不能为空");
        vo.setAffirtTypeName(getItemNameById(affirtTypeId)); //日志类型名称

        dailyLogClient.create(vo);
    }

    /**
     * 根据id删除某条值班日志信息
     * @param id
     */
    public void deleteLogic(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = dailyLogClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 修改值班日志信息
     * @param vo
     * @param principal
     */
    public void update(DailyLogVo vo, Principal principal) {
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        //获取用户姓名
        String userName = principal.getName();
        //更新人
        vo.setUpdateBy(userName);

        vo.setOrgId(userProfileVo.getOrgId()); //所属部门ID
        vo.setOrgName(getOrgNameById(userProfileVo.getOrgId()).getOrgName());

        String affirtTypeId = vo.getAffirtTypeId();
        Assert.hasText(affirtTypeId,"affirtTypeId不能为空");
        DicGroupItemVo dicGroupItemVo = getItemById(affirtTypeId);
        vo.setAffirtTypeName(dicGroupItemVo.getItemName()); //日志类型名称

        dailyLogClient.update(vo, vo.getId());
    }

    /**
     * 根据id查询值班日志信息
     * @param id
     * @return
     */
    public DailyLogVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<DailyLogVo> resultVo = dailyLogClient.findOne(id);
        DailyLogVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询值班日志列表
     * @param dailyLogSearchVo
     * @return
     */
    public List<DailyLogVo> findList(DailyLogSearchVo dailyLogSearchVo) {
        dailyLogSearchVo.setNowTime(utilsFeignClient.now().getBody());
        Assert.notNull(dailyLogSearchVo,"dailyLogSearchVo不能为null");
        ResponseEntity<List<DailyLogVo>> list = dailyLogClient.findList(dailyLogSearchVo);
        List<DailyLogVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 根据条件查询值班日志列表——分页
     * @param dailyLogSearchVo
     * @return
     */
    public RestPageImpl<DailyLogVo> findPage(DailyLogSearchVo dailyLogSearchVo) {
        Assert.notNull(dailyLogSearchVo,"dailyLogSearchVo不能为null");
        ResponseEntity<RestPageImpl<DailyLogVo>> resultVo = dailyLogClient.findPage(dailyLogSearchVo);
        RestPageImpl<DailyLogVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 修改值班日志办理状态
     * @param entityVo
     * @param dailyLogTreatmentVo
     */
    public void updateTreatStatus(DailyLogVo entityVo,DailyLogTreatmentVo dailyLogTreatmentVo) {
        Assert.notNull(dailyLogTreatmentVo,"dailyLogTreatmentVo不能为null");
        String treatStatus = dailyLogTreatmentVo.getTreatStatus();
        Assert.notNull(entityVo,"entityVo不能为null");
        entityVo.setTreatTime(dailyLogTreatmentVo.getTreatTime());
        entityVo.setTreatStatus(treatStatus);
        dailyLogClient.update(entityVo,entityVo.getId());
    }

    /**
     * 根据条件查询今天和昨天的值班日志列表-不分页
     * @param dailyLogSearchVo
     * @return
     */
    public DailyLogTYVo findTodayAndYesterdayList(DailyLogSearchVo dailyLogSearchVo) {
        LocalDateTime currentDate = utilsFeignClient.now().getBody();
        dailyLogSearchVo.setNowTime(currentDate);
        Assert.notNull(dailyLogSearchVo,"dailyLogSearchVo不能为null");
        ResponseEntity<List<DailyLogVo>> list = dailyLogClient.findList(dailyLogSearchVo);
        List<DailyLogVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        DailyLogTYVo tyVoList = new DailyLogTYVo();
        //当天的0时0分0秒
        LocalDateTime today = currentDate.minusHours(currentDate.getHour()).minusMinutes(currentDate.getMinute()).minusSeconds(currentDate.getSecond());
        //昨天的23:59:59
        LocalDateTime previousDay = today.minusDays(1).plusHours(23).plusMinutes(59).plusSeconds(59);
        //当前时间的后一秒
        LocalDateTime currentNextSeconds =currentDate.plusSeconds(1);
        List<DailyLogVo> todayList = voList.stream().filter(temp -> currentNextSeconds.isAfter(temp.getCreateTime()) && previousDay.isBefore(temp.getCreateTime())).collect(Collectors.toList());
        //前天的23:59:59
        LocalDateTime preDateTime = previousDay.minusDays(1);
        List<DailyLogVo> yesterdayList = voList.stream().filter(temp -> today.isAfter(temp.getCreateTime()) && preDateTime.isBefore(temp.getCreateTime())).collect(Collectors.toList());
        tyVoList.setToday(todayList);
        tyVoList.setYesterday(yesterdayList);
        return tyVoList;
    }
}
