package com.taiji.emp.alarm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

//通知发送方查看反馈，反馈列表包装类
public class AlertFeedbacks {

    public AlertFeedbacks(){}

    public AlertFeedbacks(String receiveOrgName,String content,LocalDateTime feedbackLasttime,String feedbackStatus,List<String> feedbackIds){
        this.receiveOrgName = receiveOrgName;
        this.content = content;
        this.feedbackLasttime = feedbackLasttime;
        this.feedbackStatus = feedbackStatus;
        this.feedbackIds = feedbackIds;
    }

    //反馈单位
    @Getter@Setter
    private String receiveOrgName;

    //反馈内容
    @Getter@Setter
    private String content;

    //最新反馈时间
    @Getter@Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    private LocalDateTime feedbackLasttime;

    //反馈状态
    @Getter@Setter
    private String feedbackStatus;

    //所有反馈ids
    @Getter@Setter
    private List<String> feedbackIds;
}
