package com.quark.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.admin.service.PermissionService;
import com.quark.admin.shiro.ShiroService;
import com.quark.common.base.BaseController;
import com.quark.common.dto.PageResult;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.Permission;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by lhr on 17-8-3.
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ShiroService shiroService;

    @PostMapping("/loadMenu")
    public List<Permission> loadMenu(){
        Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("AdminSessionId");
        List<Permission> list = permissionService.loadUserPermissionByType(userid,1);
        return list;
    }

    @PostMapping("/PermissionWithSelected")
    public QuarkResult PermissionWithSelected(Integer roleId){
        QuarkResult result = restProcessor(() -> {
            List<Permission> data = permissionService.findPermissionsAndSelected(roleId);
            return QuarkResult.ok(data);
        });

        return result;
    }

    @GetMapping
    public PageResult getAll(String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length){
        int pageNo = start/length;
        Page<Permission> page = permissionService.findByPage(pageNo, length);
        PageResult<List<Permission>> result = new PageResult<>(
                draw,
                page.getTotal(),
                page.getSize(),
                page.getRecords());

        return result;
    }

    @PostMapping("/add")
    public QuarkResult add(Permission permission) {
        QuarkResult result = restProcessor(() -> {
            permissionService.save(permission);
            //????????????
            shiroService.updatePermission();
            return QuarkResult.ok();
        });
        return result;
    }

    @PostMapping("/delete")
    public QuarkResult delete(@RequestParam(value = "id") Integer[] id){
        QuarkResult result = restProcessor(() -> {
            permissionService.deleteInBatch(id);
            //????????????
            shiroService.updatePermission();
            return QuarkResult.ok();
        });
        return result;
    }

}
