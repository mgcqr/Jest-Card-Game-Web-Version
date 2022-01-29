package com.mgcqr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
//@TableName("user")
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
}
