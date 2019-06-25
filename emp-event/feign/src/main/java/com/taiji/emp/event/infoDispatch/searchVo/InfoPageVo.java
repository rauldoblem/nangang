package com.taiji.emp.event.infoDispatch.searchVo;

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

/**
 * 报送信息分页查询类InfoPageVo
 * @author qizhijie-pc
 * @date 2018年10月23日15:24:59
 */
public class InfoPageVo extends BasePageVo{

    public InfoPageVo(){}

    /**
     * 查询选择标签类型：00信息录入，01接收待办；10信息已办，11接收已办
     * TYPE为00，则IM_ACCEPT_DEAL中CREATE_ORG_ID为当前登录者所属单位ID，且DEAL_FLAG为0；
     * TYPE为01，则IM_ACCEPT_DEAL中DEAL_ORG_ID为当前登录者所属单位ID，且DEAL_FLAG为0；
     * TYPE为10，则IM_ACCEPT_DEAL中CREATE_ORG_ID为当前登录者所属单位ID，且DEAL_FLAG不等于0；
     * TYPE为11，则IM_ACCEPT_DEAL中DEAL_ORG_ID为当前登录者所属单位ID，且DEAL_DEAL_FLAG不等于0；
     * 获取ACCEPT_ID集合后再根据其他查询条件查询IM_ACCEPT表中对应的具体接报信息返回
     */
    @Getter@Setter
//    @NotEmpty(message = "buttonType 不能为空")
    private String buttonType;

    //信息名称
    @Getter@Setter
    private String eventName;

    //事件类型选择Ids
    @Getter@Setter
    private List<String> eventTypeIds;

    //事件等级
    @Getter@Setter
    private String eventGradeId;

    //事发时间——查询开始时间
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
    @Getter@Setter
    private LocalDateTime startDate;

    //事发时间——查询结束时间
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
    @Getter@Setter
    private LocalDateTime endDate;

    //创建单位Id
    @Getter@Setter
    private String createOrgId;

    @Getter@Setter
    private List<String> buttonTypeList;

}
