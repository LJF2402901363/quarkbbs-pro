package com.quark.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quark.common.entity.Posts;

import java.util.List;

/**
 * @Author LHR
 * Create By 2017/9/3
 */
public interface PostsService extends IService<Posts> {

   /**
    * 翻页条件查询帖子
    * @param posts
    * @param pageNo
    * @param length
    * @return
    */
   Page<Posts> findByPage(Posts posts, int pageNo, int length);

   /**
    * 批量修改帖子的top
    * @param ids
    */
   void changeTop(Integer[] ids);

   /**
    * 批量修改帖子的good
    * @param ids
    */
   void changeGood(Integer[] ids);


   boolean deleteInBatch(Integer[] id);

   boolean deleteInBatch(List<Integer> id);
}
