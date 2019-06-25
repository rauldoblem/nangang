package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FinalSurfaceWaterVo {
    public List<TotalSurfaceWaterVo> totalSurfaceWaterVo;
    public String createTime;
}
