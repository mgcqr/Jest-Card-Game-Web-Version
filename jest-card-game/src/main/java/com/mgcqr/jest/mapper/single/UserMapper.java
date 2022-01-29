package com.mgcqr.jest.mapper.single;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mgcqr.jest.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
