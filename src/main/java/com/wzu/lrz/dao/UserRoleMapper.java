package com.wzu.lrz.dao;


import com.wzu.lrz.pojo.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRoleMapper {

    UserRole getUserRoleById(Integer id);
    List<UserRole> getUserRoleList();
}