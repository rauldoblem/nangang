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

public class FaxVo extends BaseVo<String> {
    public FaxVo (){}
    @Getter
    @Setter
    @Length(max = 200,message = "传真主题title字段最大长度200")
    private String title;
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
    @Length(max = 260,message = "传真文件名（绝对路径，TIF格式）fileName字段最大长度260")
    private String fileName;
    @Getter
    @Setter
    private int page;
    @Getter
    @Setter
    @Length(max = 1000,message = "文件地址filePath字段最大长度1000")
    private String filePath;
    @Getter
    @Setter
    @Length(max = 200,message = "备注notes字段最大长度200")
    private String notes;
    @Getter
    @Setter
    @Length(max = 1,message = "发送标识sendflag字段最大长度1")
    private String sendflag;
}
