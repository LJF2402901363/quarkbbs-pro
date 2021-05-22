package com.quark.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 帖子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("quark_posts")
public class Posts implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Label label;
    private String title;
    //内容
    private String content;
    //发布时间
    @JsonFormat(pattern = Constants.DATETIME_FORMAT, timezone = "GMT+8")
    private Date initTime;
    //是否置顶
    private boolean top;
    //是否精华
    private boolean good;
    //与用户的关联关系
    private User user;
    //回复数量
    private int replyCount = 0;
}
