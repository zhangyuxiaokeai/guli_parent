package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-08
 */
@CrossOrigin
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
@Autowired
   private UcenterClient ucenterClient;
@Autowired
    private StatisticsDailyService staService;
    //统计某一天的注册人数
    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }

    //图表显示，返回两部分数据：日期的json数组，数量的json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end){
       Map<String ,Object> map= staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

