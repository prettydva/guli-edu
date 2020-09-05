package com.trump.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.cms.entity.CrmBanner;
import com.trump.cms.service.CrmBannerService;
import com.trump.commonutils.PR;
import com.trump.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/educms/banner")
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //增加
    @PostMapping("/add")
    public R addBanner(@RequestBody CrmBanner banner) {
        boolean save = bannerService.save(banner);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除
    @DeleteMapping("/{bannerId}")
    public R removeById(@PathVariable String bannerId) {
        boolean remove = bannerService.removeById(bannerId);
        if (remove) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //修改
    @PutMapping("/{bannerId}")
    public R update(@PathVariable String bannerId, @RequestBody CrmBanner banner) {
        banner.setId(bannerId);
        boolean update = bannerService.updateById(banner);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //根据id查询
    @GetMapping("/{bannerId}")
    public R getById(@PathVariable String bannerId) {
        CrmBanner banner = bannerService.getById(bannerId);
        return R.ok().data(banner);

    }

    //分页查询
    @PostMapping("/page/{currentPage}/{pageSize}")
    public R getPage(@PathVariable long currentPage,
                     @PathVariable long pageSize,
                     @RequestBody(required = false) CrmBanner banner) {
        Page<CrmBanner> page = new Page<>(currentPage, pageSize);
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        bannerService.page(page, wrapper);
        PR pr = new PR(page.getTotal(), page.getRecords());
        return R.ok().data(pr);
    }
}

