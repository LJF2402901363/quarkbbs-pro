package com.quark.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quark.common.entity.User;

/**
 * @Author LHR
 * Create By 2017/8/25
 */
public interface UserService  extends IService<User> {

    /**
     * 翻页获取用户列表
     * @param user
     * @param pageNo
     * @param length
     * @return
     */
    Page<User> findUserByPage(User user, int pageNo, int length);

    /**
     * 恢复/封禁用户
     * @param id
     */
    void saveUserEnable(Integer[] id);
}
