//package com.taiji.emp.nangang.service;
//
//import com.taiji.emp.nangang.entity.PassRecordVehicle;
//import com.taiji.emp.nangang.repository.PassRecordVehicleRepository;
//import com.taiji.emp.nangang.repository.StatVehicleRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
///**
// * @author yhcookie
// * @date 2018/12/5 10:54
// */
//@Slf4j
//@Service
//@AllArgsConstructor
//public class VehicleRecordService {
//
//    @Autowired
//    private PassRecordVehicleRepository repository;
//    @Autowired
//    private StatVehicleRepository statVehicleRepository;
//
//    /**
//     * 从封闭系统触发过来表NG_STAT_VEHICLE中查询需要的数据
//     * @return List<PassRecordVehicle>
//     */
//    public List<PassRecordVehicle> excuteSql() {
//        //repository执行sql返回查询结果
//        List<PassRecordVehicle> vehicleRecords = statVehicleRepository.findVehicleRecord();
//        boolean empty = CollectionUtils.isEmpty(vehicleRecords);
//        Assert.isTrue(!empty , "List<VehicleRecord>查询结果为null");
//        return vehicleRecords;
//    }
//
//    /**
//     * 删掉之前的数据，保存新的数据
//     * @param entitys
//     */
//    public void save(List<PassRecordVehicle> entitys){
//        boolean empty = CollectionUtils.isEmpty(entitys);
//        Assert.isTrue(!empty , "入参List<VehicleRecord>不能为空");
//        //删掉上一次查询的数据
//        repository.deleteAll();
//        //重新存入表中
//        repository.save(entitys);
//    }
//
//}
