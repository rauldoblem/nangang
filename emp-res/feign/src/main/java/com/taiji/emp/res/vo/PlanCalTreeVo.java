package com.taiji.emp.res.vo;

import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 预案体系(预案树)
 */
public class PlanCalTreeVo extends IdVo<String> implements ITreeNode<PlanCalTreeVo> {

    public PlanCalTreeVo() {}

    /**
     * 目录名称
     */
    @Getter
    @Setter
    private String calName;

    /**
     * 父节点ID
     */
    @Getter@Setter
    private String parentId;

    /**
     * 是否叶子节点：0否，1是
     */
    @Getter@Setter
    private String leaf;

    /**
     * 排序
     */
    @Getter@Setter
    private Integer orders;

    @Getter@Setter
    private List<PlanBaseVo> planBaseList;

    /**
     * 节点数量
     */
    @Getter@Setter
    private Integer planNums;

    /**
     * 子节点对象
     */
    @Getter@Setter
    private List<PlanCalTreeVo> children;
}
