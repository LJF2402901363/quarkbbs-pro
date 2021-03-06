package com.quark.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lhr on 17-7-31.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String perurl;

    //资源类型　　1：菜单　2：按钮
    private Integer type;

    //父权限
    private Integer parentid;

    //排序
    private Integer sort;

    //是否选中
    private String checked;

    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
