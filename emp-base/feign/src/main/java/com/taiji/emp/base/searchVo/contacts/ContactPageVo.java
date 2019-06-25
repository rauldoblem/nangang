package com.taiji.emp.base.searchVo.contacts;

import com.taiji.emp.base.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class ContactPageVo extends BasePageVo {
    @Getter@Setter
    private String orgId;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String telephone;
    /*
    * 是否将没有手机号的人员进行过滤标识，0不过滤，1过滤
     * */
    @Getter@Setter
    private String mobileFlag;

    @Getter@Setter
    private String teamId;

    @Getter@Setter
    List<String> orgIds;

}
