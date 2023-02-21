package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staservice;
    //0/5 * * * * ?表示每隔五秒执行一次这个方法
    @Scheduled(cron = "0/5 * * * * ?")
    public void  task1(){
    System.out.println("**********task1执行了");
    }
    //每天凌晨一点执行该方法,把前一日的数据进行添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void  task2(){
        staservice.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
