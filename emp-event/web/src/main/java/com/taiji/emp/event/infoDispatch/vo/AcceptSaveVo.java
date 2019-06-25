package com.taiji.emp.event.infoDispatch.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AcceptSaveVo {

    /**
     * 报送信息Vo对象 AcceptVo
     */
    @Getter
    @Setter
    @NotNull(message = "AcceptVo不能为null")
    private AcceptVo imAccept;

    /**
     * 附件对象id串(待赋值list)
     */
    @Getter@Setter
    private List<String> fileIds;

    /**
     * 附件对象id串(待删除list)
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;

}
