package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2019/1/5 9:43
 */
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class DamListInfoVo {

    @Length(max = 50,message = "大坝name字段最大长度50")
    private String name;

    @Length(max = 36,message = "大坝code字段最大长度36")
    private String code;

    //经度
    private Double longitude;

    //纬度
    private Double latitude;

    @Length(max = 50,message = "所属组织名称字段最大长度50")
    private String orgName;

    @Length(max = 50,message = "所属组织全名字段最大长度50")
    private String orgFullName;

    @Length(max = 50,message = "所属组织code字段最大长度50")
    private String orgCode;
}
