package com.taiji.emp.res.searchVo.material;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MaterialListVo {

    @Getter@Setter
    private String name;
    @Getter@Setter
    private List<String> resTypeIds;
    @Getter@Setter
    private String specModel;
    @Getter@Setter
    private String repertoryId;
    @Getter@Setter
    private String positionId;
    @Getter@Setter
    private String createOrgId;
    @Getter@Setter
    private List<String> selectedMaterialIds;
    @Getter@Setter
    private List<String> materialIds;

}
