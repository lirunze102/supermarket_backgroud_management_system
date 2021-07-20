package com.wzu.lrz;


import com.wzu.lrz.Service.AsyncService;
import com.wzu.lrz.Service.ScheduledService;
import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.pojo.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class Springbootcrud2ApplicationTests {
    @Autowired
    UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test(){
        List<User> users=userMapper.getUserList();
        for (User u:users
             ) {
            System.out.println(u.toString());
        }
    }

}
