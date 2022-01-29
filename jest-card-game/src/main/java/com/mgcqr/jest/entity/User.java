package com.mgcqr.jest.entity;

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
