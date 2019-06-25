package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用于传输业务id和关联的附件id串
 * @author qizhijie-pc
 * @date 2018年9月28日15:54:37
 */
public class DocEntityVo {

    /**
     * 业务主键id
     */
    @Getter
    @Setter
    @NotEmpty(message = "业务主键id ywid 不能为空")
    @Length(max = 36,message = "业务主键id ywid长度不能超过36")
    private String entityId;

    /**
     * 附件对象id串(待赋值list)
     */
    @Getter
    @Setter
    private List<String> docAttIds;

    /**
     * 附件对象id串(待删除list)
     */
    @Getter
    @Setter
    private List<String> docAttDelIds;

    public DocEntityVo(){}

    public DocEntityVo(String entityId,List<String> docAttIds,List<String> docAttDelIds){
        this.entityId = entityId;
        this.docAttIds = docAttIds;
        this.docAttDelIds = docAttDelIds;
    }
}
