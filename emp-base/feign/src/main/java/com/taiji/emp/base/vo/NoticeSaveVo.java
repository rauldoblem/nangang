package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class NoticeSaveVo {

    public NoticeSaveVo() {}

    @Getter
    @Setter
    NoticeVo notice;

    /**
     * 新增的附件
     */
    @Getter
    @Setter
    List<String> fileIds;

    /**
     * 新增的附件
     */
    @Getter
    @Setter
    List<String> fileDeleteIds;
}
