package com.quark.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quark.common.entity.Notification;
import com.quark.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author LHR
 * Create By 2017/9/6
 */
public interface NotificationDao extends BaseMapper<Notification> {

    long getNotificationCount(@Param("id") Integer id);

    List<Notification> getByTouserOrderByInitTimeDesc(@Param("user")User user);

    int updateByIsRead(@Param("user") User user);

}
