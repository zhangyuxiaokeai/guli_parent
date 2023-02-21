package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;

@RequestMapping("api/ucenter/wx")
@Controller //之请求地址不需要返回数据
@CrossOrigin
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;


    //2.获取扫描人信息，添加数据
    @GetMapping("/callback")
    public String callback(String code,String state){
        try {
            //1.先获取code值，临时票据，类似验证码
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
            "?appid=%s" +
            "&secret=%s" +
            "&code=%s" +
            "&grant_type=authorization_code";
            //拼接三个参数：id 密钥 和code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //2.拿着临时code值请求微信固定地址，拿到两个值 accsess_token 和openid
            //请求这个拼接好的这个地址，得到返回两个值 access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo"+accessTokenInfo);
            //从accessTokenInfo字符串中取出两个值 access_token，openid
            //把accessTokenInfo字符串转换成map集合，根据map获取里面的key和value
            //使用gson转换工具
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String openid = (String) mapAccessToken.get("openid");
            String access_token = (String) mapAccessToken.get("access_token");

           UcenterMember member=ucenterMemberService.getOpenIdMember(openid);
           if(member==null){
               //3.拿着得到的access_token和openid，再去请求微信提供的固定地址，获取扫码人的固定信息
               //访问微信的资源服务器，获取用户信息
               String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                       "?access_token=%s" +
                       "&openid=%s";
               //再去拼接字符串，访问微信的固定地址
               String userInfoUrl = String.format(baseUserInfoUrl,
                       access_token,
                       openid);
               //发送请求
               String userInfo = HttpClientUtils.get(userInfoUrl);
               System.out.println("userInfo"+userInfo);
               //获取返回userinfo字符串扫描人信息
               HashMap<String,Object> userInfoMap = gson.fromJson(userInfo, HashMap.class);
               String nickname = (String)userInfoMap.get("nickname");
               String headimgurl =(String) userInfoMap.get("headimgurl");
               //把扫描人信息添加到数据库里面
               //判断数据库表里面是否存在相同微信信息，根据openid判断
               //如果member为空就将数据存入数据库
               member=new UcenterMember();
               member.setNickname(nickname);
               member.setAvatar(headimgurl);
               member.setOpenid(openid);
               ucenterMemberService.save(member);
           }
            //最后：返回首页面，根据路径传递字符串
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            throw new GuliException(20001,"扫码登陆失败");
        }

    }
    //1.生成微信扫描的二维码
    @GetMapping("/login")
    public String getWxCode(){
        //请求微信地址
        //固定地址，后面拼接参数
        // 微信开放平台授权baseUrl  %s相当于？代表相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

                //对redirect_url进行URLEncoder编码
           String redirectUrl= ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s中这些值
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        return "redirect:"+qrcodeUrl;
    }
}
