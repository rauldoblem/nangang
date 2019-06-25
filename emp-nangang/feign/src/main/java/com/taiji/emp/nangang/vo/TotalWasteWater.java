package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/17 17:23
 */
@Setter
@Getter
@NoArgsConstructor
public class TotalWasteWater {

    private List<String> xAxis = Arrays.asList("PH值","COD","氨氮","总氮","总磷");

    private List<String> data = new ArrayList<>();
}
