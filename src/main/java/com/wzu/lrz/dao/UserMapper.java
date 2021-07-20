package com.wzu.lrz.dao;

import com.wzu.lrz.pojo.Provider;
import com.wzu.lrz.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    User findUser(User user);
    User getUserByCode(String userCode);
    List<User> getUserList();
    List<User> getUserListByCodeName(@Param("key")String key);
    void addUser(User user);
    void updateUser(User user);
    User getUserById(Integer id);
    void deleteById(Integer id);
}