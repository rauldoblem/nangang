package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 应急处置-- 应急机构vo
 * @author qizhijie-pc
 * @date 2018年11月5日14:01:29
 */
public class CmdOrgVo extends BaseVo<String> implements ITreeNode<CmdOrgVo> {

    public CmdOrgVo(){}

    //方案Id
    @Getter
    @Setter
    @Length(max =36,message = "schemeId 最大长度不能超过36")
    @NotEmpty(message = "schemeId 不能为空字符串")
    private String schemeId;

    //预案ID
    @Getter
    @Setter
    @Length(max =36,message = "planId 最大长度不能超过36")
    private String planId;

    //应急组织名称
    @Getter
    @Setter
    @Length(max =100,message = "name 最大长度不能超过100")
    private String name;

    //父节点
    @Getter
    @Setter
    @Length(max =36,message = "parentId 最大长度不能超过36")
    private String parentId;

    //排序号
    @Getter
    @Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer orders;

    //是否叶子节点
    @Getter
    @Setter
    @Length(max =1,message = "leaf 最大长度不能超过1")
    private String leaf;

    //子节点对象
    @Getter
    @Setter
    private List<CmdOrgVo> children;

    //预案应急机构id
    @Getter
    @Setter
    private String planOrgId;

}
