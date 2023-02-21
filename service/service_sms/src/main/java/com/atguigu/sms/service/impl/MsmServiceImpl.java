package com.atguigu.sms.service.impl;

//import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
//import com.aliyun.tea.TeaException;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.sms.service.MsmService;
import com.atguigu.sms.utils.HttpUtils;
import com.atguigu.sms.utils.MailUtils;
import com.atguigu.sms.utils.Sample;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String code, String mail){
        //注释的为手机验证码登录
//        com.aliyun.dysmsapi20170525.Client client = Sample.createClient("LTAI5tR6XuzUBoXniE3LuWQq", "UiXHBSo8EAe7LYEM9vstcP9REPojAC");
//        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
//                .setSignName("阿里云短信测试")
//                .setTemplateCode("SMS_154950909")
//                .setPhoneNumbers(phone)
//                .setTemplateParam("{\"code\":\""+code+"\"}");
//        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//            client.sendSmsWithOptions(sendSmsRequest, runtime);
//        } catch (TeaException error) {
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        }
//        System.out.println(sendSmsRequest.getPhoneNumbers());
//        System.out.println(sendSmsRequest.getTemplateCode());
//        System.out.println(sendSmsRequest.getSignName());

        try {
            MailUtils.sendMail(mail,code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"验证码发送失败");
        }
        return true;
    }
}
