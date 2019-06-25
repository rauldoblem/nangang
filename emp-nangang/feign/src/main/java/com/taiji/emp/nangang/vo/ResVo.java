package com.taiji.emp.nangang.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter
public class ResVo extends BaseVo<String> {
    @Length(max =  50,message = "")
    private String searchName;
    @Length(max = 50,message = "")
    private String address;
    @Length(max = 64,message = "")
    private String lonAndLat;
    @Length(max = 50,message = "")
    private String principal;
    @Length(max = 50,message = "")
    private String principalTel;
    @Length(max = 1)
    private String tableFlag;
}
