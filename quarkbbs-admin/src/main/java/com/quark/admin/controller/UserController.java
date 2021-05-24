package com.quark.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.admin.service.UserService;
import com.quark.common.base.BaseController;
import com.quark.common.dto.PageResult;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author LHR
 * Create By 2017/8/25
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping
    public PageResult getAll(User user, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length + 1;
        Page<User> page = userService.findUserByPage(user, pageNo, length);
        PageResult<List<User>> result = new PageResult<>(
                draw,
                page.getTotal(),
                page.getSize(),
                page.getRecords());
        return result;
    }

    @PostMapping("/saveUserEnable")
    public QuarkResult saveUserEnable(@RequestParam(value = "id[]") Integer[] id){
        QuarkResult result = restProcessor(() -> {
            userService.saveUserEnable(id);
            return QuarkResult.ok();
        });
        return result;
    }

}
