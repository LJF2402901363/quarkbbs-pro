package com.quark.rest.controller;

import com.quark.common.base.BaseController;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.Posts;
import com.quark.common.entity.User;
import com.quark.rest.service.RankService;
import com.quark.common.service.RedisService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @Author LHR
 * Create By 2017/8/31
 */
@ApiModel(value = "排行榜接口", description = "七天内热帖排行榜,七天内新注册用户排行榜")
@RestController
@RequestMapping("/rank")
public class RankController extends BaseController {
    @Autowired
    private RankService rankService;

    @Autowired
    private RedisService redisService;

    @Value("${REDIS_RANK_POSTS}")
    private String REDIS_RANK_POSTS;

    @Value("${REDIS_RANK_USERS}")
    private String REDIS_RANK_USERS;

    @ApiOperation("获取一个月内热帖排行榜")
    @GetMapping("/topPosts")
    public QuarkResult getTotPosts() {
        QuarkResult result = restProcessor(() -> {
            //Redis中存在从Redis获取
            List<Posts> hot = redisService.getCacheList(REDIS_RANK_POSTS);
            if (Objects.nonNull(hot) && hot.size() > 0) {
                return QuarkResult.ok(hot);
            }
            //从数据库中获取
            hot = rankService.findPostsRank();
            redisService.setCacheList(REDIS_RANK_POSTS, hot);
            return QuarkResult.ok(hot);
        });
        return result;
    }

    @ApiOperation("获取一个月内新注册的用户")
    @GetMapping("/newUsers")
    public QuarkResult getNewUser() {
        QuarkResult result = restProcessor(() -> {
            List<User> users = redisService.getCacheList(REDIS_RANK_USERS);
            if (Objects.nonNull(users) && users.size() > 0) {
                //Redis中存在从Redis获取
                return QuarkResult.ok(users);
            }
            //从数据库中获取
            users = rankService.findUserRank();
            redisService.setCacheList(REDIS_RANK_USERS, users);
            return QuarkResult.ok(users);
        });
        return result;
    }

}
