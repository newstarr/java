package com.newstar.test;

import com.newstar.SpringbootMybatisApplication;
import com.newstar.domain.User;
import com.newstar.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMybatisApplication.class)
public class TestMybatis {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMybatis() {
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
    }
}
