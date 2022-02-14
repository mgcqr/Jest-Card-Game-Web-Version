package com.mgcqr.jest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RuntimeUserInfo {
    private String id;
    @JsonProperty("user_name")
    private String userName;
}
