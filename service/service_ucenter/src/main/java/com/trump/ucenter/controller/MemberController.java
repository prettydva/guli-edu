package com.trump.ucenter.controller;


import com.trump.commonutils.JwtUtils;
import com.trump.commonutils.R;
import com.trump.commonutils.ordervo.MemberVoOrder;
import com.trump.ucenter.entity.Member;
import com.trump.ucenter.entity.vo.RegisterVo;
import com.trump.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-26
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody Member member){
        //登录成功返回token
        String token = memberService.login(member);
        return R.ok().data(token);
    }
    //注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id获取用户信息
        Member member = memberService.getById(memberId);
        return R.ok().data(member);
    }
    //根据id查询用户信息
    @GetMapping("/getMember/{memberId}")
    public MemberVoOrder getMemberById(@PathVariable String memberId){
        Member member = memberService.getById(memberId);
        MemberVoOrder memberVo = new MemberVoOrder();
        BeanUtils.copyProperties(member,memberVo);
        return memberVo;
    }
    //查询某一天注册人数
    @GetMapping("/countRegister/{date}")
    public Integer countRegister(@PathVariable String date){
        return memberService.countRegister(date);
    }

}

