package com.trump.cms.service;

import com.trump.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-25
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getAll();
}
