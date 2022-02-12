package com.mgcqr.jest.service;

public interface UserService {
    String getId(String token);

    String getUserName(String token);
}
