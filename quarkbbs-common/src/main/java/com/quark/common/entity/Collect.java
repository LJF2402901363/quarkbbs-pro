package com.quark.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.quark.common.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author LHR
 * Create By 2017/8/18
 *
 * 收藏
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Posts posts;
    private User user;

    //收藏时间
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date initTime;
}
