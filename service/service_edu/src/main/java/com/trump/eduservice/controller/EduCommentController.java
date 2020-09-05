package com.trump.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.commonutils.PR;
import com.trump.commonutils.R;
import com.trump.eduservice.entity.EduComment;
import com.trump.eduservice.service.EduCommentService;
import com.trump.eduservice.entity.EduComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    //分页查询评论
    @GetMapping("/getPage/{page}/{size}")
    public R getCommentPage(@PathVariable long page, @PathVariable long size) {
        //创建Page类对象
        Page<EduComment> commentPage = new Page<>(page, size);
        //分页查询，无查询条件
        commentService.page(commentPage, null);
        //封装到分页结果对象
        PR<EduComment> pr = new PR<>(commentPage.getTotal(), commentPage.getRecords());
        //返回数据
        return R.ok().data(pr);
    }

    //添加评论
    @PostMapping("/addComment")
    public R addComment(@RequestBody EduComment comment) {
        boolean save = commentService.save(comment);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除评论
    @DeleteMapping("{id}")
    public R deleteComment(@PathVariable String id) {
        boolean remove = commentService.removeById(id);
        if (remove) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改评论
    @PutMapping("{id}")
    public R updateComment(@PathVariable String id,
                           @RequestBody EduComment comment) {
        comment.setId(id);
        boolean update = commentService.updateById(comment);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

