package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebEmpResApplication {

    public static void main(String[] args){
        SpringApplication.run(WebEmpResApplication.class,args);
    }

}
