package com.quark.rest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.common.dao.NotificationDao;
import com.quark.common.entity.Notification;
import com.quark.common.entity.User;
import com.quark.rest.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @Author LHR
 * Create By 2017/9/6
 */
@Service
@Transactional
public class NotificationServiceImpl extends ServiceImpl<NotificationDao,Notification> implements NotificationService{
    @Autowired
    private NotificationDao notificationDao;
    @Override
    public long getNotificationCount(int uid) {
        return notificationDao.getNotificationCount(uid);
    }

    @Override
    public List<Notification> findByUser(User user) {
        List<Notification> list = notificationDao.getByTouserOrderByInitTimeDesc(user);
        notificationDao.updateByIsRead(user);
        return list;
    }

    @Override
    public void deleteByUser(User user) {
        List<Notification> list = notificationDao.getByTouserOrderByInitTimeDesc(user);
        if (Objects.isNull(list) || list.size() == 0) {
            return;
        }

        list.forEach(e->{
            notificationDao.deleteById(e.getId());
        });
    }
}
