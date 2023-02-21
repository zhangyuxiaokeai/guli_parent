package com.atguigu.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    //注入vodClient
    @Autowired
    private VodClient vodClient;
    //根据章节id查询所有小节
    @GetMapping("/getVideoByChapterId/{chapterId}")
    public R getVideoByChapterId(@PathVariable String chapterId){
       List<EduVideo> list= eduVideoService.getVideoByChaptId(chapterId);
        return R.ok().data("eduvideolist",list);
    }
    //添加小结
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return  R.ok();
    }

    //删除小结

    /**
     * 删除小节删除对应的视频
     * @param Id
     * @return
     */
    @DeleteMapping("{Id}")
    public R deleteVideo(@PathVariable String Id){
        //根据小结id查找出视频id
        EduVideo eduVideo = eduVideoService.getById(Id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否优视频id
        if(!StringUtils.isEmpty(videoSourceId)){
            //根据视频id，远程调用实现视频删除
            //调用Vod中的方法
            R result = vodClient.removeAliVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除失败");
            }
        }
        //删除小结
        eduVideoService.removeById(Id);
        return R.ok();
    }
    //修改小节

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
       eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    @GetMapping("/getVideoByid/{id}")
    public R getVideoByid(@PathVariable String id){
        EduVideo videoInfo = eduVideoService.getById(id);
        return R.ok().data("videoInfo",videoInfo);
    }
}

