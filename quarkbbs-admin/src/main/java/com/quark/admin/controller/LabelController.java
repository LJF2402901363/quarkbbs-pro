package com.quark.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quark.admin.service.LabelService;
import com.quark.common.base.BaseController;
import com.quark.common.dto.PageResult;
import com.quark.common.dto.QuarkResult;
import com.quark.common.entity.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author LHR
 * Create By 2017/9/3
 */
@RequestMapping("/labels")
@RestController
public class LabelController extends BaseController {

    @Autowired
    private LabelService labelService;

    @GetMapping
    public PageResult getAll(String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {

        int pageNo = start / length;
        Page<Label> page = labelService.findByPage(pageNo, length);
        PageResult<List<Label>> result = new PageResult<>(draw,
                page.getTotal(),
                page.getSize(),
                page.getRecords());

        return result;
    }

    @PostMapping("/delete")
    public QuarkResult deleteLabels(@RequestParam(value = "id") Integer[] id) {
        QuarkResult result = restProcessor(() -> {
            labelService.deleteInBatch(id);
            return QuarkResult.ok();
        });
        return result;
    }

    @PostMapping("/add")
    public QuarkResult addLabels(Label label){
        QuarkResult result = restProcessor(() -> {
            labelService.save(label);
            return QuarkResult.ok();
        });

        return result;
    }

}
