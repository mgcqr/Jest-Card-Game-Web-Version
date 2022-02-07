package com.mgcqr.jest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class UserEntity implements Serializable {
    private String id;
    //@TableField("name")
    private String userName;
    private String passWord;
}
