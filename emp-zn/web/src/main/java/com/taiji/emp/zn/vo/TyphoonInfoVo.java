package com.taiji.emp.zn.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/22 9:10
 */
@Getter
@Setter
@NoArgsConstructor
public class TyphoonInfoVo {

    @Length(max = 50,message = "状态码字段最大长度50")
    private String name;

    private List<String> value = new ArrayList<>();

}
