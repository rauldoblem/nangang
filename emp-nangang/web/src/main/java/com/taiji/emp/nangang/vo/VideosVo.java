package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.Setter;

public class VideosVo {

    /**
     * id
     */
    @Getter
    @Setter
    private String id;

    /**
     * 文件名
     */
    @Getter@Setter
    private String fileName;

    /**
     * 存储相对地址
     */
    @Getter@Setter
    private String location;

    /**
     * 文件类型
     */
    @Getter@Setter
    private String fileType;

}
