package com.quark.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.common.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lhr on 17-7-31.
 */
@CacheConfig(cacheNames = "adminusers")
public interface AdminUserDao extends BaseMapper<AdminUser> {


    AdminUser findByUsername(@Param("username") String username);

    Page<AdminUser> findByPage(@Param("page") Page<AdminUser> page, @Param("adminUser") AdminUser adminUser);

//    List<AdminUser> findAll(Specification specification, SpringDataWebProperties.Sort sort);
}
