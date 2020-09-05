package com.trump.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.commonutils.JwtUtils;
import com.trump.commonutils.MD5;
import com.trump.commonutils.ResultCode;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.ucenter.entity.Member;
import com.trump.ucenter.mapper.MemberMapper;
import com.trump.ucenter.entity.vo.RegisterVo;
import com.trump.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-26
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(Member member) {
        //获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //判断是否为空
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(ResultCode.ERROR,"手机号或密码不能为空！");
        }
        //判断手机号是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member mobileMember = this.getOne(wrapper);
        if (mobileMember==null){//没有这个手机号
            throw new GuliException(ResultCode.ERROR,"手机号不存在！");
        }
        //判断密码
        //把密码加密后再比较
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(ResultCode.ERROR,"密码错误！");
        }
        //判断用户是否禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(ResultCode.ERROR,"用户已被禁用！");
        }
        //登录成功，生成并返回token
        return JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        //非空判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)
        ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(code)){
            throw new GuliException(ResultCode.ERROR,"请完善注册信息！");
        }
        //判断书输入的验证码与redis中的是否一致
        String redisCode = redisTemplate.opsForValue().get("phone");
        if (!code.equals(redisCode)){
            throw new GuliException(ResultCode.ERROR,"验证码无效！");
        }
        //判断手机号是否重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        int count = this.count(wrapper);
        if (count>0){
            throw new GuliException(ResultCode.ERROR,"该手机号已存在！");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
//        member.setIsDeleted(false);
//        member.setIsDisabled(false);
        member.setAvatar("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3283320407,439377378&fm=26&gp=0.jpg");
        //添加到数据库
        this.save(member);

    }

    @Override
    public Member getByOpenId(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return this.getOne(wrapper);
    }

    @Override
    public Integer countRegister(String date) {
        return baseMapper.countRegister(date);
    }
}
