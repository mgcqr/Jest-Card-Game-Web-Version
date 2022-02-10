package com.mgcqr.jest.core;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncTest {
    @Async
    public void task(int i){
        System.out.println(Thread.currentThread().getName() + i);
    }
}
