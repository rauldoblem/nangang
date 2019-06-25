package com.taiji.emp.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class SendfaxVo {
    public SendfaxVo(){}
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
    private LocalDateTime sendTimeStart;
    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sendTimeEnd;
    @Getter
    @Setter
    @Length(max = 50,message = "发送状态status字段最大长度50")
    private String status;
    @Getter
    @Setter
    @Length(max = 50,message = "发送结果码retcode字段最大长度50")
    private String retcode;
    @Getter
    @Setter
    @Length(max = 1000,message = "文件地址（传真文件的路径）filePath字段最大长度1000")
    private String filePath;
}
