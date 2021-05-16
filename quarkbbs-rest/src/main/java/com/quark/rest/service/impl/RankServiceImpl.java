package com.quark.rest.service.impl;

import com.quark.common.dao.PostsDao;
import com.quark.common.dao.UserDao;
import com.quark.common.entity.Posts;
import com.quark.common.entity.User;
import com.quark.rest.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author LHR
 * Create By 2017/8/31
 */
@Service
public class RankServiceImpl  implements RankService{
    @Autowired
    private PostsDao postsDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Object> findPostsRank() {
        List<Posts> hot = postsDao.findHot();
        List<Object> list = new ArrayList<>();
        if (Objects.isNull(hot) || hot.size() == 0){
            return list;
        }

        hot.forEach(e->{
            list.add(e);
        });
        return list;
    }

    @Override
    public List<Object> findUserRank() {

        List<User> userList = userDao.findNewUser();

        List<Object> list = new ArrayList<>();
        if (Objects.isNull(userList) || userList.size() == 0){
            return list;
        }

        userList.forEach(e->{
            list.add(e);
        });
        return list;
    }
}
