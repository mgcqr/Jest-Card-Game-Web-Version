package com.mgcqr.jest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.enumeration.GameState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("game")//指定entity对应的表名, 不声明默认是类名转snake形式
public class GameEntity {
    @TableId
    private String id;
    private GameState state;
    private String description;

    @JsonProperty("start_time")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonProperty("finish_time")
    private LocalDateTime finishTime;
}
