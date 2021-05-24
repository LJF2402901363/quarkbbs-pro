package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.PostsService;
import com.quark.common.dao.PostsDao;
import com.quark.common.entity.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author LHR
 * Create By 2017/9/3
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsDao,Posts> implements PostsService {
    @Autowired
   private PostsDao postsDao;


    @Override
    public Page<Posts> findByPage(Posts posts, int pageNo, int length) {
        Page<Posts> page = new Page<>(pageNo, length);
        page =  postsDao.findPostsByPage(page,posts);
        return page;
    }

    @Override
    public void changeTop(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        List<Posts> all = postsDao.selectBatchIds(idList);
        for (Posts p :all) {
            if (p.isTop()==false) {
                p.setTop(true);
            }else {
                p.setTop(false);
            }
            postsDao.insert(p);
        }

    }

    @Override
    public void changeGood(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        List<Posts> all =  postsDao.selectBatchIds(idList);
        for (Posts p :all) {
            if (p.isGood()==false) {
                p.setGood(true);
            }else {
                p.setGood(false);
            }
            postsDao.insert(p);
        }

    }

    public boolean deleteInBatch(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return false;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        for (Integer id : ids) {
            idList.add(id);
        }
        int result = postsDao.deleteBatchIds(idList);
        return result != 0;
    }

    @Override
    public boolean deleteInBatch(List<Integer> idList) {
        if (Objects.isNull(idList) || idList.size() == 0){
            return  false;
        }
        int result = postsDao.deleteBatchIds(idList);
        return result != 0;
    }


}
