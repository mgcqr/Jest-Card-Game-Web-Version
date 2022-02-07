package com.mgcqr.jest.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResDto<T> {

    private List<T> records;
    private long current;
    private long size;
    private long total;

}
