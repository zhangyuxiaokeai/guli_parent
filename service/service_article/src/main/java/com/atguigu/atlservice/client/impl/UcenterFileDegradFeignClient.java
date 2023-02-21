package com.atguigu.atlservice.client.impl;

import com.atguigu.atlservice.client.UcenterClient;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradFeignClient implements UcenterClient {

    @Override
    public UcenterMemberOrder getInfoUc(String id) {
        throw new GuliException(20001,"调用失败");
    }
}
