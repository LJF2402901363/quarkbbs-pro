package com.quark.admin.service;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quark.common.entity.Label;

public interface LabelService extends IService<Label> {

    /**
     * 翻页查询
     * @param pageNo
     * @param length
     * @return
     */
    Page<Label> findByPage(int pageNo, int length);

    boolean deleteInBatch(Integer[] id);

    boolean deleteInBatch(List<Integer> id);
}
