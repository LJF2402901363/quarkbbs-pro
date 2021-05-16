package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.UserService;
import com.quark.common.dao.UserDao;
import com.quark.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author LHR
 * Create By 2017/8/25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
    @Autowired
     private UserDao userDao;
    @Override
    public Page<User> findByPage(User user, int pageNo, int length) {
        Page<User> page = new Page<>(pageNo, length);
       page = userDao.findByPage(page,user);
        return page;
    }

    @Override
    public void saveUserEnable(Integer[] id) {
        if (id == null || id.length == 0){
            return;
        }
        List<Integer> arr = new ArrayList<>(id.length);
        Arrays.stream(id).forEach(e -> arr.add(e));
        List<User> all = userDao.selectBatchIds(arr);
        for (User user :all) {
            if (user.getEnable() == 1) {
                user.setEnable(0);
            } else {
                user.setEnable(1);
            }
            userDao.insert(user);
        }

    }
}
