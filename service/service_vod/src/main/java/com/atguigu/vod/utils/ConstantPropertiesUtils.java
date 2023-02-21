package com.atguigu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private  String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;
    //定义公开的静态的常量
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
    ACCESS_KEY_ID=keyId;
    ACCESS_KEY_SECRET=keySecret;
    }
}
