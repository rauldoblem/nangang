package com.taiji.emp.nangang.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/27 12:26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmokeInfoVo {

    @Length(max = 20,message = "名称字段最长20")
    private String name;

    private List<String> data;
}
