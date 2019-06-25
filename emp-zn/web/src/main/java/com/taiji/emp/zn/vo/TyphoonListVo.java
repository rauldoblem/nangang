package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/23 20:45
 */
@Setter
@Getter
@NoArgsConstructor
public class TyphoonListVo {

    @Length(max = 36,message = "code字段最大长度50")
    private String code;

    @Length(max = 36,message = "msg字段最大长度50")
    private String msg;

    private List<ForTyphoonListVo> data;

}
