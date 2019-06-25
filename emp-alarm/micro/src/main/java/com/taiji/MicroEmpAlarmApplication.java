package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * alarm micro main
 * @time 2018年12月11日17:40:02
 */
@EnableDiscoveryClient
@SpringCloudApplication
@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroEmpAlarmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroEmpAlarmApplication.class,args);
    }
}
