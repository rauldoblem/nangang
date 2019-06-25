package com.taiji.emp.nangang.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.feign.*;
import com.taiji.emp.nangang.searchVo.dailyCheck.DailyCheckPageVo;
import com.taiji.emp.nangang.vo.*;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyCheckService extends BaseService{

    @Autowired
    private DailyCheckClient client;

    @Autowired
    private DailyCheckItemsClient itemsClient;

    @Autowired
    private DailyCheckDailyLogClient dailyCheckDailyLogClient;

    @Autowired
    private ShedulingClient shedulingClient;

    @Autowired
    private UtilsFeignClient utilsFeignClient;

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    private LocalDateTime getCurrentTime(){
        LocalDateTime currentDateTime = utilsFeignClient.now().getBody();
        return currentDateTime;
    }
    /**
     * 根据更新值班检查表(修改isShift，没加updateby)
     */
    public void updateDailyCheck(String id, Principal principal) {

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = userProfileVo.getName();

//        vo.setCreateBy(account);
//        vo.setUpdateBy(account);

        boolean notBlank = StringUtils.isNotBlank(id);
        Assert.isTrue(notBlank ,"检查表ID 不能为空格或空字符串" );

        ResponseEntity<DailyCheckVo> resultVo = client.updateDailyCheck(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 调用duty的接口获取检查记录,首页使用，从值班记录表中查询，
     * 若没有，则用数据字典（查询）初始化
     */
    public DailyCheckItemsListVo getCheckItem(ShiftDateVo shiftDateVo, Principal principal) {

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = userProfileVo.getName();
        DailyCheckItemsListVo dailyCheckItemsListVo = new DailyCheckItemsListVo();
        List<DailyCheckItemsVo> dailyCheckItemsVos;
        String dutyDateStart = shiftDateVo.getDutyDateStart();
        String dutyDateEnd = shiftDateVo.getDutyDateEnd();
        LocalDateTime currentDateTime = null;
        //查询上一班、当前、下一班的工作检查单
        if (!StringUtils.isEmpty(dutyDateStart) && !StringUtils.isEmpty(dutyDateEnd)){
            String subTimes = getSubTimes(dutyDateStart, dutyDateEnd);
            int i1 = Integer.parseInt(subTimes);
            int i2 = i1/2;
            String s = DateUtil.addMinutes(dutyDateStart, i2);
            currentDateTime = DateUtil.strToLocalDateTime(s);
        }else{
            currentDateTime = getCurrentTime();
        }
        //调用duty的接口，返回结果集 拆出patternId和当前时间 用这两个约束一条check
        ResponseEntity<SchedulingVo> resultVo = shedulingClient.findShiftPattId(currentDateTime);
        SchedulingVo schedulingVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //无班次patternId,查询Global获取patternId
        String shiftPatternId;
        if(schedulingVo == null){
            shiftPatternId = NangangGlobal.SHIFT_PATTERN_ID;
        }else {
            shiftPatternId = schedulingVo.getShiftPatternId();
        }
        //把两个条件封装到Vo里边，判断这条记录是否存在
        LocalDate localDate = LocalDate.from(currentDateTime);
        DailyCheckVo vo = new DailyCheckVo();
        vo.setCheckDate(localDate);
        vo.setShiftPatternId(shiftPatternId);
        // 查询这个班次对应的dailycheck记录是否存在
        DailyCheckVo dailyCheckVoOne =  client.exists(vo);
        if(dailyCheckVoOne!=null){
            // 存在：根据dailycheckid  返回一个items集合
            ResponseEntity<List<DailyCheckItemsVo>> resultVos = client.selectItem(vo);
            dailyCheckItemsVos = ResponseEntityUtils.achieveResponseEntityBody(resultVos);
            /**
             * dailyCheckItemsVo里新加字段dailyLogId 关联了dailyLog表里的数据。此处，根据返回的dailyCheckItemsVos遍历所得id去查中间表，
             * itemId若对应有dailyLogId 则把dailyLogId 赋值给dailyCheckItemsVo的dailyLogId，前端拿这个判断按钮的灰or亮
             */
            //拼接前台数据dailyCheck
            ResponseEntity<DailyCheckVo> dailyCheckVos = client.findOne(vo);
            DailyCheckVo dailyCheckVo = ResponseEntityUtils.achieveResponseEntityBody(dailyCheckVos);
            for (DailyCheckItemsVo dailyCheckItemsVo : dailyCheckItemsVos) {
                String dailyCheckItemsVoId = dailyCheckItemsVo.getId();
                ResponseEntity<String> resultDailyLogId = itemsClient.findDailyLogId(dailyCheckItemsVoId);
                String dailyLogId = ResponseEntityUtils.achieveResponseEntityBody(resultDailyLogId);
                //返回结果可以是null，赋值为null就好，至此，返回结果就有dailyLogId了
                dailyCheckItemsVo.setDailyLogId(dailyLogId);
                if (null != dailyCheckVo){
                    dailyCheckItemsVo.setCheckDate(dailyCheckVo.getCheckDate());
                    dailyCheckItemsVo.setCheckShiftPattern(dailyCheckVo.getShiftPatternId());
                }
            }
        }else{
            /**
             * 不存在：新建一条dailyCheck，绑定上patternId，查出新增的checkId
             * 查出字典项，保存成一个list 设置上dailyCheckId，保存上这个集合 展示
             */

            if(schedulingVo == null){
                vo.setShiftPatternName(NangangGlobal.SHIFT_PATTERN_NAME);
            }else {
                vo.setShiftPatternName(schedulingVo.getShiftPatternName());
            }
            vo.setIsShift(NangangGlobal.IS_SHIFT_NO);
            DailyCheckVo dailyCheckVo1 = ResponseEntityUtils.achieveResponseEntityBody(client.save(vo));
            //ResponseEntity<DailyCheckVo> dailyCheckVo = client.findOne(vo);
            //DailyCheckVo checkVo = ResponseEntityUtils.achieveResponseEntityBody(dailyCheckVo);
            String dailyCheckId = dailyCheckVo1.getId();
            //查出对应的字典项，赋值给item的list
            List<DicGroupItemVo> items = findItems().stream()
                    .sorted(Comparator.comparing(DicGroupItemVo::getOrders))
                    .collect(Collectors.toList());
            List<DailyCheckItemsVo> dailyCheckItems = new ArrayList<>();
            if(!CollectionUtils.isEmpty(items)){
                for (int i = 0; i < items.size(); i++) {
                    String itemName = items.get(i).getItemName();
                    String itemId = items.get(i).getId();
                    DailyCheckItemsVo checkItemsVo = new DailyCheckItemsVo();
                    //初始化信息应该还要加的
                    checkItemsVo.setCheckItemContent(itemName);
                    checkItemsVo.setDailycheckId(dailyCheckId);
                    checkItemsVo.setCreateBy(account);
                    checkItemsVo.setCheckResult("0");
                    //添加checkItemId
                    checkItemsVo.setCheckItemId(itemId);
                    dailyCheckItems.add(checkItemsVo);
                }
            }
            ResponseEntity<List<DailyCheckItemsVo>> listResponseEntity = client.saveByList(dailyCheckItems);
            //保存返回的vo的checkResult是null，应该在vo设置初始值0
            dailyCheckItemsVos = ResponseEntityUtils.achieveResponseEntityBody(listResponseEntity);
        }
        dailyCheckItemsListVo.setDailyCheck(dailyCheckVoOne);
        dailyCheckItemsListVo.setDailyCheckItems(dailyCheckItemsVos);
        return dailyCheckItemsListVo;
    }

    /**
     * 根据条件查询工作检查管理列表-分页
     */
    public RestPageImpl<DailyCheckVo> findPage(DailyCheckPageVo dailyCheckPageVo) {

        Assert.notNull(dailyCheckPageVo,"lawPageVo 不能为空");
        dailyCheckPageVo.setTodayDate(getCurrentLocalDate());
        ResponseEntity<RestPageImpl<DailyCheckVo>> resultVo = client.findPage(dailyCheckPageVo);
        RestPageImpl<DailyCheckVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 新增值班日志（关联检查项id）
     * @param vo
     * @param principal
     */
    public void addDailyLog(DailyCheckDailyLogVo vo, Principal principal) {

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = userProfileVo.getName();

        DailyLogVo dailyLogVo = vo.getDailyLog();
        dailyLogVo.setCreateBy(account);
        dailyLogVo.setUpdateBy(account);
        String checkItemId = vo.getCheckItemId();

        boolean notBlank = StringUtils.isNotBlank(checkItemId);
        Assert.isTrue(notBlank ,"检查项ID 不能为空格或空字符串" );

        //远程dailyCheckDailyLogClient 完成dialylog的保存
        ResponseEntity<DailyLogVo> dailyLogVoResponseEntity = dailyCheckDailyLogClient.create(dailyLogVo);
        DailyLogVo result = ResponseEntityUtils.achieveResponseEntityBody(dailyLogVoResponseEntity);
        //本地的client
        vo.getDailyLog().setId(result.getId());
        ResponseEntity<DailyCheckDailyLogVo> resultVo = client.addDailyLog(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    public static String getSubTimes(String startTime,String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = df.parse(endTime);
            Date d2 = df.parse(startTime);
            long diff = d1.getTime() - d2.getTime();
            long days = diff / 86400000L;
            long hours = (diff - days * 86400000L) / 3600000L;
            long minutes = (diff - days * 86400000L - hours * 3600000L) / 60000L;
            //return days + "天" + hours + "小时" + minutes + "分";
            return minutes+"";
        } catch (Exception var13) {
            var13.printStackTrace();
            return "";
        }
    }

    public  LocalDate getCurrentLocalDate(){
        LocalDateTime currentDateTime = utilsFeignClient.now().getBody();
        String dateTimeStr = DateUtil.getDateTimeStr(currentDateTime);
        LocalDate localDate = DateUtil.strToLocalDate(dateTimeStr);
        return localDate;
    }

}
