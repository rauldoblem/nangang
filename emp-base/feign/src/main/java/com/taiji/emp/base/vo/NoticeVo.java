package com.taiji.emp.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 通知公告实体类 feign NoticeVo
 * @author qzp-pc
 * @date 2018年10月21日14:09:03
 */
public class NoticeVo extends BaseVo<String> {

    public NoticeVo() {}

    /**
     * 通知名称
     */
    @Getter
    @Setter
    @Length(max = 200,message = "通知名称title最大长度200")
    private String title;

    /**
     * 通知类型ID
     */
    @Getter
    @Setter
    private String noticeTypeId;

    /**
     * 通知类型名称
     */
    @Getter
    @Setter
    private String noticeTypeName;

    /**
     * 内容
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "内容content最大长度2000")
    private String content;

    /**
     * 创建单位ID
     */
    @Getter
    @Setter
    private String buildOrgId;

    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    private String buildOrgName;

    /**
     * 下发状态：0未下发，1已下发
     */
    @Getter
    @Setter
    @Length(max = 1,message = "下发状态sendStatus最大长度1")
    private String sendStatus;

    /**
     * 下发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime sendTime;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注content最大长度2000")
    private String notes;

}
