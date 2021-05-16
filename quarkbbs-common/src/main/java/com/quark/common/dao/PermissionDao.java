package com.quark.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quark.common.entity.Permission;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lhr on 17-7-31.
 */
@Repository
@CacheConfig(cacheNames = "permissions")
public interface PermissionDao extends BaseMapper<Permission> {


    @Cacheable
    List<Permission> findAll();

}
