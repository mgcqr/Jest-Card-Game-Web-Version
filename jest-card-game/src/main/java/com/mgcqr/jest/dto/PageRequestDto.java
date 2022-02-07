package com.mgcqr.jest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageRequestDto {
    //@JsonProperty("page_num")
    private Integer current;
    private Integer size;
}
