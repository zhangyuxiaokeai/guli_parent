package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.VO.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-27
 */
@CrossOrigin
@RestController
@RequestMapping("/educenter/ucenter")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录方法
    @PostMapping ("/login")
    public R loginUser(@RequestBody(required = false) UcenterMember member){
        //调用service中的方法实现登录
        //member对象腰封转偶极号和密码

      String token= memberService.login(member);
      return R.ok().data("token",token);
    }
    //注册方法
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    //token保存用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberIdByJwtToken);
        return R.ok().data("userInfo",member);
    }

    //根据token保存的信息，查询用户的信息
    @GetMapping("getInfoUc/{id}")
    public UcenterMemberOrder getInfoUc(@PathVariable String id){
        //根据用户id查询用户信息
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable  String day){
       Integer count= memberService.countRegisterDay(day);
       return R.ok().data("count",count);
    }
    }

