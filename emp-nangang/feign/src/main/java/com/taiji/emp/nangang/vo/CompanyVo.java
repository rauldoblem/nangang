package com.taiji.emp.nangang.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter@NoArgsConstructor

public class CompanyVo extends BaseVo<String> {
    @Length(max = 50,message = "最大长度为50")
    private String name;
    @Length(max = 50,message = "最大长度为50")
    private String companyType;
    @Length(max = 200,message = "最大长度为200")
    private String address;
    @Length(max = 64,message = "最大长度为64")
    private String lonAndLot;
    @Length(max = 50,message = "最大长度为50")
    private String companySize;
    @Length(max = 50,message = "最大长度为50")
    private Double floorArea;

    private Double personNum;

    @Length(max = 100,message = "最大长度为100")
    private String busiScope;
    @Length(max = 50,message = "最大长度为50")
    private String legalPerson;
    @Length(max = 50,message = "最大长度为50")
    private String induType;
    @Length(max = 50,message = "最大长度为50")
    private String chargePerson;
    @Length(max = 50,message = "最大长度为50")
    private String chargeTel;
    @Length(max = 50,message = "最大长度为50")
    private String safetyPerson;
    @Length(max = 50,message = "最大长度为50")
    private String safetyTel;
    @Length(max = 50,message = "最大长度为50")
    private String notes;
    @Length(max = 1,message = "最大长度为1")
    private String sourceFlag;
    @Length(max = 50,message = "最大长度为50")
    private String code;
    @Length(max = 50,message = "最大长度为50")
    private String nature;
    @Length(max = 50,message = "最大长度为50")
    private String region;

    private Double regCapital;
    @Length(max = 100,message = "最大长度为100")
    private String email;
    @Length(max = 50,message = "最大长度为50")
    private String fax;
}
