package com.taiji.emp.res.vo;

import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 预案组织机构管理
 */
public class PlanOrgVo extends BaseVo<String> implements ITreeNode<PlanOrgVo> {

    public PlanOrgVo(){}

    /**
     * 预案ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案ID planId字段最大长度36")
    private String planId;

    /**
     * 响应级别ID（事件等级ID）
     */
    @Getter@Setter
    @Length(max = 36,message = "响应级别ID（事件等级ID）eventGradeID字段最大长度36")
    private String eventGradeId;

    /**
     * 响应级别名称（事件等级名称）
     */
    @Getter@Setter
    @Length(max = 100,message = "响应级别名称（事件等级名称）eventGradeName字段最大长度100")
    private String eventGradeName;

    /**
     * 应急组织名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "应急组织名称name字段最大长度100")
    private String name;

    /**
     * 父节点
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父节点parentId字段最大长度36")
    private String parentId;

    /**
     * 排序号
     */
    @Getter
    @Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer orders;

    /**
     * 是否叶子节点
     */
    @Getter@Setter
    @Length(max = 1,message = "是否叶子节点leaf字段最大长度1")
    private String leaf;

    /**
     * 子节点对象
     */
    @Getter@Setter
    private List<PlanOrgVo> children;

    /**
     * 是否主责：0否，1是
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否主责isMain字段最大长度1")
    private String isMain;
}
