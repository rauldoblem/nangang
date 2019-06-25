package com.taiji.emp.nangang.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.common.constant.SignGlobal;
import com.taiji.emp.nangang.feign.DailyCheckClient;
import com.taiji.emp.nangang.feign.SchedulingTaskClient;
import com.taiji.emp.nangang.feign.SignTaskClient;
import com.taiji.emp.nangang.vo.DailyCheckItemsListVo;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import com.taiji.emp.nangang.vo.DailyCheckVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyCheckService extends BaseService  {

    @Autowired
    SchedulingTaskClient schedulingTaskClient;
    @Autowired
    SignTaskClient signTaskClient;
    @Autowired
    private DailyCheckClient dailyCheckClient;



    /**
     * 定时任务用
     * 调用duty的接口获取检查记录,首页使用，从值班记录表中查询，
     * 若没有，则用数据字典（查询）初始化
     */
    public DailyCheckItemsListVo getCheckItemScheduled() {
        String account = SignGlobal.USER_NAME;
        DailyCheckItemsListVo dailyCheckItemsListVo = new DailyCheckItemsListVo();
        List<DailyCheckItemsVo> dailyCheckItemsVos;
        //调用duty的接口，返回结果集 拆出patternId和当前时间 用这两个约束一条check
        LocalDateTime currentDateTime = LocalDateTime.now().plusHours(1);
        ResponseEntity<SchedulingVo> resultVo = schedulingTaskClient.findShiftPattId(currentDateTime);
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
        DailyCheckVo dailyCheckVoOne =  dailyCheckClient.exists(vo);
        if(dailyCheckVoOne!=null){
            // 存在：根据dailycheckid  返回一个items集合
            ResponseEntity<List<DailyCheckItemsVo>> resultVos = dailyCheckClient.selectItem(vo);
            dailyCheckItemsVos = ResponseEntityUtils.achieveResponseEntityBody(resultVos);
            /**
             * dailyCheckItemsVo里新加字段dailyLogId 关联了dailyLog表里的数据。此处，根据返回的dailyCheckItemsVos遍历所得id去查中间表，
             * itemId若对应有dailyLogId 则把dailyLogId 赋值给dailyCheckItemsVo的dailyLogId，前端拿这个判断按钮的灰or亮
             */
            //拼接前台数据dailyCheck
            ResponseEntity<DailyCheckVo> dailyCheckVos = dailyCheckClient.findOne(vo);
            DailyCheckVo dailyCheckVo = ResponseEntityUtils.achieveResponseEntityBody(dailyCheckVos);
            for (DailyCheckItemsVo dailyCheckItemsVo : dailyCheckItemsVos) {
                String dailyCheckItemsVoId = dailyCheckItemsVo.getId();
                ResponseEntity<String> resultDailyLogId = signTaskClient.findTaskDailyLogId(dailyCheckItemsVoId);
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
            DailyCheckVo dailyCheckVo1 = ResponseEntityUtils.achieveResponseEntityBody(dailyCheckClient.save(vo));
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
            ResponseEntity<List<DailyCheckItemsVo>> listResponseEntity = dailyCheckClient.saveByList(dailyCheckItems);
            //保存返回的vo的checkResult是null，应该在vo设置初始值0
            dailyCheckItemsVos = ResponseEntityUtils.achieveResponseEntityBody(listResponseEntity);
        }
        dailyCheckItemsListVo.setDailyCheck(dailyCheckVoOne);
        dailyCheckItemsListVo.setDailyCheckItems(dailyCheckItemsVos);
        return dailyCheckItemsListVo;
    }

}
