package com.trump.cms.controller;


import com.trump.cms.entity.CrmBanner;
import com.trump.cms.service.CrmBannerService;
import com.trump.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/educms/bannerFront")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    //查询所有
    @GetMapping("/getAll")
    public R getAll(){
        List<CrmBanner> list = bannerService.getAll();
        return R.ok().data(list);
    }

}

