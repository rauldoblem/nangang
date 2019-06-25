//package com.taiji.emp.nangang.task;
//
//import com.taiji.emp.nangang.entity.PassRecordVehicle;
//import com.taiji.emp.nangang.service.VehicleRecordService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
///**
// * @author yhcookie
// * @date 2018/12/5 10:33
// */
//@Slf4j
//@EnableScheduling
//@AllArgsConstructor
//@Component
//public class VehicleRecordTask {
//
//    @Autowired
//    private VehicleRecordService service;
//    /**
//     * SpringTask定时任务 保存VehicleRecord
//     * 任务间隔10分钟
//     */
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void saveJob(){
//
//        //执行sql，拿到查询结果List<PassRecordVehicle>
//        List<PassRecordVehicle> results = service.excuteSql();
//        boolean empty = CollectionUtils.isEmpty(results);
//        Assert.isTrue(!empty , "List<VehicleRecord>查询结果为null");
//        //保存到PassRecordVehicle表中
//        service.save(results);
//    }
//
//}
