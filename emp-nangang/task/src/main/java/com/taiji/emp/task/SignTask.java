package com.taiji.emp.task;

import com.taiji.emp.nangang.service.DailyCheckService;
import com.taiji.emp.nangang.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 值班人员签到、签出
 * @author qzp
 * @date 2019/01/31
 */
@Slf4j
@EnableScheduling
@Component
public class SignTask {


    @Autowired
    SignService signService;

    @Autowired
    DailyCheckService dailyCheckService;
    /**
     * 上午7:30 白班 8:00-17:30
     */
    @Scheduled(cron = "0 30 07 ? * *")
    public void signInDay (){
        signService.signInDay();
    }

    /**
     * 下午16:30  夜班 17:30-8:00
     */
    @Scheduled(cron = "0 30 16 ? * *")
    public void signInNight (){
        log.info("执行....");
        signService.signInNight();
    }

    /**
     * 工作检查单定时任务
     */
    @Scheduled(cron = "0 30 07,16 ? * *")
    //@Scheduled(fixedRate = 30000)
    public void reportCurrent(){
        dailyCheckService.getCheckItemScheduled();
    }

}
