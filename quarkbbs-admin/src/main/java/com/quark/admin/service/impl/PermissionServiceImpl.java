package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.PermissionService;
import com.quark.common.dao.AdminUserDao;
import com.quark.common.dao.PermissionDao;
import com.quark.common.dao.RoleDao;
import com.quark.common.entity.AdminUser;
import com.quark.common.entity.Permission;
import com.quark.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lhr on 17-8-1.
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> loadUserPermission(Integer id) {
        List<Permission> perlist = new ArrayList<>();
        AdminUser user = adminUserDao.findAdminUserById(id);
        if (Objects.isNull(user) || Objects.isNull(user.getRoles())){
            return  perlist;
        }
        Set<Role> roles = user.getRoles();
        for (Role role:roles){
            List<Permission> permissionslist =  permissionDao.findRolePermissionByRoleId(role.getId());
            if (Objects.isNull(permissionslist)||permissionslist.size() == 0){
                continue;
            }
            Collections.sort(permissionslist,Comparator.comparing(Permission::getSort));
            permissionslist.forEach(p -> {
                if (p.getParentid() > 0 ){
                    perlist.add(p);
                }
            });
        }
        return perlist;
    }

    @Override
    public List<Permission> loadUserPermissionByType(Integer id, Integer type) {
        List<Permission> perlist = new ArrayList<>();
        AdminUser user = adminUserDao.findAdminUserById(id);
        if (Objects.isNull(user) || Objects.isNull(user.getRoles())){
            return  perlist;
        }
       Set<Role> roles = user.getRoles();
        for (Role role:roles){
            List<Permission> permissionslist =  permissionDao.findRolePermissionByRoleId(role.getId());
            if (Objects.isNull(permissionslist)||permissionslist.size() == 0){
                continue;
            }
            Collections.sort(permissionslist,Comparator.comparing(Permission::getSort));
            permissionslist.forEach(p -> {
                if (p.getParentid() > 0 && p.getType() == type){
                   perlist.add(p);
                }
            });
        }
        return perlist;
    }

    @Override
    public List<Permission> findPermissionsAndSelected(Integer id) {
       Role role = roleDao.findRoleByRoleId(id);
       if (role == null){
           return  new ArrayList<Permission>();
       }
        Set<Permission> permissions = role.getPermissions();
        for (Permission p: permissions) {
            if (permissions.contains(p)) p.setChecked("true");
            else p.setChecked("false");
        }
        return new ArrayList<>(permissions);
    }

    @Override
    public Page<Permission> findByPage(int pageNo, int length) {
        Page<Permission> page = new Page<>(pageNo, length);
        page = permissionDao.findPermissonByPage(page);
        return page;
    }
    public boolean deleteInBatch(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return false;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        for (Integer id : ids) {
            idList.add(id);
        }
        int result = permissionDao.deleteBatchIds(idList);
        return result != 0;
    }

    @Override
    public boolean deleteInBatch(List<Integer> idList) {
        if (Objects.isNull(idList) || idList.size() == 0){
            return  false;
        }
        int result = permissionDao.deleteBatchIds(idList);
        return result != 0;
    }

}
