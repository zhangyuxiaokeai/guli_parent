package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-24
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
//后台banner管理
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        crmBannerService.page(bannerPage, null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    //添加bannner
    @ApiOperation(value = "新增banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //修改banner
    @ApiOperation(value = "修改banner")
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }
    //删除banner
    @ApiOperation(value = "删除banner")
    @DeleteMapping("/deleteBannerByid/{id}")
    public R deleteBannerByid(@PathVariable Integer id){
        crmBannerService.removeById(id);
        return R.ok();
    }
}

