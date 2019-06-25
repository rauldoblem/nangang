package com.taiji.emp.base.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

public class ForSmsRecieveVo extends IdVo<String> {
    public ForSmsRecieveVo(){}

    @Getter
    @Setter
    private String receiverNames;

    @Getter
    @Setter
    private String receiverTels;
}
