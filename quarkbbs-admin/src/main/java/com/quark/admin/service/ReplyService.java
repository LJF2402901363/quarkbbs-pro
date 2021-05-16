package com.quark.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quark.common.entity.Reply;

import java.util.List;

/**
 * @Author LHR
 * Create By 2017/9/3
 */
public interface ReplyService extends IService<Reply> {

    /**
     * 翻页条件查询回复
     * @param reply
     * @param pageNo
     * @param length
     * @return
     */
   Page<Reply> findByPage(Reply reply, int pageNo, int length);

    boolean deleteInBatch(Integer[] id);

    boolean deleteInBatch(List<Integer> id);
    
}
