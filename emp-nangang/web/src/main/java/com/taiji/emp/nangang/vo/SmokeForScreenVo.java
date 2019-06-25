package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/27 10:51
 */
@Setter
@Getter
@NoArgsConstructor
public class SmokeForScreenVo {

    private List<SmokeInfoVo> series;

    //数据的时间集合
    private List<String> xaxis;
}
