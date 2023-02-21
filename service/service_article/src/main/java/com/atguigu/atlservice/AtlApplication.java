package com.atguigu.atlservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients //feign
@EnableDiscoveryClient //nacos注册
@ComponentScan(basePackages ={"com.atguigu"})
@MapperScan("com.atguigu.atlservice.mapper")
public class AtlApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtlApplication.class,args);
    }
}
