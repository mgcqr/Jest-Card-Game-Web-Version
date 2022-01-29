package com.mgcqr.mapper;

import com.mgcqr.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUnionMapper {
    User findById(@Param("id") Integer id);
}
