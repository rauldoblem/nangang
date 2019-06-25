package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/13 16:51
 */
@Setter
@Getter
@NoArgsConstructor
public class BayonetFlowForTotalVehicleVo {

    @Length(max = 50 ,message = "卡口名称字段最大长度50")
    private String bayonetName;

    private String type = "bar";

    private List<String> data;

}
