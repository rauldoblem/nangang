package com.taiji.emp.base.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


/**
 * 通讯录组 与 通讯录信息 中间表 实体类
 * @author SunYi
 * @date 2018年10月22日
 */
public class ContactMidVo extends IdVo<String> {

    public ContactMidVo(){}

    /**
     * 组ID
     */
    @Getter
    @Setter
    @NotEmpty(message = "通讯录组id teamId 不能为空")
    @Length(max = 36,message = "通讯录组ID teamId字段最大长度36")
    private String teamId;

    /**
     * 组织名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "组织名称 teamId字段最大长度50")
    private String teamName;

    /**
     * 人员ID
     */
    @Getter
    @Setter
    @NotEmpty(message = "人员信息id contact 不能为空")
    private ContactVo contact;

    /**
     * 人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "人员姓名 addrName字段最大长度50")
    private String addrName;

    /**
     * 人员ID集合
     */
    @Getter
    @Setter
    private List<String> contactIdList;

}
