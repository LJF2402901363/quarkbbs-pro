package com.quark.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.Permission;
import com.quark.common.entity.Role;

import java.util.List;

/**
 * Created by lhr on 17-8-1.
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询用户的角色
     *
     * @param id
     * @return
     */
    QuarkResult findRolesAndSelected(Integer id);


    /**
     * 保存角色的权限
     * @param roleid
     * @param pers
     */
    void saveRolePermission(Integer roleid, Permission[] pers);

    boolean deleteInBatch(Integer[] id);

    boolean deleteInBatch(List<Integer> id);
}
