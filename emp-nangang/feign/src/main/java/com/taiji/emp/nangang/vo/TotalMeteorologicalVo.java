package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/17 13:08
 */
@Getter
@Setter
@NoArgsConstructor
public class TotalMeteorologicalVo {

    private List<String> temperature = new ArrayList<>();

    private List<String> time = new ArrayList<>();
}
