package com.taiji.emp.event.infoDispatch.vo;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 初报 续报，数据拆分
 * @author qizhijie-pc
 */
public class AcceptIsFirstOrNotFirst{

    public AcceptIsFirstOrNotFirst(){}

    /**
     * 初报
     */
    @Getter@Setter
    public List<AcceptVo> firstIMAccept;

    /**
     * 续报
     */
    @Getter@Setter
    public List<AcceptVo> resubmits;

}
