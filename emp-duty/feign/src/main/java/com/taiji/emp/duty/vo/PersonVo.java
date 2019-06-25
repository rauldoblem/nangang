package com.taiji.emp.duty.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 值班人员表  feign PersonVo
 */
public class PersonVo extends IdVo<String> {

    public PersonVo() {}

    /**
     *  通讯录ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "通讯录ID addrId字段最大长度36")
    private String addrId;

    /**
     *  通讯录人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "通讯录人员姓名 addrName字段最大长度50")
    private String addrName;

    /**
     *  所在值班分组编码
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所在值班分组编码 dutyTeamId字段最大长度36")
    private String dutyTeamId;

    /**
     *  所在值班分组名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "所在值班分组名称 dutyIName字段最大长度50")
    private String dutyteamName;

    /**
     *  组内排序
     */
    @Getter
    @Setter
    @Min(value=0,message = "组内排序最小为0")
    @Max(value=9999,message = "组内排序最大为9999")
    private Integer orderInTeam;

    @Getter
    @Setter
    @Length(max = 50,message = "创建者名称 createBy字段最大长度50")
    private String createBy;

    @Getter
    @Setter
    @Length(max = 50,message = "创建者名称 createBy字段最大长度50")
    private String orgName;

    @Getter
    @Setter
    @Length(max = 50,message = "创建者名称 createBy字段最大长度50")
    private String mobile;

    @Getter
    @Setter
    @Length(max = 50,message = "创建者名称 createBy字段最大长度50")
    private String telephone;

    /**
     * 创建时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime createTime;

    /**
     *  是否交接班，0否，1是
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否交接班 isSchift字段最大长度1")
    private String isSchift;

    @Getter
    @Setter
    private List<String> addrIds;

    /**
     *  历史值班人员ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "历史值班人员ID hisPersonId字段最大长度36")
    private String hisPersonId;

    /**
     *  历史值班人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "历史值班人员姓名 hisPersonName字段最大长度50")
    private String hisPersonName;

    /**
     *  是否排班标志flag
     */
    @Getter
    @Setter
    private String flag;

}
