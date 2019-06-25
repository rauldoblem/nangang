package com.taiji.emp.res.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


/**
 * 应急知识实体类 Knowledge
 * @author qizhijie-pc
 * @date 2018年9月18日17:24:06
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "KL_KNOWLEDGES")
public class Knowledge extends BaseEntity<String> implements DelFlag {

    public Knowledge(){}

    /**
     * 标题
     */
    @Getter
    @Setter
    @NotBlank(message = "标题不能为空字符串")
    @Length(max = 100,message = "标题title字段最大长度100")
    private String title;

    /**
     * 知识类型ID
     */
    @Getter
    @Setter
    @Column(name = "KNO_TYPE_ID")
    @NotBlank(message = "知识类型ID不能为空字符串")
    @Length(max = 36,message = "知识类型ID knoTypeId字段最大长度36")
    private String knoTypeId;

    /**
     * 知识类型名称
     */
    @Getter
    @Setter
//    @NotBlank(message = "知识类型名称不能为空字符串")
    @Column(name = "KNO_TYPE_NAME")
    @Length(max = 100,message = "知识类型名称knoTypeName字段最大长度100")
    private String knoTypeName;

    /**
     * 适用事件分类ID
     */
    @Getter
    @Setter
    @NotBlank(message = "适用事件分类ID不能为空字符串")
    @Column(name = "EVENT_TYPE_ID")
    @Length(max = 36,message = "适用事件分类ID eventTypeId字段最大长度100")
    private String eventTypeId;

    /**
     * 适用事件类型名称
     */
    @Getter
    @Setter
    //    @NotBlank(message = "适用事件类型名称不能为空字符串")
    @Column(name = "EVENT_TYPE_NAME")
    @Length(max = 50,message = "适用事件类型名称 eventTypeName字段最大长度50")
    private String eventTypeName;

    /**
     * 知识来源
     */
    @Getter
    @Setter
    @Length(max = 100,message = "知识类型名称source字段最大长度100")
    private String source;

    /**
     * 主题词
     */
    @Getter
    @Setter
    @Column(name = "KEY_WORD")
    @Length(max = 100,message = "主题词keyWord字段最大长度100")
    private String keyWord;

    /**
     * 内容
     */
    @Getter
    @Setter
    @Lob
    private String content;

    /**
     * 创建单位ID
     */
    @Getter
    @Setter
    @Column(name = "CREATE_ORG_ID")
    @Length(max = 36,message = "创建单位ID createOrgId字段最大长度36")
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    @Column(name = "CREATE_ORG_NAME")
    @Length(max = 100,message = "创建单位名称 createOrgName字段最大长度100")
    private String createOrgName;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
