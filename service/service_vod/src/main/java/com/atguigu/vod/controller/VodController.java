package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传阿里云视频
    @PostMapping("/UploadAliyunVideo")
    public R UploadAliyunVideo(MultipartFile file){
      String videoId=vodService.uploadVideoAli(file);
        return  R.ok().data("videoId",videoId);
    }

    //删除视频
    @DeleteMapping("/removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable String  id){
        vodService.deleteAliVideo(id);
        return R.ok();
    }

    //删除多个aliyun视频
    @DeleteMapping("delete-batch")
    public  R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
       vodService.removeMoreAliyunVideo(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID,
                    ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();

            //项request设置视频id
            request.setVideoId(id);
            //调用方法获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
           throw new GuliException(20001,"获取凭证失败");
        }
    }
}
