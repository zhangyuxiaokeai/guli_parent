package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradFeignClient implements UcenterClient {
    @Override
    public UcenterMemberOrder getInfoUc(String id) {
        throw new GuliException(20001,"调用失败");
    }
}
