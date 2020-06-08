package com.newstar.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstar.SpringbootJpaApplication;
import com.newstar.domain.User;
import com.newstar.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRedis() throws JsonProcessingException {
        //1、从redis中获取数据
        String userListJson = redisTemplate.boundValueOps("user.findAll").get();
        if(userListJson == null) {
            //2、如果不存在，就从mysql数据库中查询，把数据存入redis，并打印
            List<User> all = userRepository.findAll();
            //使用spring内置的jackson将list转json字符串
            ObjectMapper objectMapper = new ObjectMapper();
            userListJson = objectMapper.writeValueAsString(all);
            redisTemplate.boundValueOps("user.findAll").set(userListJson);
            System.out.println("=========从数据库中获取数据=========");
        }else{
            //3、如果存在就直接打印
            System.out.println("=========从redis缓存中获取数据=========");
        }
    }
}
