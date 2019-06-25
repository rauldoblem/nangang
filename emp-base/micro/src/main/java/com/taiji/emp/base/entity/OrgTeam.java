package com.taiji.emp.base.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yhcookie
 * @date 2018/12/28 9:24
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BS_ORG_TEAM")
public class OrgTeam extends BaseEntity<String> implements DelFlag {

    @Length(max = 100,message = "分组名字字段最大长度100")
    @NotEmpty(message = "分组名字不能为空")
    private String teamName;

    @Length(max = 8,message = "排序字段最大长度8")
    private String orders;

    @Length(max = 36,message = "创建分组的用户ID字段最大长度36")
    @NotEmpty(message = "用户id不能为空")
    private String createUserId;

    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
