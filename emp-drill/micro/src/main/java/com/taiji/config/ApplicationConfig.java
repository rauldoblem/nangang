package com.taiji.config;

import net.sf.log4jdbc.DataSourceSpyInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qzp
 *
 * @date 2017-10-28
 */
@Configuration
public class ApplicationConfig {

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
}
