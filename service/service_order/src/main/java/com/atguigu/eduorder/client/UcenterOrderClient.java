package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.impl.UcenterOrderFileDegradFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = UcenterOrderFileDegradFeignClient.class)

public interface UcenterOrderClient {
    @GetMapping("/educenter/ucenter/getInfoUc/{id}")
    public UcenterMemberOrder getInfoUc(@PathVariable("id") String id);
}
