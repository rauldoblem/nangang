package com.taiji.emp.event.redisEva.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 过程再现 流程树 管理实体类 ProcessRedisNode
 * @author SunYi
 * @date 2018年11月20日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SE_PROCESS_NODE")
public class ProcessRedisNode extends IdEntity<String>{

    public ProcessRedisNode(){}


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



}
