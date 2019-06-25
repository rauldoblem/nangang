package com.taiji.emp.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.IdEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DOC_ATTACHMENT")
public class DocAttachment extends IdEntity<String>implements DelFlag{

    public DocAttachment(){}

    /**
     * 业务主键id
     */
    @Getter
    @Setter
    @Column(name="YWID")
    @Length(max = 36,message = "业务主键id ywid长度不能超过36")
    private String ywid;

    /**
     * 文件名
     */
    @Getter@Setter
    @Length(max = 100,message = "文件名name长度不能超过100")
    private String name;

    /**
     * 文件类型
     */
    @Getter@Setter
    @Length(max = 20,message = "文件类型type长度不能超过20")
    private String type;

    /**
     * 文件后缀
     */
    @Getter@Setter
    @Length(max = 10,message = "文件后缀suffix长度不能超过10")
    private String suffix;

    /**
     * 存储相对地址
     */
    @Getter@Setter
    @Length(max = 200,message = "存储相对地址location长度不能超过200")
    private String location;

    /**
     * 上传时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @CreatedDate
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
    @Getter@Setter
    @Column(name="UPLOAD_TIME")
    private LocalDateTime uploadTime;

    /**
     * 上传用户id
     */
    @Getter@Setter
    @Column(name = "UPLOAD_USER_ID")
    @Length(max = 36,message = "上传用户id uploadUserId长度不能超过36")
    private String uploadUserId;

    /**
     * 上传用户姓名
     */
    @Getter@Setter
    @Column(name = "UPLOAD_USER_NAME")
    @Length(max = 20,message = "上传用户姓名 uploadUserName长度不能超过20")
    private String uploadUserName;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
