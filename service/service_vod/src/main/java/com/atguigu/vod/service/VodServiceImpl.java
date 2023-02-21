package com.atguigu.vod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteCategoryResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService{
    /**
     * 流式上传视频
     * @param file
     * @return
     */
    @Override
    public String uploadVideoAli(MultipartFile file) {
    //fileName 上传文件原始名称
        String fileName=file.getOriginalFilename();
    //inputStream 上传文件输入流
        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //title 上传文件之后显示的名称
        String title= fileName.substring(0,fileName.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String videoId=null;
        if (response.isSuccess()) {
            videoId=response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId=response.getVideoId();
        }
        System.out.println(videoId);
        return videoId;
    }

    @Override
    public void deleteAliVideo(String id) {
        //初始化对象
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //创建视频删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置视频id
            request.setVideoIds(id);
            //调用初始化对象实现删除
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }

    }

    @Override
    public void removeMoreAliyunVideo(List videoIdList) {
        //初始化对象
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //创建视频删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将videoList的值转换成为1,2,3,4的形式
            String videoId = StringUtils.join(videoIdList.toArray(), ",");
            //向request中设置视频id
            request.setVideoIds(videoId);
            //调用初始化对象实现删除
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
