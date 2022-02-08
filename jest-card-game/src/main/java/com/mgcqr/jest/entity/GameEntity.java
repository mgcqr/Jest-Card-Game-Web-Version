package com.mgcqr.jest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mgcqr.jest.enumeration.GameState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("game")//指定entity对应的表名, 不声明默认是类名转snake形式
public class GameEntity {
    @TableId
    private String id;
    private GameState state;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
}
