package com.mgcqr.jest.core;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncTest {

    private static final ThreadLocal<String> STRING_THREAD_LOCAL = new ThreadLocal<>();

    @Async
    public void task(int i){
        STRING_THREAD_LOCAL.set(Thread.currentThread().getName());
        System.out.println(STRING_THREAD_LOCAL.get()+ "  " + i);
    }
}
