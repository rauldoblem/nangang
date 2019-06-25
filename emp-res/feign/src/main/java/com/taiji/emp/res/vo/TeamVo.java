package com.taiji.emp.res.vo;

import com.taiji.micro.common.validator.Phone;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TeamVo extends BaseVo<String>{

    public TeamVo(){}

    /**
     * 队伍名称
    */
    @Getter@Setter
    @Length(max=100,message = "队伍名称name长度不能超过100")
    @NotEmpty(message = "队伍名称不能为空")
    private String name;

    /**
     * 队伍性质ID
     */
    @Getter@Setter
    @Length(max=36,message = "队伍性质IDpropertyId长度不能超过36")
    private String propertyId;

    /**
     * 队伍性质名称
     */
    @Getter@Setter
    @Length(max=50,message = "队伍性质名称propertyName长度不能超过50")
    private String propertyName;

    /**
     * 队伍类型ID
     */
    @Getter@Setter
    @Length(max=36,message = "队伍类型IDteamTypeId长度不能超过36")
    @NotEmpty(message = "队伍类型ID不能为空")
    private String teamTypeId;

    /**
     * 队伍类型名称
     */
    @Getter@Setter
    @Length(max=50,message = "队伍类型名称teamTypeName长度不能超过50")
//    @NotEmpty(message = "队伍类型不能为空")
    private String teamTypeName;

    /**
     * 所属单位
     */
    @Getter@Setter
    @Length(max=50,message = "所属单位unit长度不能超过50")
    private String unit;

    /**
     * 队伍值班电话
     */
    @Getter@Setter
    @Length(max=50,message = "队伍值班电话dutyTel长度不能超过50")
    @NotEmpty(message = "队伍值班电话不能为空")
    private String dutyTel;

    /**
     * 负责人
     */
    @Getter@Setter
    @Length(max=50,message = "负责人principal长度不能超过50")
    @NotEmpty(message = "负责人不能为空")
    private String principal;

    /**
     * 负责人手机
     */
    @Getter@Setter
    @Length(max=50,message = "负责人手机principalTel长度不能超过50")
    @NotEmpty(message = "负责人手机不能为空")
    @Phone
    private String principalTel;

    /**
     * 队伍人数
     */
    @Getter@Setter
    @Min(value=0,message = "队伍人数最小为0")
    @Max(value=9999,message = "队伍人数最大为9999")
    @NotNull(message = "队伍人数不能为空")
    private Integer teamNum;

    /**
     * 经纬度
     */
    @Getter@Setter
    @Length(max=64,message = "经纬度lonAndLat长度不能超过64")
    @NotEmpty(message = "经纬度不能为空")
    private String lonAndLat;

    /**
     * 常驻地址
     */
    @Getter@Setter
    @Length(max=100,message = "常驻地址address长度不能超过100")
    @NotEmpty(message = "常驻地址不能为空")
    private String address;

    /**
     * 专业特长
     */
    @Getter@Setter
    @Length(max=500,message = "专业特长specialty长度不能超过500")
    @NotEmpty(message = "专业特长不能为空")
    private String specialty;

    /**
     * 队伍构成
     */
    @Getter@Setter
    @Length(max=2000,message = "队伍构成constituted长度不能超过2000")
    private String constituted;

    /**
     * 主要装备
     */
    @Getter@Setter
    @Length(max=2000,message = "主要装备materials长度不能超过2000")
    private String materials;

    /**
     * 队伍救援能力
     */
    @Getter@Setter
    @Length(max=2000,message = "队伍救援能力teamAbility长度不能超过2000")
    private String teamAbility;

    /**
     * 典型抢险事例
     */
    @Getter@Setter
    private String teamCase;

    /**
     * 备注
     */
    @Getter@Setter
    @Length(max=2000,message = "备注notes长度不能超过2000")
    private String notes;

    /**
     * 创建单位id
     */
    @Getter@Setter
    @Length(max=36,message = "创建单位id createOrgId长度不能超过36")
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Getter@Setter
    @Length(max=100,message = "创建单位名称 createOrgName长度不能超过100")
    private String createOrgName;

}
