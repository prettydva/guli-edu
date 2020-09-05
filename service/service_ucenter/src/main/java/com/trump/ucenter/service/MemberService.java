package com.trump.ucenter.service;

import com.trump.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-26
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getByOpenId(String openid);

    Integer countRegister(String date);
}
