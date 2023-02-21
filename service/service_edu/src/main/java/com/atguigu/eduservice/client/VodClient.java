package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    //定义调用方法路径
    @DeleteMapping("/eduvod/video/removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable("id") String  id);
    //定义删除多个视频的方法
    @DeleteMapping("/eduvod/video/delete-batch")
    public  R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
