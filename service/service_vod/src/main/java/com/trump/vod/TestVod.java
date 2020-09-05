package com.trump.vod;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.*;
import com.aliyun.vod.upload.resp.*;
import com.trump.vod.utils.ConstantVodUtils;
import com.trump.vod.utils.ConstantVodUtils;

public class TestVod {
        //账号AK信息请填写(必选)
        private static final String accessKeyId = ConstantVodUtils.KEYID;
        //账号AK信息请填写(必选)
        private static final String accessKeySecret = ConstantVodUtils.KEYSECRET;

        public static void main(String[] args) {
            // 一、视频文件上传
            // 视频标题(必选)
            String title = "测试标题";
            // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
            // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
            // 3.流式上传时，文件名称为源文件名，如文件名称.mp4(必选)。
            // 任何上传方式文件名必须包含扩展名
            String fileName = "E:/cloud/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";
            //上传视频的方法
            UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
            /* 可指定分片上传时每个分片的大小，默认为2M字节 */
            request.setPartSize(2 * 1024 * 1024L);
            /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
            request.setTaskNum(1);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadVideoResponse response = uploader.uploadVideo(request);
//            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else {
                /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        }
    }
