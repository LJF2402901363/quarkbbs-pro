package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.AdminUserService;
import com.quark.admin.service.PermissionService;
import com.quark.admin.service.RoleService;
import com.quark.common.dao.RoleDao;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.AdminUser;
import com.quark.common.entity.Permission;
import com.quark.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lhr on 17-8-1.
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao,Role> implements RoleService {

    @Autowired
    private AdminUserService userService;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleDao roleDao;
    public QuarkResult findRolesAndSelected(Integer id) {
       AdminUser adminUser = userService.getById(id);
       if (Objects.isNull(adminUser)){
           return  QuarkResult.ok("");
       }
        Set<Role> userRole = userService.getById(id).getRoles();
        for (Role r: userRole) {
            if (userRole.contains(r)) r.setSelected(1);
        }

        return QuarkResult.ok(userRole);
    }



    @Override
    public void saveRolePermission(Integer roleid, Permission[] pers) {
        Role role = roleDao.selectById(roleid);
        if (pers==null){
            role.setPermissions(new HashSet<>());
        }
        else {
            role.setPermissions(Stream.of(pers).collect(toSet()));
        }
        save(role);
    }
    public boolean deleteInBatch(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return false;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        for (Integer id : ids) {
            idList.add(id);
        }
        int result = roleDao.deleteBatchIds(idList);
        return result != 0;
    }

    @Override
    public boolean deleteInBatch(List<Integer> idList) {
        if (Objects.isNull(idList) || idList.size() == 0){
            return  false;
        }
        int result = roleDao.deleteBatchIds(idList);
        return result != 0;
    }
}
