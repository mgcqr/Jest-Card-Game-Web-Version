package com.mgcqr.jest.service.impl;

import com.mgcqr.jest.service.BasicService;
import org.springframework.stereotype.Service;

@Service
public class BasicServiceImpl implements BasicService {

    private int count;

    @Override
    public synchronized int count(){
        return count ++;
    }

}
