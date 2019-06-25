package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 仓位表 feign PositionVo
 */
public class PositionVo extends BaseVo<String>{

    public PositionVo(){}

    /**
     *  仓位名称
     */
    @Getter@Setter
    @NotEmpty(message = "仓位名称名称不能为空")
    @Length(max = 100,message = "仓位名称名称name字段最大长度100")
    private String name;

    /**
     *  仓位编码（从外面系统对接的数据）
     */
    @Getter@Setter
    @Length(max = 36,message = "仓位编码code字段最大长度36")
    private String code;

    /**
     *  仓位序号
     */
    @Getter@Setter
    @Min(value=0,message = "仓位序号最小为0")
    @Max(value=9999,message = "仓位序号最大为9999")
    private Integer sortNumber;

    /**
     *  物资库编码
     */
    @Getter@Setter
    @Length(max = 36,message = "物资库编码repertoryId字段最大长度36")
    private String repertoryId;

    /**
     *  物资库名称
     */
    @Getter@Setter
    @Length(max = 100,message = "物资库名称repertoryName字段最大长度100")
    private String repertoryName;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    private String createOrgName;

}
