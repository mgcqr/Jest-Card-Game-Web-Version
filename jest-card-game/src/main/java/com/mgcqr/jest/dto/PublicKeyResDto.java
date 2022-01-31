package com.mgcqr.jest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PublicKeyResDto {
    @JsonProperty("public_key")
    private String publicKey;
}
