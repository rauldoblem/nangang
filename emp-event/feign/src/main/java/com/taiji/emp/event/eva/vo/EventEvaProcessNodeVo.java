package com.taiji.emp.event.eva.vo;

import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 过程再现流程树实体类 ProcessNode
 * @author SunYi
 * @date 2018年10月30日
 */
public class EventEvaProcessNodeVo extends IdVo<String>  implements ITreeNode<EventEvaProcessNodeVo> {

    public EventEvaProcessNodeVo(){}


    /**
     * 节点名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "节点名称 nodeName 字段最大长度50")
    private String nodeName;
    /**
     * 父ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父ID parentId 字段最大长度36")
    private String parentId;
    /**
     * 是否叶子节点，0否1是
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否叶子节点 leaf字段最大长度1")
    private String leaf;

    /**
     * 排序
     */
    @Getter
    @Setter
    @Length(max = 8,message = "排序 orders 字段最大长度8")
    private Integer orders;

    /**
     *  状态：0无效，1有效
     */
    @Getter
    @Setter
    @Length(max = 1,message = "状态 status 字段最大长度1")
    private String status;


    @Getter
    @Setter
    private EventEvaProcessVo process;

    @Getter@Setter
    private List<String> fileIds;

    @Getter
    @Setter
    private List<EventEvaProcessNodeVo> children;

}
