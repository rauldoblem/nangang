package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/20 11:10
 */
@Getter
@Setter
@NoArgsConstructor
public class CompanyInfoVo {

        @Length(max = 50 ,message = "公司名字字段最大长度50")
        public String name;

        @Length(max = 50 ,message = "公司类型字段最大长度50")
        public String companyType;

        @Length(max = 50 ,message = "公司地址字段最大长度50")
        public String address;

        @Length(max = 50 ,message = "公司经度字段最大长度50")
        public String longitude;

        @Length(max = 50 ,message = "公司纬度字段最大长度50")
        public String latitude;

        @Length(max = 50 ,message = "企业规模字段最大长度50")
        public String scale;

        @Length(max = 50 ,message = "占地规模字段最大长度50")
        public String area;

        @Length(max = 50 ,message = "员工数字段最大长度50")
        public String employees;

        @Length(max = 50 ,message = "经营范围字段最大长度50")
        public String businessScope;

        @Length(max = 50 ,message = "法定代表人字段最大长度50")
        public String legalRepresentative;

        @Length(max = 50 ,message = "行业类别及代码字段最大长度50")
        public String industryCategoryAndCode;

        @Length(max = 50 ,message = "企业负责人字段最大长度50")
        public String principal;

        @Length(max = 50 ,message = "企业负责人电话字段最大长度50")
        public String principalTel;

        @Length(max = 50 ,message = "安全部门负责人最大长度50")
        public String securityPrincipal;

        @Length(max = 50 ,message = "安全部门负责人电话字段最大长度50")
        public String securityPrincipalTel;

        @Length(max = 50 ,message = "备注字段最大长度50")
        public String note;

}
