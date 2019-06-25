package com.taiji.emp.base.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yhcookie
 * @date 2018/12/28 9:31
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BS_ORG_TEAM_MID")
public class OrgTeamMid {

    @Id
    @GenericGenerator(
            name = "customUUIDGenerator",
            strategy = "com.taiji.micro.common.id.CustomSortUUIDGenerator"
    )
    @GeneratedValue(
            generator = "customUUIDGenerator"
    )
    @Length(max = 36,message = "id字段最大长度36")
    public String id;

    @Length(max = 36,message = "分组id字段最大长度36")
    private String teamId;

    @Length(max = 100,message = "分组名字字段最大长度100")
    private String teamName;

    @Length(max = 36,message = "该分组内的组织机构Id字段最大长度36")
    private String orgId;

    @Length(max = 100,message = "该分组内的组织机构名字字段最大长度100")
    private String orgName;
}
