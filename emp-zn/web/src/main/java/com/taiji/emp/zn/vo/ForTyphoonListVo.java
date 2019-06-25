package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/23 20:48
 */
@Setter
@Getter
@NoArgsConstructor
public class ForTyphoonListVo {

    @Length(max = 36,message = "status字段最大长度50")
    private String xuHao;

    @Length(max = 50,message = "chnName字段最大长度50")
    private String chnName;
}
