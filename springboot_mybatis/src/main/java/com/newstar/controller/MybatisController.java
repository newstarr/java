package com.newstar.controller;

import com.newstar.domain.User;
import com.newstar.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MybatisController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/query")
    public List<User> Query() {
        return userMapper.queryUserList();
    }
}
