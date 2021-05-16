package com.quark.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.common.entity.Label;
import com.quark.common.entity.Posts;
import com.quark.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@CacheConfig(cacheNames = "postses")
@Repository
public interface PostsDao extends BaseMapper<Posts> {

    @Cacheable
    List<Posts> findAll();

    List<Posts> findHot();

    IPage<Posts> findByUser(User user);

    IPage<Posts> findByLabel(Label label);

    Page<Posts> findByPage(@Param("page") Page<Posts> page, @Param("posts") Posts posts);

    Page<Posts> getPostsByPage(@Param("page") Page<Posts> page, @Param("type") String type, @Param("search") String search);

    Page<Posts> getPostsByUserId(@Param("page") Page<Posts> page, @Param("id") Integer id);

    Page<Posts> getPostsByLabel(@Param("page") Page<Posts> page, @Param("label") Label label);
}
