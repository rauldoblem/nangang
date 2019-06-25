package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 组装对外提供的字段
 * @author qizhijie-pc
 * @date 2018年9月29日14:29:12
 */
public class DocAttDTO {

    /**
     * 主键
     */
    @Getter@Setter
    private String id;

    /**
     * 文件名称
     */
    @Getter@Setter
    private String fileName;

    /**
     * 相对路径
     */
    @Getter@Setter
    private String location;

    /**
     * 文件类型
     */
    @Getter@Setter
    private String fileType;

    public DocAttDTO(DocAttVo vo){
        this.id = vo.getId();
        this.fileName = vo.getName();
        this.fileType = vo.getType();
        this.location = vo.getLocation();
    }

}
