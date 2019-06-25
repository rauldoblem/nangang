package com.taiji.emp.res.searchVo.planOrgResponDetail;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanOrgResponDetailListVo extends BaseVo {

    /**
     * vo
     */
    @Getter@Setter
    private List<PlanOrgResponDetailVo> planOrgResponDetailVo;

    @Getter@Setter
    private List<String> ids;

}
