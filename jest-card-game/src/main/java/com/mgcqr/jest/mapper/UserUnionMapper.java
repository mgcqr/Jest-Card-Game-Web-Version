package com.mgcqr.jest.mapper;

import com.mgcqr.jest.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUnionMapper {
    UserEntity findById(@Param("id") Integer id);
}
