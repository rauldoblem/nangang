package com.taiji.emp.base.searchVo.telrecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taiji.emp.base.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TelrecordPageVo extends BasePageVo {
    @Getter
    @Setter
    private String caller;
    @Getter
    @Setter
    private String callee;
    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    @DateTimeFormat
    @JsonFormat
    @JsonDeserialize
    @JsonSerialize
    private LocalDateTime callBeginTime;
    @Getter
    @Setter
    @DateTimeFormat
    @JsonFormat
    @JsonDeserialize
    @JsonSerialize
    private LocalDateTime callEndTime;
}
