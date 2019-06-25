package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/10 11:37
 */
@NoArgsConstructor
public class WasteWaterVo {

//    @Getter
//    @Setter
//    @Length(max = 50,message = "水流量字段最大长度50")
//    private String waterFlow;

    @Getter
    @Setter
    @Length(max = 50,message = "PH字段最大长度50")
    private String ph;

    @Getter
    @Setter
    @Length(max = 50,message = "COD字段最大长度50")
    private String cod;

    @Getter
    @Setter
    @Length(max = 50,message = "氨氮折算值字段最大长度50")
    private String ammoniaNitrogen;

    @Getter
    @Setter
    @Length(max = 50,message = "总氮字段最大长度50")
    private String totalNitrogen;

    @Getter
    @Setter
    @Length(max = 50,message = "总磷字段最大长度50")
    private String totalPhosphorus;

    private List<String> data;

    public List<String> getData(){
        List<String> data = new ArrayList<>();
        //data.add(waterFlow);
        data.add(ph);
        data.add(cod);
        data.add(ammoniaNitrogen);
        data.add(totalNitrogen);
        data.add(totalPhosphorus);
        return data;
    }
}
