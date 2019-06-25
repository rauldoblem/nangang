package com.taiji.emp.res.searchVo.knowledge;

import com.taiji.emp.res.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 应急知识分页查询项 对象
 * @author qizhijie-pc
 * @date 2018年10月12日11:22:04
 */
public class KnowPageVo extends BasePageVo {
    
    @Getter@Setter
    private String title;

    @Getter@Setter
    private List<String> eventTypeIds;

    @Getter@Setter
    private String keyWord;

    @Getter@Setter
    private String knoTypeId;

    @Getter@Setter
    private String createOrgId;

}
