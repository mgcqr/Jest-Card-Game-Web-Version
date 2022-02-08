package com.mgcqr.jest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("game_user_relation")
public class GameUserRelEntity {
    @TableId
    private String id;

    @TableField("game_id")
    private String gameId;

    @TableField("user_id")
    private String userId;

    private Integer score;
}
