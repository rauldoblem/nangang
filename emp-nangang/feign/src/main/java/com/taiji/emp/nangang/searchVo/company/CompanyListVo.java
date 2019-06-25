package com.taiji.emp.nangang.searchVo.company;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter@Getter
public class CompanyListVo {
    @Length(max = 50,message = "最大长度为50")
    private String name;
}
