package com.trump.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.cms.entity.CrmBanner;
import com.trump.cms.mapper.CrmBannerMapper;
import com.trump.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    //查询所有banner
    @Cacheable(value = "banner",key = "'bannerList'")
    @Override
    public List<CrmBanner> getAll() {
        //根据id进行降序排序，显示排序后的前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql
        wrapper.last("limit 2");
        return this.list(wrapper);
    }
}
