package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.VO.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
  @Autowired
   private RedisTemplate<String,String> template;

   //验证登录方法
    @Override
    public String login(UcenterMember member) {
        //判断账号密码是否为空
        String mail = member.getMail();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mail)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败，邮箱或者密码不能为空");
        }
        //根据邮箱查询是否有这个账号
        QueryWrapper<UcenterMember> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq("mail",mail);
        UcenterMember ucenterMember = baseMapper.selectOne(loginWrapper);
        //判断是否查询出结果
        if(ucenterMember==null){
            throw new GuliException(20001,"登录失败账号密码错误");
        }
        //因为存储到数据库的密码是加密的
        //输入的密码进行加密，在和数据库中密码进行对比
        if(!MD5.encrypt(member.getPassword()).equals(ucenterMember.getPassword())){
            throw new GuliException(20001,"密码输入错误");
        }
        if(ucenterMember.getIsDisabled()){
            throw new GuliException(20001,"账号违规，请联系客服");
        }
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    //注册方法
    @Override
    public void register(RegisterVo registerVo) {
        String mail = registerVo.getMail();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if (StringUtils.isEmpty(mail)||StringUtils.isEmpty(nickname)||
        StringUtils.isEmpty(password)||StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册失败，值不能为空");
        }
        //判断验证码
        String redisCode = template.opsForValue().get(mail);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"验证码错误,注册失败");
        }
        QueryWrapper<UcenterMember> mailWrapper= new QueryWrapper<>();
        mailWrapper.eq("mail",mail);
        Integer count = baseMapper.selectCount(mailWrapper);
        if (count>0){
            throw new GuliException(20001,"邮箱已经被注册");
        }
        //数据添加到数据库中
        UcenterMember ucenterRegister = new UcenterMember();
        ucenterRegister.setMail(mail);
        ucenterRegister.setNickname(nickname);
        ucenterRegister.setPassword(MD5.encrypt(password));//密码需要加密
        ucenterRegister.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        ucenterRegister.setIsDisabled(false);//用户不禁用
        int insert = baseMapper.insert(ucenterRegister);

    }
    //根据openId判断是否有重复用户
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }


    //查询某一天的注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }


}
