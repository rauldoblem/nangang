package com.taiji.emp.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_RECVFAX")
public class Recvfax extends BaseEntity<String> implements DelFlag {
    public Recvfax(){}
    @Getter
    @Setter
    @Length(max = 36,message = "国际区号（发送方）ic字段最大长度36")
    private String ic;
    @Getter
    @Setter
    @Length(max = 36,message = "长途区号（发送方）ldc字段最大长度36")
    private String ldc;
    @Getter
    @Setter
    @Length(max = 200,message = "传真主题title字段最大长度200")
    private String title;
    @Getter
    @Setter
    @Length(max = 64,message = "传真号码（发送方）faxNumber字段最大长度64")
    private String faxNumber;
    @Getter
    @Setter
    @Length(max = 100,message = "发件人sender字段最大长度100")
    private String sender;
    @Getter
    @Setter
    @Length(max = 100,message = "收件人receiver字段最大长度100")
    private String receiver;
    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime recvtime;
    @Getter
    @Setter
    @Min(0)
    private Integer page;
    @Getter
    @Setter
    @Length(max = 1000,message = "文件地址filePath字段最大长度1000")
    private String filePath;
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
