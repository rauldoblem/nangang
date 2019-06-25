package com.taiji.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * 定义访问资源路径配置文件
 * */
@Configuration
public class FileConfig extends WebMvcConfigurerAdapter {

    /**
     * 在配置文件中配置的文件保存路径
     */
    @Value("${local.file.storage.address}")
    private String fileLocalAddress;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/files/show/**")
                .addResourceLocations("file:/"+fileLocalAddress);
        super.addResourceHandlers(registry);
    }

    /**
     * 配置文件最大大小
     * 以下配置等用于 在 application.properties中使用
     * spring.http.multipart.maxFileSize=10MB
     * spring.http.multipart.maxRequestSize=100MB
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //设置文件最大大小
        factory.setMaxFileSize("10MB"); //可设置 KB,MB;也可使用long型（以byte为单位）
        //设置上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }


}
