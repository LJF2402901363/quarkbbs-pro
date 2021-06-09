package com.quark.rest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.common.dao.UserDao;
import com.quark.common.entity.User;
import com.quark.common.exception.ServiceProcessException;
import com.quark.common.service.RedisService;
import com.quark.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @Author LHR
 * Create By 2017/8/21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RedisService redisSocketService;

    @Autowired
    private RedisService redisService;

    @Value("${REDIS_USERID_KEY}")
    private String REDIS_USERID_KEY;

    @Value("${REDIS_USER_KEY}")
    private String REDIS_USER_KEY;

    @Value("${REDIS_USER_TIME}")
    private Integer REDIS_USER_TIME;
    @Autowired
    private UserDao userDao;
    @Override
    public boolean checkUserName(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) return true;
        return false;
    }

    @Override
    public boolean checkUserEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) return true;
        return false;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void createUser(String email, String username, String password) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setInit_time(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userDao.insert(user);
    }

    @Override
    public String loginUser(User user) {
        String token = UUID.randomUUID().toString();
        redisService.setCacheMapValue(REDIS_USER_KEY , token, user);
        redisSocketService.setCacheSet(REDIS_USERID_KEY,user.getId());
//        loginId.add(user.getId());//维护一个登录用户的set
        return token;
    }

    @Override
    public User getUserByToken(String token) {
        User user = redisService.getValueAndUpDate(REDIS_USER_KEY ,token, REDIS_USER_TIME);
        return user;
    }

    @Override
    public void logoutUser(String token) {
        User user = getUserByToken(token);
        if (user != null){
            redisService.deleteCacheMapKey(REDIS_USER_KEY ,token);
            redisSocketService.deleteFromSet(REDIS_USERID_KEY,user.getId());
        }

//        loginId.remove(user.getId());//维护一个登录用户的set
    }

    @Override
    public void updateUser(String token, String username, String signature, Integer sex) {
        User cacheuser = redisService.getValueByMapKey(REDIS_USER_KEY , token);
        if (cacheuser == null) throw new ServiceProcessException("session过期,请重新登录");
        User user = userDao.selectById(cacheuser.getId());
        user.setSex(sex);
        user.setSignature(signature);
        userDao.updateById(user);
        redisService.setCacheMapValue(REDIS_USER_KEY ,token, user);
    }

    @Override
    public void updataUserIcon(String token, String icon) {
        User cacheuser = redisService.getValueByMapKey(REDIS_USER_KEY ,token);
        if (cacheuser == null)
            throw new ServiceProcessException("用户Session过期，请重新登录");
        User user = userDao.selectById(cacheuser.getId());
        user.setIcon(icon);
        userDao.updateById(user);
        redisService.setCacheMapValue(REDIS_USER_KEY , token, user);
    }


    @Override
    public void updateUserPassword(String token, String oldpsd, String newpsd) {
        User cacheuser = redisService.getValueByMapKey(REDIS_USER_KEY , token);
        if (cacheuser == null)
            throw new ServiceProcessException("用户Session过期，请重新登录");
        User user = userDao.selectById(cacheuser.getId());
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldpsd.getBytes())))
            throw new ServiceProcessException("原始密码错误,请重新输入");
        user.setPassword(DigestUtils.md5DigestAsHex(newpsd.getBytes()));
        userDao.updateById(user);
        redisService.deleteCacheMapKey(REDIS_USER_KEY,token);
    }


}
