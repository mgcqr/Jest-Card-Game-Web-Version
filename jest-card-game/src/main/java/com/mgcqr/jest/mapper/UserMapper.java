package com.mgcqr.jest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mgcqr.jest.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
