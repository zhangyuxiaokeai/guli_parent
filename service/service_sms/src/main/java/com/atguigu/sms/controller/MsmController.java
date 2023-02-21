package com.atguigu.sms.controller;

import com.aliyuncs.utils.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.sms.service.MsmService;
import com.atguigu.sms.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方式
    @GetMapping("/send/{mail}")
    public R sendMsm(@PathVariable String mail) {
        //1.从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(mail);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //生成随机值，传递阿里云进行发送
       code= RandomUtil.getSixBitRandom();
        //调用service发送短信的方法
       boolean isSend= msmService.send(code,mail);
       if(isSend){
           //如果发送成功，讲验证码放入到redis里面
           //设置有效时间
           redisTemplate.opsForValue().set(mail,code,1, TimeUnit.MINUTES);
           return R.ok();
       }else {
           return R.error().message("邮箱发送失败");
       }
    }
}
