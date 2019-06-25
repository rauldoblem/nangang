package com.taiji.config;

import com.taiji.base.common.utils.SqlFormat;
import net.sf.log4jdbc.DataSourceSpyInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author scl
 *
 * @date 2017-02-09
 */
@Configuration
public class ApplicationConfig {

    @Value("${sqlFormat}")
    private Integer sqlFormat;
    /**
     * log4jdbc sql日志打印过滤器
     */
    @Bean
    public DataSourceSpyInterceptor log4jdbcInterceptor() {
        return new DataSourceSpyInterceptor();
    }

    /**
     * log4jdbc sql日志打印过滤器
     */
    @Bean
    public BeanNameAutoProxyCreator dataSourceLog4jdbcAutoProxyCreator() {
        BeanNameAutoProxyCreator  beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("dataSource");

        beanNameAutoProxyCreator.setInterceptorNames("log4jdbcInterceptor");
        return beanNameAutoProxyCreator;
    }

    /**
     * SqlFormat bean
     * 用于转化代码中用到的原生sql的大小写
     * @return
     */
    @Bean
    public SqlFormat format(){
        SqlFormat format = new SqlFormat(sqlFormat);
        return format;
    }
}
