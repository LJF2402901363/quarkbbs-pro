package com.quark.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quark.common.entity.Role;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by lhr on 17-7-31.
 */

@CacheConfig(cacheNames = "roles")
public interface RoleDao extends BaseMapper<Role> {

    @Cacheable
    List<Role> findAll();

}
