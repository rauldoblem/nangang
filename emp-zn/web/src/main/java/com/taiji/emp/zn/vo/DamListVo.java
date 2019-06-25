package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2019/1/5 9:43
 */
@Setter
@Getter
@NoArgsConstructor
public class DamListVo {
    @Length(max = 50,message = "状态码字段最大长度50")
    private String code;

    @Length(max = 50,message = "信息字段最大长度50")
    private String msg;

    private List<DamListInfoVo> data;
}
