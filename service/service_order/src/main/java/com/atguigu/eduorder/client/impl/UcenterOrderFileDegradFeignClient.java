package com.atguigu.eduorder.client.impl;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.UcenterOrderClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterOrderFileDegradFeignClient implements UcenterOrderClient {
    @Override
    public UcenterMemberOrder getInfoUc(String id) {
        return null;
    }
}
