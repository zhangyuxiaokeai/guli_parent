package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerFront")
@CrossOrigin
//用户显示banner
public class BannerFrontController {
@Autowired CrmBannerService crmBannerService;

@ApiOperation(value = "查询banner")
@GetMapping("/banner")
    public R getBanner(){
   List<CrmBanner> list= crmBannerService.selectAllBanner();
   return R.ok().data("list",list);
}
}
