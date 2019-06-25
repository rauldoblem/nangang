package com.taiji.emp.base.vo;

import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 通讯录组 管理实体类Vo  ContactTeamVo
 * @author SunYi
 * @date 2018年10月22日
 */
public class ContactTeamVo extends IdVo<String> implements ITreeNode<ContactTeamVo> {

    public ContactTeamVo(){}

    /**
     * 组名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "组名 teamName字段最大长度36")
    private String teamName;

    /**
     * 当前用户ID
     */
    @Getter
    @Setter
    @Length(max = 36, message = "当前用户ID userId 字段最大长度36")
    private String userId;

    /**
     * 父节点ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父节点ID parentId 字段最大长度36")
    private String parentId;

    /**
     * 是否叶子节点（0：是；1：不是）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否叶子节点 leaf 字段最大长度1")
    private String leaf;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
    /**
     * 子节点数据
     */

    @Getter
    @Setter
    private List<ContactTeamVo> children;

    /**
     *排序
     */
    @Getter
    @Setter
    private Integer orders;
}
