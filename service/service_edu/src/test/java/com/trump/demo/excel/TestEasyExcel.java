package com.trump.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.trump.eduservice.entity.excel.SubjectData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    private String filename = "E:/write.xlsx";
    @Test
    //写入
    public void test1(){
        //实现excel写的操作
        //1.设置写入文件夹地址和excel文件名称
        //2.调用easyexcel老场面的方法实现写操作
        //参数：文件路径名称，实体类class
        EasyExcel.write(filename,SubjectData.class).sheet("学生列表").doWrite(getData());


    }
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setName("迪丽热巴"+i);
            list.add(demoData);
        }
        return list;
    }

    @Test
    //读取操作
    public void test2(){
        EasyExcel.read(filename, DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
