package com.taiji.emp.base.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 附件管理 feign DocAttVo
 * @author qizhijie-pc
 * @date 2018年9月28日15:54:37
 */
public class DocAttVo extends IdVo<String>{

    public DocAttVo(){}

    /**
     * 业务主键id
     */
    @Getter@Setter
    @Length(max = 36,message = "业务主键id ywid长度不能超过36")
    private String ywid;

    /**
     * 文件名
     */
    @Getter@Setter
    @Length(max = 100,message = "文件名name长度不能超过100")
    private String name;

    /**
     * 文件类型
     */
    @Getter@Setter
    @Length(max = 20,message = "文件类型type长度不能超过20")
    private String type;

    /**
     * 文件后缀
     */
    @Getter@Setter
    @Length(max = 10,message = "文件后缀suffix长度不能超过10")
    private String suffix;

    /**
     * 存储相对地址
     */
    @Getter@Setter
    @Length(max = 200,message = "存储相对地址location长度不能超过200")
    private String location;

    /**
     * 上传用户id
     */
    @Getter@Setter
    @Length(max = 36,message = "上传用户id uploadUserId长度不能超过36")
    private String uploadUserId;

    /**
     * 上传用户姓名
     */
    @Getter@Setter
    @Length(max = 20,message = "上传用户姓名 uploadUserName长度不能超过20")
    private String uploadUserName;


}
