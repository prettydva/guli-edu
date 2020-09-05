package com.trump.oss.controller;

import com.trump.commonutils.R;
import com.trump.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadAvatar(MultipartFile file) {
        //获取上传文件
        //返回到上传到oss的文件路径
        String url = ossService.uploadAvatar(file);
        return R.ok().data(url);
    }
}
