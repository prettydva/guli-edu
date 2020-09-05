package com.trump.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.trump.oss.service.OssService;
import com.trump.oss.utils.ConstantPropertiesUtils;
import com.trump.oss.service.OssService;
import com.trump.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service("ossService")
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatar(MultipartFile file) {
        try {
            //从工具类获取值
            String endpoint = ConstantPropertiesUtils.END_POINT;
            String keyId = ConstantPropertiesUtils.KEY_ID;
            String keySecret = ConstantPropertiesUtils.KEY_SECRET;
            String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
            //创建oss实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //1.在文件名称添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //trumplihl01.jpg
            fileName = uuid + fileName;

            //2.把文件按日期分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接
            fileName = datePath + "/" + fileName;
            /**
             *  调用oss方法实现上传
             *  第一个参数 Bucket名称
             *  第二个参数 上传到oss文件路径和文件名称 /aa/bb/1.txt
             *  第三个参数 上传文件输入流
             */
            ossClient.putObject(bucketName, fileName, inputStream);
            //关闭OSSClient
            ossClient.shutdown();
            //把上传后文件路径返回
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
