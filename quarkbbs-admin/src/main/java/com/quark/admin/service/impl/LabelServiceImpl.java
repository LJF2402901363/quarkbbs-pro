package com.quark.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quark.admin.service.LabelService;
import com.quark.common.dao.LabelDao;
import com.quark.common.entity.Label;
import org.checkerframework.checker.units.qual.A;
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
public class LabelServiceImpl extends ServiceImpl<LabelDao,Label> implements LabelService{
   @Autowired
   private LabelDao labelDao;
    @Override
    public Page<Label> findByPage(int pageNo, int length) {
      Page<Label> page = new Page<>(pageNo, length);
      this.page(page,null);
        return page;
    }

    @Override
    public boolean deleteInBatch(Integer[] ids) {
        if (Objects.isNull(ids) || ids.length == 0){
            return false;
        }
        ArrayList<Integer> idList = new ArrayList<>(ids.length);
        for (Integer id : ids) {
            idList.add(id);
        }
        int result = labelDao.deleteBatchIds(idList);
        return result != 0;
    }

    @Override
    public boolean deleteInBatch(List<Integer> idList) {
        if (Objects.isNull(idList) || idList.size() == 0){
            return  false;
        }
        int result = labelDao.deleteBatchIds(idList);
        return result != 0;
    }
}
