package com.taiji.emp.nangang.vo;

import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
public class DailyCheckDailyLogVo {

    @Length(max = 36,message = "检查项checkItemID字段最大长度36")
    private String checkItemId;

    private DailyLogVo dailyLog;
}
