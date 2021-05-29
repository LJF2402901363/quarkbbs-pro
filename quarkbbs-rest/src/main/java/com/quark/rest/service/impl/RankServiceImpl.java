package com.quark.rest.service.impl;

import com.quark.common.dao.PostsDao;
import com.quark.common.dao.UserDao;
import com.quark.common.entity.Posts;
import com.quark.common.entity.User;
import com.quark.common.utils.DateUtil;
import com.quark.rest.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<Posts> findPostsRank() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(30L);
        String endTimeStr = DateUtil.dateToString(endTime);
        String startTimeStr = DateUtil.dateToString(startTime);
        List<Posts> hot = postsDao.findHot(startTimeStr,endTimeStr);
        return hot;
    }

    @Override
    public List<User> findUserRank() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(30L);
        String endTimeStr = DateUtil.dateToString(endTime);
        String startTimeStr = DateUtil.dateToString(startTime);
        List<User> userList = userDao.findNewUser(startTimeStr,endTimeStr);
       return userList;
    }
}
