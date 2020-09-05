package com.trump.ucenter.controller;

import com.google.gson.Gson;
import com.trump.commonutils.ResultCode;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.commonutils.JwtUtils;
import com.trump.ucenter.entity.Member;
import com.trump.ucenter.service.MemberService;
import com.trump.ucenter.utils.HttpClientUtils;
import com.trump.ucenter.utils.WxUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
public class WxController {
    @Autowired
    private MemberService memberService;

    //获取扫描人信息，添加数据
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            //1.使用code请求微信固定地址，得到access_token和openid
            String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接参数:id，密钥和code
            baseUrl = String.format(baseUrl,
                    WxUtils.OPEN_ID,
                    WxUtils.SECRET,
                    code);
            //2.请求拼接好的地址，得到access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(baseUrl);
            //把字符串转换为map集合，获取数据
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            if (StringUtils.isBlank(access_token)) {
                throw new GuliException(ResultCode.ERROR, "获取微信授权token失败!");
            }
            if (StringUtils.isBlank(openid)) {
                throw new GuliException(ResultCode.ERROR, "获取微信openid失败!");
            }
            //把扫码人信息添加到数据库
            //判断是否存在相同微信信息,根据openid查询
            Member member = memberService.getByOpenId(openid);
            if (member == null) {//无数据，进行添加
                //3.使用access_token和openid请求微信提供的地址，获取用户信息
                String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接参数
                userInfoUrl = String.format(
                        userInfoUrl,
                        access_token,
                        openid);
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                if (StringUtils.isBlank(nickname)) {
                    throw new GuliException(ResultCode.ERROR, "获取微信用户信息[昵称]失败!");
                }
                if (StringUtils.isBlank(headimgurl)) {
                    throw new GuliException(ResultCode.ERROR, "获取微信用户信息[头像]失败!");
                }
                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //返回首页面,通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new GuliException(ResultCode.ERROR, "扫码登录失败!");
        }
    }

    //生成微信扫描二维码
    @RequestMapping("/login")
    public String createWxCode() {
        //微信开放平台授权url,%s相当于占位符
        String url = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = WxUtils.REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s里的值
        url = String.format(
                url,
                WxUtils.OPEN_ID,
                redirectUrl,
                "atguigu"
        );

        return "redirect:" + url;
    }
}
