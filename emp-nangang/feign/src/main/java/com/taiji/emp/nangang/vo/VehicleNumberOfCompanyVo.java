package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/19 17:32
 */
@Getter
@Setter
@NoArgsConstructor
public class VehicleNumberOfCompanyVo {

    @Length(max =50,message = "公司名字字段最大长度50")
    private String name;

    @Length(max =50,message = "车辆数量字段最大长度50")
    private String total;
}
