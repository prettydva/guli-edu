package com.trump.ucenter.mapper;

import com.trump.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trump.ucenter.entity.Member;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Trump
 * @since 2020-08-26
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegister(String date);
}
