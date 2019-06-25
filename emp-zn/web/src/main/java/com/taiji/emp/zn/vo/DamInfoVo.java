package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author yhcookie
 * @date 2018/12/23 16:13
 */
@Setter
@Getter
@NoArgsConstructor
public class DamInfoVo {

    @Length(max = 50,message = "坝上水位字段最大长度50")
    private String onDamLeval;

    @Length(max = 50,message = "坝下水位标识字段最大长度50")
    private String downDamLeval;

    @Length(max = 50,message = "坝上库容字段最大长度50")
    private String damCapacity;

    @Length(max = 50,message = "坝下流量字段最大长度50")
    private String downDamFlow;

    @Length(max = 50,message = "库区雨量字段最大长度50")
    private String rainfall;

    @Length(max = 50,message = "入库流量标识字段最大长度50")
    private String inDamFlow;

    @Length(max = 50,message = "出库流量标识字段最大长度50")
    private String outDamFlow;

    @Length(max = 50,message = "弃水流量标识字段最大长度50")
    private String wastewaterFlow;

    @Length(max = 50,message = "发电流量标识字段最大长度50")
    private String powerFlow;

}
