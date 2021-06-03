package com.quark.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.common.entity.Label;
import org.apache.ibatis.annotations.Param;

public interface LabelDao extends BaseMapper<Label> {
    /**
     * @Description :通过标签的id查找标签
     * @Date 0:07 2021/5/30 0030
     * @Param * @param labelId ：标签的ID
     * @return com.quark.common.entity.Label
     **/
    Label findLabelByLabelId(@Param("labelId")Integer labelId);
    /**
     * @Description :分页查找标签数据
     * @Date 10:35 2021/5/24 0024
     * @Param * @param page ：
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.quark.common.entity.Label>
     **/
    Page<Label> findLabelByPage(Page<Label> page);
    /**
     * @Description :分页模糊查找标签数据
     * @Date 10:35 2021/5/24 0024
     * @Param * @param page ：
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.quark.common.entity.Label>
     **/
    Page<Label> findSearchLabelByPage(@Param("page") Page<Label> page,@Param("label") Label label);
}
