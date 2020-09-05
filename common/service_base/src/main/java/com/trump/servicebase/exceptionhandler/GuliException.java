package com.trump.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//自定义异常类
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor //生成有参数的构造方法
@NoArgsConstructor //无参数构造
public class GuliException extends RuntimeException {
    private Integer code; //状态码
    private String msg; //异常信息

}
