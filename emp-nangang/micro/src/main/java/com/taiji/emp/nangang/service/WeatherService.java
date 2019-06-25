package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.Weather;
import com.taiji.emp.nangang.repository.WeatherRepository;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 15:53
 */
@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

    @Autowired
    private WeatherRepository repository;

    public List<Weather> fineAll() {
        //获取当前时间
        Date date = new Date();
        String time = DateUtil.dateToString(date,"dd");
        List<Weather> list = repository.findAll(time);
        return list;

    }

    public List<Weather> saveAll(List<Weather> list) {
        boolean empty = CollectionUtils.isEmpty(list);
        List<Weather> saveResult = null;
        if(!empty){
            repository.deleteAll();
            saveResult = repository.save(list);
        }
        return saveResult;
    }

//    /**
//     * 检查 job是否已经在其他服务器执行，已执行返回true
//     */
//    public boolean checkJob() {
//        boolean checkResult = repository.checkJob();
//        return checkResult;
//    }
}
