package com.quark.rest.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.common.dao.LabelDao;
import com.quark.common.dao.PostsDao;
import com.quark.common.dao.UserDao;
import com.quark.common.entity.Label;
import com.quark.common.entity.Posts;
import com.quark.common.entity.User;
import com.quark.common.exception.ServiceProcessException;
import com.quark.rest.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author LHR
 * Create By 2017/8/26
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsDao, Posts> implements PostsService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private PostsDao postsDao;
    @Transactional
    @Override
    public void savePosts(Posts posts, Integer labelId, User user) {
        try {
            Label label = labelDao.selectById(labelId);

            if (label == null) throw new ServiceProcessException("标签不存在!");
            //标签的帖子数量+1
            Integer postsCount = label.getPostsCount();
            label.setPostsCount(++postsCount);
            labelDao.insert(label);

            //添加帖子
            posts.setLabel(label);
            posts.setInitTime(new Date());
            posts.setUser(user);
            postsDao.insert(posts);
        } catch (ServiceProcessException e) {
            throw e;
        } catch (Exception e) {
            // 所有编译期异常转换为运行期异常
            throw new ServiceProcessException("发布帖子失败!");
        }
    }

    @Override
    public Page<Posts> getPostsByPage(String type, String search, int pageNo, int length) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "top"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Page<Posts> page = new Page<>(pageNo, length);
        page = postsDao.getPostsByPage(page,type,search);
        return page;
    }

    @Override
    public List<Posts> getPostsByUserId(Integer userId) {
        Page<Posts> page = new Page<>(1,10);
        page =  postsDao.getPostsByUserId(page,userId);
       return  page.getRecords();
    }

    @Override
    public Page<Posts> getPostsByLabel(Label label, int pageNo, int lenght) {
       Page<Posts> page = new Page<>(pageNo,lenght);
        page = postsDao.getPostsByLabel(page,label);
        return page;
    }

}
