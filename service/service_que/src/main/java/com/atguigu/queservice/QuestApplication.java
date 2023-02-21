package com.atguigu.queservice;

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
@MapperScan("com.atguigu.queservice.mapper")
public class QuestApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestApplication.class,args);
    }
}
