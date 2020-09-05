package com.trump.commonutils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页结果对象
 */
@Data
public class PR<T> {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("分页数据")
    private List<T> rows;

    public PR() {
    }

    public PR(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
    public static Map<String,Object> getPageMap(Page<?> page){
        Map<String,Object> map = new HashMap<>();
        long current = page.getCurrent();
        long total = page.getTotal();
        long size = page.getSize();
        long pages = page.getPages();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();
        List<?> records = page.getRecords();
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("records",records);
        return map;
    }



}




