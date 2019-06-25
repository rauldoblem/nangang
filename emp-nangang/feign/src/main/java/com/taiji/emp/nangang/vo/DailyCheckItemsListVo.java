package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class DailyCheckItemsListVo{

    @Getter
    @Setter
    private DailyCheckVo dailyCheck;

    @Setter@Getter
    private List<DailyCheckItemsVo> dailyCheckItems;
}
