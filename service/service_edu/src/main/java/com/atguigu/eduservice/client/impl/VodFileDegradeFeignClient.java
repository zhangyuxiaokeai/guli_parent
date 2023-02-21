package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliVideo(String id) {

        return   R.error().message("time out");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return   R.error().message("time out");
    }
}
