package com.quark.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.quark.common.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author LHR
 * Create By 2017/8/18
 *
 * 通知
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("quark_notification")
public class Notification {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private boolean isRead = false;
    private User touser;
    private User fromuser;
    private Posts posts;
    @JsonFormat(pattern = Constants.DATETIME_FORMAT, timezone = "GMT+8")
    private Date initTime;

}
