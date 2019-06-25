package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/21 22:38
 */
@Setter
@Getter
@NoArgsConstructor
public class ShipVo {

    @Length(max = 50,message = "船只唯一标识字段最大长度50")
    private String mmsi;

    @Length(max = 50,message = "船只名字标识字段最大长度50")
    private String name;
}
