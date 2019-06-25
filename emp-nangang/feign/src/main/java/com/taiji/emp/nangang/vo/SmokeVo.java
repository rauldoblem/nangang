package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/10 11:36
 */
@NoArgsConstructor
public class SmokeVo {

    @Getter
    @Setter
    @Length(max = 50,message = "So2折算值字段最大长度50")
    private String so2converted;

    @Getter
    @Setter
    @Length(max = 50,message = "NO折算值字段最大长度50")
    private String noconverted;

    @Getter
    @Setter
    @Length(max = 50,message = "Smoke折算值字段最大长度50")
    private String smokeconverted;

    @Getter
    @Setter
    @Length(max = 50,message = "HCl折算值字段最大长度50")
    private String hclconverted;

    @Getter
    @Setter
    @Length(max = 50,message = "流量字段最大长度50")
    private String flow;

    @Getter
    @Setter
    @Length(max = 50,message = "流量字段最大长度50")
    private String createTime;

    private List<String> value;

    public List<String> getValue(){
        List<String> value = new ArrayList<>();
        value.add(so2converted);
        value.add(noconverted);
        value.add(smokeconverted);
        value.add(hclconverted);
        value.add(flow);
        return value;
    }
}
