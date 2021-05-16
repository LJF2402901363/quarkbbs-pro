package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.ReplyService;
import com.quark.common.dao.ReplyDao;
import com.quark.common.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author LHR
 * Create By 2017/9/3
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyDao,Reply> implements ReplyService {
    @Autowired
    private ReplyDao replyDao;
    @Override
    public Page<Reply> findByPage(Reply reply, int pageNo, int length) {
        Page<Reply> page = new Page<>(pageNo,length);
        page = replyDao.findByPage(page,reply);
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
        int result = replyDao.deleteBatchIds(idList);
        return result != 0;
    }

    @Override
    public boolean deleteInBatch(List<Integer> idList) {
        if (Objects.isNull(idList) || idList.size() == 0){
            return  false;
        }
        int result = replyDao.deleteBatchIds(idList);
        return result != 0;
    }
}
