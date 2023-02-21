package com.atguigu.atlservice.client;

import com.atguigu.atlservice.client.impl.UcenterFileDegradFeignClient;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = UcenterFileDegradFeignClient.class)
public interface UcenterClient {
    @GetMapping("/educenter/ucenter/getInfoUc/{id}")
    public UcenterMemberOrder getInfoUc(@PathVariable("id") String id);
}
