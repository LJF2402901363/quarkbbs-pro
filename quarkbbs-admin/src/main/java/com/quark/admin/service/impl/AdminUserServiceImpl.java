package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.AdminUserService;
import com.quark.admin.service.RoleService;
import com.quark.admin.utils.PasswordHelper;
import com.quark.common.dao.AdminUserDao;
import com.quark.common.dao.RoleDao;
import com.quark.common.entity.AdminUser;
import com.quark.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lhr on 17-8-1.
 */
@Service
@Transactional
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUser> implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private RoleDao roleDao;
    @Override
    public AdminUser findByUserName(String username) {
        return adminUserDao.findByUsername(username);
    }

    @Override
    public Page<AdminUser> findByPage(AdminUser adminUser, int pageNo, int length) {
       Page<AdminUser> page = new Page<>(pageNo, length);
       page =  adminUserDao.findByPage(page,adminUser);
        return page;
    }

    @Override
    public void saveAdmin(AdminUser entity) {
        entity.setEnable(1);
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(entity);
        save(entity);
    }

    @Override
    public void saveAdminRoles(Integer uid, Integer[] roles) {
        AdminUser adminUser = adminUserDao.selectById(uid);
        if (roles == null) {
            adminUser.setRoles(new HashSet<>());
        } else {
            if (Objects.isNull(roles) || roles.length == 0){
                return;
            }
            ArrayList<Integer> idList = new ArrayList<>(roles.length);
            for (Integer id : roles) {
                idList.add(id);
            }
            List<Role> roleSet = roleDao.selectBatchIds(idList);
            adminUser.setRoles(new HashSet<>(roleSet));
        }
        adminUserDao.insert(adminUser);
    }

    @Override
    public void saveAdminEnable(Integer[] ids) {
         if (Objects.isNull(ids) || ids.length == 0){
             return;
         }
         ArrayList<Integer> idList = new ArrayList<>(ids.length);
         for (Integer id : ids) {
             idList.add(id);
         }
        List<AdminUser> all = adminUserDao.selectBatchIds(idList);
        for (AdminUser user : all) {
            if (user.getEnable() == 1) {
                user.setEnable(0);
            } else {
                user.setEnable(1);
            }
            adminUserDao.insert(user);
        }
    }

    @Override
    public boolean deleteAdmin(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return false;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        for (Integer id : ids) {
            idList.add(id);
        }
        int result = adminUserDao.deleteBatchIds(idList);
        return result != 0;
    }

    @Override
    public boolean deleteAdmin(List<Integer> idList) {
        if (Objects.isNull(idList) || idList.size() == 0){
            return  false;
        }
        int result = adminUserDao.deleteBatchIds(idList);
        return result != 0;
    }
}
