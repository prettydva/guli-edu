package com.trump.servicebase.exceptionhandler;
import com.trump.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    @ExceptionHandler({Exception.class})
    //返回json数据
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("全局异常处理..");
    }

    // 特殊异常处理
    @ExceptionHandler({ArithmeticException.class})
    //返回json数据
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("全局异常处理..");
    }
    // 自定义
    @ExceptionHandler({GuliException.class})
    //返回json数据
    @ResponseBody
    public R error(GuliException e){
        e.printStackTrace();
        log.error(e.getMessage());//记录到日志
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
