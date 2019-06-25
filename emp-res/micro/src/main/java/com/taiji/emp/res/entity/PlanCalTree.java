package com.taiji.emp.res.entity;

import com.taiji.micro.common.entity.IdEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 预案体系(预案树)
 * 22号  CRUD
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_PLAN_CALTREE")
public class PlanCalTree extends IdEntity<String> implements DelFlag {
    public PlanCalTree() {}

    /**
     * 目录名称
     */
    @Getter@Setter
    @Length(max = 100,message = "目录名称calName字段最大长度100")
    private String calName;

    /**
     * 父节点ID
     */
    @Getter@Setter
    @Length(max = 36,message = "父节点ID parentId字段最大长度36")
    private String parentId;

    /**
     * 是否叶子节点：0否，1是
     */
    @Getter@Setter
    @Length(max = 1,message = "是否叶子节点leaf字段最大长度1")
    private String leaf;

    /**
     * 排序
     */
    @Getter
    @Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer orders;

    @Getter@Setter
    @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = PlanBase.class)
    @JoinColumn(name="PLAN_CALTREE_ID")
    private List<PlanBase> planBaseList;

    /**
     * 节点数量
     */
    @Getter@Setter
    @Transient
    private Integer planNums;

//    public Integer getPlanNums() {
//        return this.planBaseList.size();
//    }

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
