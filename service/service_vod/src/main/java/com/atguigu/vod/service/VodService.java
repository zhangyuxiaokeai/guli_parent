package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoAli(MultipartFile file);

    void deleteAliVideo(String id);

    void removeMoreAliyunVideo(List videoIdList);
}
