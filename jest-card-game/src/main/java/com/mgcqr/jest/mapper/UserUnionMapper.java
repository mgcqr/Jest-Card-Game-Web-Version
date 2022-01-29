package com.mgcqr.jest.mapper;

import com.mgcqr.jest.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUnionMapper {
    User findById(@Param("id") Integer id);
}
