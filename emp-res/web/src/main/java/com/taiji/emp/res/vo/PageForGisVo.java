package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * Hazard、Material、Support、Target、Team通用的PageForGisVo
 * @author yhcookie
 * @date 2019/1/4 14:18
 */
@Getter
@Setter
@NoArgsConstructor
public class PageForGisVo {

    private int page;

    private int size;

    @Length(max = 30,message = "浙能组织机构code字段最大长度30")
    private String orgCode;
}
