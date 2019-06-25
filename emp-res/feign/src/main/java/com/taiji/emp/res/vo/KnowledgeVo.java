package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 应急知识 feign KnowledgeVo
 * @author qizhijie-pc
 * @date 2018年9月18日17:24:06
 */
public class KnowledgeVo extends BaseVo<String>{

    public KnowledgeVo(){}

    /**
     * 标题
     */
    @Getter
    @Setter
    @Length(max = 100,message = "标题title字段最大长度100")
    private String title;

    /**
     * 知识类型ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "知识类型ID knoTypeId字段最大长度36")
    private String knoTypeId;

    /**
     * 知识类型名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "知识类型名称knoTypeName字段最大长度100")
    private String knoTypeName;

    /**
     * 适用事件分类ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "适用事件分类ID eventTypeId字段最大长度100")
    private String eventTypeId;

    /**
     * 适用事件类型名称
     */
    @Getter
    @Setter
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
    @Length(max = 100,message = "主题词keyWord字段最大长度100")
    private String keyWord;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String content;

    /**
     * 创建单位编码
     */
    @Getter
    @Setter
    @Length(max = 36,message = "创建单位ID createOrgId字段最大长度36")
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "创建单位名称 createOrgName字段最大长度100")
    private String createOrgName;

}
