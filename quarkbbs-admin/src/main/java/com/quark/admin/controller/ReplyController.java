package com.quark.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.admin.service.ReplyService;
import com.quark.admin.service.UserService;
import com.quark.common.base.BaseController;
import com.quark.common.dto.PageResult;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.Reply;
import com.quark.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author LHR
 * Create By 2017/9/3
 */
@RequestMapping("/replys")
@RestController
public class ReplyController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public PageResult getAll(Reply reply, Integer uid, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        if (uid!=null){
            User user = userService.getById(uid);
            reply.setUser(user);
        }

        Page<Reply> page = replyService.findByPage(reply, pageNo, length);
        PageResult<List<Reply>> result = new PageResult<>(
                draw,
                page.getTotal(),
                page.getSize(),
                page.getRecords());
        return result;
    }

    @PostMapping("/delete")
    public QuarkResult delete(@RequestParam(value = "id") Integer[] id){
        QuarkResult result = restProcessor(() -> {
            replyService.deleteInBatch(id);
            return QuarkResult.ok();
        });
        return result;
    }

}
