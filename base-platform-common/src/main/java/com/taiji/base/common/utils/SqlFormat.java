package com.taiji.base.common.utils;

import org.springframework.util.Assert;

/**
 * 格式化sql为大写或小写
 * @author yhcookie
 * @date 2018/12/28 16:12
 */
public class SqlFormat {

    private Integer format;

    public SqlFormat(Integer format){
        this.format = format;
    }

    public String sqlFormat(String sql){
        Assert.notNull(sql,"sql语句不能为null");
        switch(format){
            case 0:
            {
                 return sql.toUpperCase();
            }
            case 1:
            {
                return sql.toLowerCase();
            }
            default:
            {
                return sql;
            }
        }
    }
}
